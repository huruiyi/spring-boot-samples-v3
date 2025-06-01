package vip.fairy.service;

import java.util.List;
import java.util.Optional;
import vip.fairy.entity.Course;

public interface CourseService {
    Course save(Course course);
    List<Course> findAll();
    Optional<Course> findById(Long id);
    void deleteById(Long id);
}
