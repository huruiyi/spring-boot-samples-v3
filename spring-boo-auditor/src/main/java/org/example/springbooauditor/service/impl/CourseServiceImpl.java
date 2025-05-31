package org.example.springbooauditor.service.impl;

import java.util.List;
import java.util.Optional;
import org.example.springbooauditor.entity.Course;
import org.example.springbooauditor.repisitory.CourseRepository;
import org.example.springbooauditor.service.CourseService;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

  private final CourseRepository courseRepository;

  public CourseServiceImpl(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Override
  public Course save(Course course) {
    return courseRepository.save(course);
  }

  @Override
  public List<Course> findAll() {
    return courseRepository.findAll();
  }

  @Override
  public Optional<Course> findById(Long id) {
    return courseRepository.findById(id);
  }

  @Override
  public void deleteById(Long id) {
    courseRepository.deleteById(id);
  }
}
