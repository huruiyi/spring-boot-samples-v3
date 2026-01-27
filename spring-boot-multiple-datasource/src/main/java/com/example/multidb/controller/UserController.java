package com.example.multidb.controller;

import com.example.multidb.dto.TransactionTestRequest;
import com.example.multidb.entity.User;
import com.example.multidb.service.TransactionTestService;
import com.example.multidb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionTestService transactionTestService;

    /**
     * 根据ID查询用户
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "查询成功");
        response.put("data", user);
        return ResponseEntity.ok(response);
    }

    /**
     * 查询所有用户
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> findAll() {
        List<User> users = userService.findAll();
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "查询成功");
        response.put("data", users);
        return ResponseEntity.ok(response);
    }

    /**
     * 保存用户，返回插入后的自增ID
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> save(@RequestBody User user) {
        Long userId = userService.save(user);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "保存成功");
        response.put("data", userId);
        response.put("userId", userId);
        return ResponseEntity.ok(response);
    }

    /**
     * 更新用户
     */
    @PutMapping
    public ResponseEntity<Map<String, Object>> update(@RequestBody User user) {
        int result = userService.update(user);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "更新成功");
        response.put("data", result);
        return ResponseEntity.ok(response);
    }

    /**
     * 根据ID删除用户
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteById(@PathVariable Long id) {
        int result = userService.deleteById(id);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "删除成功");
        response.put("data", result);
        return ResponseEntity.ok(response);
    }

    /**
     * 根据用户名查询用户
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<Map<String, Object>> findByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "查询成功");
        response.put("data", user);
        return ResponseEntity.ok(response);
    }

    /**
     * 事务测试：创建用户和订单（使用自定义参数）
     * 用于测试多数据源的事务提交与回滚
     *
     * 参数说明：
     * - rollback: true=测试回滚（会抛出异常），false=正常提交
     * - username: 用户名
     * - email: 邮箱
     * - phone: 电话
     * - orderNo: 订单号
     * - productName: 产品名称
     * - quantity: 数量
     * - totalPrice: 总价格
     */
    @PostMapping("/create-with-order")
    public ResponseEntity<Map<String, Object>> createUserWithOrder(@RequestBody TransactionTestRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String result = transactionTestService.createUserAndOrder(request);
            response.put("code", 200);
            response.put("message", "操作成功");
            response.put("data", result);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "操作失败: " + e.getMessage());
            response.put("data", null);
            response.put("rollback", request.getRollback());
        }
        return ResponseEntity.ok(response);
    }

    /**
     * 事务测试：创建用户和订单（使用写死的数据）
     * 用于快速测试事务提交与回滚
     *
     * 参数说明：
     * - rollback: true=测试回滚（会抛出异常），false=正常提交
     */
    @GetMapping("/test-transaction")
    public ResponseEntity<Map<String, Object>> testTransaction(@RequestParam(defaultValue = "false") Boolean rollback) {
        Map<String, Object> response = new HashMap<>();
        try {
            String result = transactionTestService.createWithHardcodedData(rollback);
            response.put("code", 200);
            response.put("message", "操作成功");
            response.put("data", result);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "操作失败: " + e.getMessage());
            response.put("data", null);
            response.put("rollback", rollback);
            response.put("note", "由于使用了多数据源，回滚可能不会完全生效，建议使用分布式事务方案");
        }
        return ResponseEntity.ok(response);
    }
}
