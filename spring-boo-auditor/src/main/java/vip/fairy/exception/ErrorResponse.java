package vip.fairy.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用错误响应类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // 只包含非空字段
public class ErrorResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    // 错误状态码（如400、401、500）
    private Integer status;

    // 错误码（业务自定义，如USER_NOT_FOUND、INVALID_PARAM）
    private String code;

    // 错误消息（面向用户）
    private String message;

    // 错误详情（面向开发人员）
    private String details;

    // 时间戳
    private Long timestamp = System.currentTimeMillis();

    // 附加数据（如验证失败的字段信息）
    private Map<String, Object> data;

    // 构造函数重载
    public ErrorResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public ErrorResponse(Integer status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    // 链式方法 - 添加附加数据
    public ErrorResponse addData(String key, Object value) {
        if (this.data == null) {
            this.data = new HashMap<>();
        }
        this.data.put(key, value);
        return this;
    }
}
