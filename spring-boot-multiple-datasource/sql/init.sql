-- 创建用户数据库
CREATE DATABASE IF NOT EXISTS db_user DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE db_user;

-- 创建用户表
CREATE TABLE IF NOT EXISTS t_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '电话',
    age INT COMMENT '年龄',
    address VARCHAR(200) COMMENT '地址',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_username (username),
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 插入测试数据
INSERT INTO t_user (username, email, phone, age, address) VALUES
('zhangsan', 'zhangsan@example.com', '13800138001', 28, '北京市海淀区'),
('lisi', 'lisi@example.com', '13800138002', 30, '上海市浦东新区'),
('wangwu', 'wangwu@example.com', '13800138003', 25, '广州市天河区');

-- 创建订单数据库
CREATE DATABASE IF NOT EXISTS db_order DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE db_order;

-- 创建订单表
CREATE TABLE IF NOT EXISTS t_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '订单ID',
    order_no VARCHAR(50) NOT NULL UNIQUE COMMENT '订单号',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    product_name VARCHAR(200) COMMENT '产品名称',
    quantity INT DEFAULT 1 COMMENT '数量',
    total_price DECIMAL(10, 2) COMMENT '总价格',
    status VARCHAR(20) DEFAULT 'PENDING' COMMENT '订单状态：PENDING-待支付，PAID-已支付，SHIPPED-已发货，COMPLETED-已完成，CANCELLED-已取消',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_order_no (order_no),
    INDEX idx_user_id (user_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 插入测试数据
INSERT INTO t_order (order_no, user_id, product_name, quantity, total_price, status) VALUES
('ORD202401270001', 1, 'iPhone 15 Pro', 1, 8999.00, 'PAID'),
('ORD202401270002', 1, 'MacBook Pro', 1, 14999.00, 'SHIPPED'),
('ORD202401270003', 2, 'AirPods Pro', 2, 3598.00, 'PAID'),
('ORD202401270004', 3, 'iPad Air', 1, 4799.00, 'COMPLETED'),
('ORD202401270005', 2, 'Apple Watch', 1, 3199.00, 'PENDING');
