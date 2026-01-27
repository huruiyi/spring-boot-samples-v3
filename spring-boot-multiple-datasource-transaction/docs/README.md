# Spring Boot 3 多数据源动态切换示例项目

## 项目简介

本项目基于 Spring Boot 3.2.1 实现了动态数据源切换功能，支持在用户模块和订单模块之间自动切换不同的 MySQL 数据库。

## 技术栈

- Spring Boot 3.2.1
- Java 17
- MySQL 8.0
- HikariCP 连接池
- Spring AOP
- Lombok
- Maven

## 项目结构

```
spring-boot-multiple-database/
├── src/
│   └── main/
│       ├── java/com/example/multidb/
│       │   ├── annotation/          # 自定义注解
│       │   │   └── DataSource.java  # 数据源切换注解
│       │   ├── aspect/              # AOP切面
│       │   │   └── DataSourceAspect.java
│       │   ├── config/              # 配置类
│       │   │   └── DataSourceConfig.java
│       │   ├── constant/            # 常量
│       │   │   └── DataSourceType.java
│       │   ├── context/             # 上下文
│       │   │   └── DataSourceContextHolder.java
│       │   ├── controller/          # 控制器
│       │   │   ├── UserController.java
│       │   │   └── OrderController.java
│       │   ├── datasource/          # 数据源
│       │   │   └── DynamicDataSource.java
│       │   ├── entity/              # 实体类
│       │   │   ├── User.java
│       │   │   └── Order.java
│       │   ├── repository/          # 数据访问层
│       │   │   ├── UserRepository.java
│       │   │   └── OrderRepository.java
│       │   ├── service/             # 服务层
│       │   │   ├── UserService.java
│       │   │   └── OrderService.java
│       │   └── MultiDatabaseApplication.java
│       └── resources/
│           └── application.yml      # 配置文件
├── sql/
│   └── init.sql                    # 数据库初始化脚本
└── pom.xml                         # Maven配置
```

## 快速开始

### 1. 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+

### 2. 数据库初始化

执行 `sql/init.sql` 脚本，创建两个数据库和相应的表：

```bash
mysql -u root -p < sql/init.sql
```

### 3. 修改配置

在 `src/main/resources/application.yml` 中修改数据库连接信息：

```yaml
spring:
  datasource:
    user:
      url: jdbc:mysql://localhost:3306/user_db
      username: root
      password: fairy-vip
    order:
      url: jdbc:mysql://localhost:3306/order_db
      username: root
      password: fairy-vip
```

### 4. 运行项目

```bash
mvn clean package
java -jar target/spring-boot-multiple-database-1.0.0.jar
```

或者在 IDE 中直接运行 `MultiDatabaseApplication` 主类。

## API 接口

### 用户模块

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/users | 查询所有用户 |
| GET | /api/users/{id} | 根据ID查询用户 |
| GET | /api/users/username/{username} | 根据用户名查询用户 |
| POST | /api/users | 保存用户 |
| PUT | /api/users | 更新用户 |
| DELETE | /api/users/{id} | 删除用户 |
| POST | /api/users/create-with-order | 创建用户和订单（自定义参数） |
| GET | /api/users/test-transaction | 事务测试（写死数据） |

### 订单模块

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /api/orders | 查询所有订单 |
| GET | /api/orders/{id} | 根据ID查询订单 |
| GET | /api/orders/orderNo/{orderNo} | 根据订单号查询订单 |
| GET | /api/orders/user/{userId} | 根据用户ID查询订单列表 |
| GET | /api/orders/username/{username} | 根据用户名查询订单列表 |
| POST | /api/orders | 保存订单 |
| PUT | /api/orders | 更新订单 |
| PUT | /api/orders/{id}/status | 更新订单状态 |
| DELETE | /api/orders/{id} | 删除订单 |

## 使用示例

### 1. 查询所有用户（使用 user_db 数据库）

```bash
curl http://localhost:8080/api/users
```

### 2. 查询所有订单（使用 order_db 数据库）

```bash
curl http://localhost:8080/api/orders
```

### 3. 创建新用户

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "phone": "13900139000",
    "age": 28,
    "address": "深圳市南山区"
  }'
