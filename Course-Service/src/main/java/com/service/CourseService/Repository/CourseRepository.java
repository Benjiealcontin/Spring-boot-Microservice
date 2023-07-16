package com.service.CourseService.Repository;

import com.service.CourseService.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {

    Course findByid(Long id);
}