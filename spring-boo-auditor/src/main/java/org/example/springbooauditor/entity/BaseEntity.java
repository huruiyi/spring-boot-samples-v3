package org.example.springbooauditor.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class) // 启用审计监听器
public class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @CreatedBy
  @Column(name = "created_by", nullable = false, updatable = false)
  private String createdBy; // 创建者用户

  @CreatedDate
  @Column(name = "created_time", nullable = false, updatable = false)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") // 响应时格式化
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 接收请求时解析
  private LocalDateTime createdDate; // 创建时间

  @LastModifiedBy
  @Column(name = "last_modified_by", nullable = false)
  private String lastModifiedBy; // 最后修改者用户

  @LastModifiedDate
  @Column(name = "last_modified_time", nullable = false)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") // 响应时格式化
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 接收请求时解析
  private LocalDateTime lastModifiedDate; // 最后修改时间

}
