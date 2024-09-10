package vip.fairy.web;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  @Autowired
  DefaultRedisScript<Boolean> redisScript;

  @RequestMapping("/redis")
  public String getKey() {
    ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
    boolean flag = Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent("visitCount_redis", 1, 10, TimeUnit.SECONDS));
    if (flag) {
      System.out.println("第一次访问");
    } else {
      valueOperations.increment("visitCount_redis");
    }

    int i = Integer.parseInt(Objects.requireNonNull(valueOperations.get("visitCount_redis")).toString());

    if (i > 10) {
      return "此接口十秒内访问超过10次，请稍后访问";
    }

    return Objects.requireNonNull(valueOperations.get("visitCount_redis")).toString();
  }

  @RequestMapping("/redisTest")
  public void redisTest() {
    redisTemplate.opsForList().leftPushAll("l1", "a1", "a2");

    redisTemplate.opsForList().rightPushAll("users", "user1", "user2", "user3");

    BoundListOperations<String, Object> boundListOperations = redisTemplate.boundListOps("l2");

    Object r1 = boundListOperations.rightPop();
    System.out.println(r1);

    List<Object> list = boundListOperations.range(0, -1);
    list.forEach(System.out::println);
  }

  @RequestMapping("/luaTest")
  public String luaTest() {
    String key = "chk";

    redisTemplate.delete(key);
    redisTemplate.opsForValue().set(key, "ha ha ha");

    String s1 = Objects.requireNonNull(redisTemplate.opsForValue().get(key)).toString();
    System.out.println(s1);

    Boolean isEq = redisTemplate.execute(redisScript, Collections.singletonList(key), "ha ha ha", "3333");
    System.out.println(isEq);

    String s2 = Objects.requireNonNull(redisTemplate.opsForValue().get(key)).toString();
    System.out.println(s2);

    return null;
  }

}
