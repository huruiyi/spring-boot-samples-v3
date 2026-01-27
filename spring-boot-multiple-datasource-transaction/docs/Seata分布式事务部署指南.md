# Seata 分布式事务部署指南

## 1. 项目修改说明

### 1.1 已添加的依赖

pom.xml 中已添加 Seata 相关依赖：
```xml
<dependency>
    <groupId>io.seata</groupId>
    <artifactId>seata-spring-boot-starter</artifactId>
    <version>2.0.0</version>
</dependency>
```

### 1.2 已添加的配置文件

- `src/main/resources/application.yml` - Seata客户端配置
- `src/main/resources/file.conf` - Seata服务配置
- `src/main/resources/registry.conf` - Seata注册配置

### 1.3 已修改的代码

TransactionTestService 已添加 `@GlobalTransactional` 注解：
```java
@GlobalTransactional(name = "create-user-and-order", rollbackFor = Exception.class)
public String createUserAndOrder(TransactionTestRequest request) {
    // 跨数据源操作
    userService.save(user);
    orderService.save(order);
}
```

## 2. 部署步骤

### 2.1 初始化Seata Server数据库

执行 Seata Server 所需的表结构：

```bash
mysql -u root -pfairy-vip < sql/seata_server.sql
```

这会创建 `seata` 数据库和三张核心表：
- `global_table` - 全局事务表
- `branch_table` - 分支事务表
- `lock_table` - 全局锁表

### 2.2 初始化业务数据库

确保业务数据库包含 undo_log 表：

```bash
mysql -u root -pfairy-vip < sql/init.sql
```

这会在 `user_db` 和 `order_db` 中都创建 `undo_log` 表，用于 Seata 的 AT 模式。

### 2.3 下载 Seata Server

从 Seata 官网下载服务器：
```bash
# 下载 Seata Server 2.0.0
# https://github.com/seata/seata/releases

# 解压后，复制配置文件到 Seata 目录
cp src/main/resources/file.conf {seata目录}/conf/
cp src/main/resources/registry.conf {seata目录}/conf/
```

### 2.4 启动 Seata Server

**Windows:**
```bash
# 方法1：使用提供的脚本
start-seata-server.bat

# 方法2：直接运行（需要先下载Seata Server）
cd {seata目录}/bin
seata-server.bat -p 8091
```

**Linux/Mac:**
```bash
# 方法1：使用提供的脚本
chmod +x start-seata-server.sh
./start-seata-server.sh

# 方法2：直接运行
cd {seata目录}/bin
./seata-server.sh -p 8091
```

验证 Seata Server 启动成功：
```
访问: http://localhost:8091
或查看日志: INFO Server started...
```

### 2.5 启动应用

确保 Seata Server 已启动后，启动 Spring Boot 应用：

```bash
mvn clean install
mvn spring-boot:run
```

查看日志确认 Seata 客户端注册成功：
```
【Seata全局事务】开始创建用户和订单，XID: 192.168.1.100:8091:123456789
```

## 3. 测试分布式事务

### 3.1 测试正常提交

```bash
curl http://localhost:8080/api/users/test-transaction?rollback=false
```

**预期结果：**
```
{
  "code": 200,
  "message": "操作成功",
  "data": "用户和订单创建成功！用户ID: 4, 订单号: TEST_1737941234567, 订单ID: 6"
}
```

**数据库验证：**
```sql
-- user_db
SELECT * FROM t_user;
-- 应该看到新增的用户

-- order_db
SELECT * FROM t_order;
-- 应该看到新增的订单

-- undo_log
SELECT * FROM undo_log;
-- 应该看到Seata记录的undo日志
```

### 3.2 测试回滚

```bash
curl http://localhost:8080/api/users/test-transaction?rollback=true
```

**预期结果：**
```
{
  "code": 500,
  "message": "操作失败: Data truncation: Data too long for column 'username' at row 1",
  "data": null,
  "rollback": true,
  "note": "由于使用了多数据源，回滚可能不会完全生效，建议使用分布式事务方案"
}
```

**数据库验证：**
```sql
-- user_db
SELECT * FROM t_user;
-- 应该看不到新增的用户（被Seata回滚）

-- order_db
SELECT * FROM t_order;
-- 应该看不到新增的订单（被Seata回滚）

-- undo_log
SELECT * FROM undo_log;
-- 应该看到Seata记录的undo日志，用于回滚
```

### 3.3 查看Seata全局事务日志

在应用日志中可以看到完整的事务流程：

