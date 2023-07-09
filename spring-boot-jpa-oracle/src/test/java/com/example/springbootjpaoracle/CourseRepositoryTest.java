package com.example.springbootjpaoracle;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.springbootjpaoracle.entity.Course;
import com.example.springbootjpaoracle.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(classes = JpaOracleApplication.class)
public class CourseRepositoryTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CourseRepository repository;

    @Test
    public void findById_basic() {
        Course course = repository.findById(10001L);
        assertEquals("JPA in 50 Steps", course.getName());
    }

    @Test
    @DirtiesContext
    public void deleteById_basic() {
        repository.deleteById(10002L);
        assertNull(repository.findById(10002L));
    }

    @Test
    @DirtiesContext
    public void save_basic() {

        // get a course
        Course course = repository.findById(10001L);
        assertEquals("JPA in 50 Steps", course.getName());

        // update details
        course.setName("JPA in 50 Steps - Updated");
        repository.save(course);

        // check the value
        Course course1 = repository.findById(10001L);
        assertEquals("JPA in 50 Steps - Updated", course1.getName());
    }
}
