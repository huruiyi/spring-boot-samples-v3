# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/4.0.2/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/4.0.2/maven-plugin/build-image.html)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/4.0.2/reference/actuator/index.html)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/4.0.2/reference/using/devtools.html)
* [Spring for Apache Kafka](https://docs.spring.io/spring-boot/4.0.2/reference/messaging/kafka.html)
* [Spring Web](https://docs.spring.io/spring-boot/4.0.2/reference/web/servlet.html)

### Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the
parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

```bash
# 1. 启动应用
# 2. 发送正常消息
curl -X POST "http://localhost:8080/api/kafka/send/success?msg=normal_msg_001"

# 3. 验证 output-topic（新开终端）
kafka-console-consumer --bootstrap-server localhost:9092 \
--topic output-topic --from-beginning --property print.timestamp=true

# ✅ 预期结果：
#   [2024-06-15 10:20:30] PROCESSED: SUCCESS: normal_msg_001 @ 2024-06-15T10:20:30
# ✅ input-topic 偏移量已提交（重启消费者不会重复消费）



```


```bash
# 1. 发送异常消息
curl -X POST "http://localhost:8080/api/kafka/send/error?msg=test_error_001"

# 2. 立即查看 output-topic（无新增消息！）
kafka-console-consumer --bootstrap-server localhost:9092 \
--topic output-topic --from-beginning

# 3. 重启应用后再次消费 input-topic（消息仍在！）
# ✅ 预期结果：
#   - output-topic 无 "error-trigger" 相关消息（发送操作已回滚）
#   - 应用日志显示 "触发事务回滚" + "业务处理失败"
#   - 重启消费者后，该消息被重新拉取（偏移量未提交）
```



```powershell
# 从头开始查看所有已提交消息（含时间戳/分区/偏移量）
.\bin\windows\kafka-console-consumer.bat `
    --bootstrap-server localhost:9092 `
    --topic output-topic `
    --from-beginning `
    --property print.timestamp=true `
    --property print.partition=true `
    --property print.offset=true `
    --property print.key=true `
    --property key.separator=" | " `
    --property line.separator=$'\n--------------------\n'
``


.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic input-topic --from-beginning --isolation-level read_uncommitted --property print.timestamp=true  --property print.offset=true