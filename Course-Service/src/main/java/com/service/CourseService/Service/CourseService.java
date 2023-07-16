package com.service.CourseService.Service;


import com.service.CourseService.Entity.Course;
import com.service.CourseService.Exception.CourseNotFoundException;
import com.service.CourseService.Repository.CourseRepository;
import com.service.CourseService.Request.CourseRequest;
import com.service.CourseService.Response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;


    //Add department
    public MessageResponse AddCourse(CourseRequest courseRequest) {
        Course course = Course.builder()
                .courseCode(courseRequest.getCourseCode())
                .courseName(courseRequest.getCourseName())
                .build();
        courseRepository.save(course);

        return new MessageResponse("Department created successfully!");
    }


    //GetALlCourses
    public List<Course> getCourseList() {
        try {
            return courseRepository.findAll();
        } catch (Exception e) {
            // Log the error or do something else with it
            throw new RuntimeException("An error occurred while fetching Department: " + e.getMessage(), e);
        }
    }

    //GetCoursesByID
    public Course getCourseById(Long courseId) {
        try {
            Optional<Course> courseOptional = courseRepository.findById(courseId);
            return courseOptional.orElseThrow(() -> new CourseNotFoundException(courseId));
        } catch (Exception e) {
            // Log the error or do something else with it
            throw new RuntimeException("An error occurred while fetching Course: " + e.getMessage(), e);
        }
    }


    //Find department by ID
    public Course getCourseById2(Long id) {
        try {
            return courseRepository.findByid(id);
        } catch (Exception e) {
            // Log the error or do something else with it
            throw new RuntimeException("An error occurred while fetching Department: " + e.getMessage(),
                    e);
        }

    }
}

