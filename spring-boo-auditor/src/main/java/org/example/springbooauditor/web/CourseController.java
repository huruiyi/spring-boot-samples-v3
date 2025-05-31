package org.example.springbooauditor.web;

import java.util.List;
import java.util.Optional;
import org.example.springbooauditor.entity.Course;
import org.example.springbooauditor.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

  private final CourseService courseService;

  public CourseController(CourseService courseService) {
    this.courseService = courseService;
  }

  // 创建课程
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  //@PreAuthorize("hasRole('ADMIN') && #course.price < 1000") // 方法级：额外验证价格
  public Course createCourse(@RequestBody Course course) {
    return courseService.save(course);
  }

  // 获取所有课程
  @GetMapping
  public List<Course> getAllCourses() {
    return courseService.findAll();
  }

  // 根据 ID 获取课程
  @GetMapping("/{id}")
  public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
    Optional<Course> course = courseService.findById(id);
    return course.map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  // 更新课程
  @PutMapping("/{id}")
  public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course courseDetails) {
    return courseService.findById(id)
        .map(existingCourse -> {
          // 更新课程信息
          existingCourse.setName(courseDetails.getName());
          existingCourse.setDescription(courseDetails.getDescription());
          // 保存更新后的课程
          Course updatedCourse = courseService.save(existingCourse);
          return ResponseEntity.ok(updatedCourse);
        })
        .orElse(ResponseEntity.notFound().build());
  }

  // 删除课程
  // 只有课程所有者或ADMIN可以删除课程
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN') or @securityService.isCourseOwner(#id, authentication.name)")
  public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
    return courseService.findById(id)
        .map(course -> {
          courseService.deleteById(id);
          return ResponseEntity.noContent().<Void>build();
        })
        .orElse(ResponseEntity.notFound().build());
  }
}