```
【Seata全局事务】开始创建用户和订单，XID: 127.0.0.1:8091:123456789, rollback: true
用户创建成功，用户ID: 4, 用户名: test_user_1737941234567
订单创建成功，订单ID: 6, 订单号: TEST_1737941234567
【Seata全局事务】处理失败，异常类型: DataIntegrityViolationException
【Seata全局事务】将触发全局回滚，XID: 127.0.0.1:8091:123456789
【Seata全局事务】方法执行完成
```

## 4. Seata 工作原理

### 4.1 AT模式（当前使用）

```
第一阶段（准备）：
1. 拦截SQL，解析SQL语义
2. 查询before image（更新前的数据）
3. 执行业务SQL
4. 查询after image（更新后的数据）
5. 生成undo log（包含before和after）
6. 提交本地事务
7. 注册branch到TC（Transaction Coordinator）
8. 报告执行结果

第二阶段（提交）：
1. TC收到所有branch的提交报告
2. 异步删除undo log
3. 事务完成

第二阶段（回滚）：
1. TC收到异常或超时
2. TC向各branch发送回滚指令
3. Branch根据undo log反向执行SQL
4. Branch报告回滚完成
5. TC标记事务回滚
```

### 4.2 事务流程图

```
应用层：
@GlobalTransactional方法
    ↓
TC (Transaction Coordinator, 8091端口)
    ↓ 注册全局事务，生成XID
    ↓
    ↓ 传播XID到所有分支
    ↓
RM (Resource Manager)
    ├── RM1 (user_db): 拦截SQL → 生成undo log → 提交 → 注册branch
    └── RM2 (order_db): 拦截SQL → 生成undo log → 提交 → 注册branch
    ↓
异常发生时：
    ├── RM1: 根据undo log回滚
    └── RM2: 根据undo log回滚
```

## 5. 常见问题

### 5.1 Seata Server连接失败

**问题：**
```
io.seata.core.exception.RmTransactionException: Can not register TC
```

**解决：**
1. 检查 Seata Server 是否启动：`http://localhost:8091`
2. 检查端口是否被占用：`netstat -ano | findstr "8091"`
3. 检查配置文件路径是否正确

### 5.2 undo_log表创建失败

**问题：**
```
Table 'seata.undo_log' doesn't exist
```

**解决：**
确保在所有业务数据库中都执行了 undo_log 表的创建SQL。

### 5.3 全局事务无法回滚

**问题：**
数据没有被回滚

**检查：**
1. 确认使用了 `@GlobalTransactional` 注解
2. 查看日志中的 XID 是否生成
3. 检查 undo_log 表中是否有记录
4. 查看 Seata Server 日志是否有异常

### 5.4 性能问题

**优化建议：**
1. undo_log 定期清理：定期删除已完成事务的undo log
2. 调整Seata参数：
   ```yaml
   seata:
     client:
       rm-report-success-count: 10  # 增加上报间隔
       tm-commit-retry-count: 10
   ```

## 6. 生产环境配置

### 6.1 使用Nacos注册中心

```yaml
seata:
  registry:
    type: nacos
    nacos:
      server-addr: 127.0.0.1:8848
      namespace: seata
      group: SEATA_GROUP
      application: seata-server
  config:
    type: nacos
    nacos:
      server-addr: 127.0.0.1:8848
      namespace: seata
      group: SEATA_GROUP
```

### 6.2 集群部署

```
Seata Server集群：
├── TC1 (192.168.1.101:8091)
├── TC2 (192.168.1.102:8091)
└── TC3 (192.168.1.103:8091)

配置：
seata:
  service:
    grouplist:
      default: 192.168.1.101:8091;192.168.1.102:8091;192.168.1.103:8091
```

### 6.3 监控和告警

集成Prometheus + Grafana监控Seata：
```yaml
seata:
  metrics:
    enabled: true
    exporter-type: prometheus
    registry-type: compact
```

## 7. 总结

### 使用Seata的优势

✅ **真正的跨数据源事务**：多个数据库的操作可以作为一个原子性整体
✅ **自动回滚**：无需手动补偿，Seata自动根据undo log回滚
✅ **高可用**：支持集群部署，避免单点故障
✅ **多种模式**：AT、TCC、SAGA，适应不同业务场景

### 注意事项

⚠️ **性能开销**：每个SQL都会被拦截，生成undo log，有性能损耗
⚠️ **数据库支持**：需要支持事务的数据库（MySQL、PostgreSQL、Oracle等）
⚠️ **配置复杂**：需要部署TC Server，配置文件较多
⚠️ **锁机制**：全局锁可能导致死锁，需要合理设计业务流程

### 适用场景

✅ **推荐使用**：跨数据库的强一致性业务（订单+库存+账户）
✅ **推荐使用**：微服务架构中的分布式事务
⚠️ **谨慎使用**：简单单应用单数据库（直接用@Transactional即可）
