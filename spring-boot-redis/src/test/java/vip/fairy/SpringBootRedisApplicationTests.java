package vip.fairy;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson2.JSON;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import vip.fairy.model.UserInfo;

@SpringBootTest
class SpringBootRedisApplicationTests {


  @Autowired
  RedisTemplate<String, String> redisTemplate;

  @Autowired
  RedisTemplate<String, Object> objectRedisTemplate;

  @Test
  void contextLoads() {
    redisTemplate.opsForValue().set("hobby", "play pc");

    UserInfo userInfo = new UserInfo();
    userInfo.setUserId(1000L);
    userInfo.setUserName("胡汉三");
    userInfo.setUserEmail("hobby@gmail.com");
    userInfo.setUserBirthday(LocalDateTime.now().plusDays(-30));
    redisTemplate.opsForValue().set("user-service:user:1", JSON.toJSONString(userInfo));

    String userInfoJson = redisTemplate.opsForValue().get("user-service:user:1");
    System.out.println(userInfoJson);
  }

  @Test
  void testLua1() {
    RedisScript<List> script = RedisScript.of(new DefaultResourceLoader().getResource("lua/people.lua"), List.class);
    List list = objectRedisTemplate.execute(script, Collections.singletonList("users"), Collections.emptyList());
    list.forEach(System.out::println);
  }

  @Test
  void testLua2() {
    String luaScript = """
        local function get_list(key)
            local values = redis.call('LRANGE', key, 0, -1) -- 获取整个列表
            return values
        end
        return get_list(KEYS[1])
        """;
    List list = objectRedisTemplate.execute(RedisScript.of(luaScript, List.class), Collections.singletonList("users"), Collections.emptyList());
    list.forEach(System.out::println);
  }

  @Test
  void testLuaLock1() {
    String lockKey = "SN123";
    String uuid = UUID.fastUUID().toString();
    boolean success = Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(lockKey, uuid, 3, TimeUnit.MINUTES));
    if (!success) {
      System.out.println("锁已存在");
      return;
    }
    // 执行 lua 脚本
    DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
    // 指定 lua 脚本
    redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/del-key.lua")));
    // 指定返回类型
    redisScript.setResultType(Long.class);
    // 参数一：redisScript，参数二：key列表，参数三：arg（可多个）
    Long result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), uuid);
    System.out.println(result);
  }

  private static final String RELEASE_LOCK_LUA_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";


  @Test
  void testLuaLock2() {
    String lockKey = "SN123";
    String UUID = cn.hutool.core.lang.UUID.fastUUID().toString();
    boolean success = Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(lockKey, UUID, 3, TimeUnit.MINUTES));
    if (!success) {
      System.out.println("锁已存在");
      return;
    }
    // 指定 lua 脚本，并且指定返回值类型
    DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(RELEASE_LOCK_LUA_SCRIPT, Long.class);
    // 参数一：redisScript，参数二：key列表，参数三：arg（可多个）
    Long result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), UUID);
    System.out.println(result);
  }

  @Test
  void testLuaLock() {
    String lockKey = "SN123";
    String uuid = UUID.randomUUID().toString();
    boolean locked = Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(lockKey, uuid, 1000, TimeUnit.SECONDS));
    try {
      if (locked) {
        System.out.println("拿到锁，执行相关业务逻辑.......");
      } else {
        System.out.println("未拿到锁，中断相关业务逻辑操作.......");
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } finally {
      //  释放锁，先比对自己锁的值是否相等，相等则为自己的锁
      String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
      redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class), List.of(lockKey), uuid);
    }

  }

}
