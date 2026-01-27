# Seata 分布式事务快速启动指南

## 完整步骤（按顺序执行）

### 步骤1：安装依赖

```bash
# 在项目根目录执行
mvn clean install
```

这会下载 Seata 相关依赖，耗时约3-5分钟。

### 步骤2：创建数据库

```bash
# 创建所有数据库（包括Seata数据库）
mysql -u root -pfairy-vip < sql/init.sql

# 创建Seata Server数据库
mysql -u root -pfairy-vip < sql/seata_server.sql
```

**验证Seata客户端连接：**
查看启动日志，应该看到：
```
[INFO] register RM success. RM address=127.0.0.1:8091
[INFO] register TM success. TM address=127.0.0.1:8091
```

### 步骤6：测试分布式事务

#### 测试1：正常提交
```bash
curl http://localhost:8080/api/users/test-transaction?rollback=false
```

**预期输出：**
```json
{
  "code": 200,
  "message": "操作成功",
  "data": "用户和订单创建成功！用户ID: X, 订单号: TEST_xxx, 订单ID: Y"
}
```

#### 测试2：测试回滚（Seata全局回滚）
```bash
curl http://localhost:8080/api/users/test-transaction?rollback=true
```

**预期输出：**
```json
{
  "code": 500,
  "message": "操作失败: Data truncation: Data too long for column 'username' at row 1",
  "data": null,
  "rollback": true,
  "note": "由于使用了多数据源，回滚可能不会完全生效，建议使用分布式事务方案"
}
```

**验证回滚：**
```sql
-- 连接MySQL
mysql -u root -pfairy-vip

-- 查看user_db
USE user_db;
SELECT * FROM t_user;
-- rollback=true时，应该看不到新增的用户

-- 查看order_db
USE order_db;
SELECT * FROM t_order;
-- rollback=true时，应该看不到新增的订单

-- 查看undo_log（Seata记录的回滚日志）
USE user_db;
SELECT * FROM undo_log;
-- 应该看到Seata的undo日志记录
```

## 验证Seata工作正常

### 检查点1：XID生成

在应用日志中搜索 XID：
```
grep "XID" logs/application.log
```

应该看到：
```
【Seata全局事务】开始创建用户和订单，XID: 127.0.0.1:8091:1234567890
```

### 检查点2：Undo Log记录

```sql
USE user_db;
SELECT * FROM undo_log ORDER BY log_created DESC LIMIT 10;

USE order_db;
SELECT * FROM undo_log ORDER BY log_created DESC LIMIT 10;
```

应该看到最近的undo日志记录。

### 检查点3：全局事务表

```sql
USE seata;

-- 全局事务
SELECT * FROM global_table;

-- 分支事务
SELECT * FROM branch_table;
```

## 对比：有Seata vs 无Seata

### 场景：创建用户和订单后抛出异常

#### 无Seata（本地事务）
```
1. 创建用户 → user_db事务提交 ✅
2. 创建订单 → order_db事务提交 ✅
3. 抛出异常
结果：用户和订单都创建成功，无法回滚 ❌
```

#### 有Seata（分布式事务）
```
1. 创建用户 → 记录undo log → user_db事务提交 ✅
2. 创建订单 → 记录undo log → order_db事务提交 ✅
3. 抛出异常
4. Seata TC协调回滚
5. user_db根据undo log回滚 ✅
6. order_db根据undo log回滚 ✅
结果：用户和订单都回滚成功 ✅
```

## 常见问题快速解决

### 问题1：Maven下载依赖慢

**解决：**
使用阿里云镜像：
```xml
<mirror>
  <id>aliyun</id>
  <mirrorOf>central</mirrorOf>
  <name>Aliyun Maven Mirror</name>
  <url>https://maven.aliyun.com/repository/public</url>
</mirror>
```

### 问题2：Seata Server启动失败

**错误：**
```
Address already in use
```

**解决：**
```bash
# Windows
netstat -ano | findstr "8091"
taskkill /F /PID {占用进程的PID}

# Linux/Mac
lsof -i:8091
kill -9 {占用进程的PID}
```

### 问题3：应用启动失败

**错误：**
```
The import io.seata cannot be resolved
```

**原因：** Maven依赖未下载完成

**解决：**
```bash
mvn clean install
# 等待下载完成后重新启动应用
```

### 问题4：数据库连接失败

**错误：**
```
Access denied for user 'root'@'localhost'
```

**解决：**
检查application.yml中的密码是否正确：
```yaml
spring:
  datasource:
    user:
      password: fairy-vip
    order:
      password: fairy-vip
```

## 监控和调试

### 查看Seata Server日志

```
{Seata目录}/logs/seata-server.log
```

### 查看应用日志

```
tail -f logs/application.log | grep Seata
```

### 查看数据库事务状态

```sql
-- 当前活跃的全局事务
SELECT * FROM seata.global_table WHERE status = 0;

-- 当前活跃的分支事务
SELECT * FROM seata.branch_table WHERE status = 0;
```

## 总结

### 快速验证流程

```
1. mvn clean install (下载Seata依赖)
2. mysql -u root -pfairy-vip < sql/init.sql (创建数据库)
3. mysql -u root -pfairy-vip < sql/seata_server.sql (创建Seata表)
4. 启动Seata Server (cd seata-server/bin && ./seata-server.sh)
5. mvn spring-boot:run (启动应用)
6. curl http://localhost:8080/api/users/test-transaction?rollback=true (测试)
```

### 关键验证点

✅ Seata Server 运行在 8091 端口
✅ 应用日志中看到 XID 生成
✅ undo_log 表有记录
✅ 回滚时数据库没有脏数据
✅ 日志中有全局回滚的记录

### 预期效果

- **正常提交**：用户和订单都创建成功 ✅
- **异常回滚**：用户和订单都回滚成功 ✅
- **跨数据源**：user_db 和 order_db 的事务统一管理 ✅
- **自动补偿**：无需手动删除数据，Seata自动回滚 ✅
