package vip.fairy;

import com.alibaba.fastjson2.JSON;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
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
}
