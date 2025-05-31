package org.example.springbooauditor.service;

import org.example.springbooauditor.repisitory.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("securityService")
public class SecurityService {

  @Autowired
  CourseRepository courseRepository;

  // 判断用户是否为课程所有者
  public boolean isCourseOwner(Long courseId, String username) {
    // 根据courseId查询数据库，验证username是否为课程所有者
    // 示例逻辑，实际需根据业务实现
    return courseRepository.findById(courseId)
        .map(course -> course.getCreatedBy().equals(username))
        .orElse(false);
  }
}