```

### 4. 创建新订单

```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "orderNo": "ORD202401270006",
    "userId": 1,
    "productName": "Magic Keyboard",
    "quantity": 1,
    "totalPrice": 1099.00,
    "status": "PENDING"
  }'
```

### 5. 根据用户名查询订单列表（跨库查询）

```bash
curl http://localhost:8080/api/orders/username/zhangsan
```

此接口会先查询 user_db 数据库获取用户ID，然后查询 order_db 数据库获取该用户的订单列表。

### 6. 事务测试 - 使用写死的数据创建用户和订单

```bash
# 测试事务提交（正常执行）
curl http://localhost:8080/api/users/test-transaction?rollback=false

# 测试事务回滚（会抛出异常）
curl http://localhost:8080/api/users/test-transaction?rollback=true
```

### 7. 事务测试 - 使用自定义参数创建用户和订单

```bash
curl -X POST http://localhost:8080/api/users/create-with-order \
  -H "Content-Type: application/json" \
  -d '{
    "rollback": false,
    "username": "testuser001",
    "email": "test001@example.com",
    "phone": "13800138001",
    "orderNo": "TEST001",
    "productName": "测试商品",
    "quantity": 1,
    "totalPrice": 199.99
  }'
```

**重要提示：**
- `rollback: false`：正常提交，用户和订单都会被创建
- `rollback: true`：测试回滚，会抛出异常
- 在多数据源场景下，默认的事务机制只能保证单个数据源的事务一致性
- 如需实现真正的跨数据源分布式事务，建议使用 Seata 等分布式事务框架

## 分布式事务（Seata）

本项目已集成 Seata 分布式事务框架，支持真正的跨数据源事务回滚。

### Seata 集成

1. **依赖引入**：已添加 `seata-spring-boot-starter` 依赖
2. **配置文件**：已配置 `application.yml`、`file.conf`、`registry.conf`
3. **全局事务注解**：`TransactionTestService` 使用 `@GlobalTransactional` 注解
4. **Undo Log表**：在所有业务数据库中创建 `undo_log` 表

### 快速体验分布式事务

```bash
# 1. 启动 Seata Server
cd {seata-server目录}/bin
./seata-server.sh -p 8091

# 2. 初始化 Seata 数据库
mysql -u root -pfairy-vip < sql/seata_server.sql

# 3. 确保业务数据库包含 undo_log 表
mysql -u root -pfairy-vip < sql/init.sql

# 4. 启动应用
mvn spring-boot:run

# 5. 测试正常提交（数据会真正提交）
curl http://localhost:8080/api/users/test-transaction?rollback=false

# 6. 测试回滚（跨数据源会自动回滚）
curl http://localhost:8080/api/users/test-transaction?rollback=true
```

详细的部署指南请查看：`docs/Seata分布式事务部署指南.md`

## 核心实现

### 1. 数据源注解

使用 `@DataSource` 注解在 Service 类上指定使用的数据源：

```java
@Service
@DataSource(DataSourceType.USER)
public class UserService {
    // ...
}

@Service
@DataSource(DataSourceType.ORDER)
public class OrderService {
    // ...
}
```

### 2. 动态数据源

通过继承 `AbstractRoutingDataSource` 实现动态路由：

```java
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSource();
    }
}
```

### 3. AOP 切面

使用 AOP 切面拦截方法调用，在方法执行前设置数据源，执行后清除：

```java
@Around("@annotation(com.example.multidb.annotation.DataSource) || @within(com.example.multidb.annotation.DataSource)")
public Object around(ProceedingJoinPoint point) throws Throwable {
    // 设置数据源
    DataSourceContextHolder.setDataSource(dataSourceType);
    try {
        return point.proceed();
    } finally {
        // 清除数据源上下文
        DataSourceContextHolder.clearDataSource();
    }
}
```

## 注意事项

1. 确保两个数据库已经创建并初始化
2. 数据库用户名和密码需根据实际情况修改
3. Service 类上必须使用 `@DataSource` 注解指定数据源
4. 不要在同一个事务中操作多个数据源

## 许可证

MIT License
