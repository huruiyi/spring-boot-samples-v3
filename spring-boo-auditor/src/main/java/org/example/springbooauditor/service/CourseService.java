package org.example.springbooauditor.service;

import java.util.List;
import java.util.Optional;
import org.example.springbooauditor.entity.Course;

public interface CourseService {
    Course save(Course course);
    List<Course> findAll();
    Optional<Course> findById(Long id);
    void deleteById(Long id);
}
