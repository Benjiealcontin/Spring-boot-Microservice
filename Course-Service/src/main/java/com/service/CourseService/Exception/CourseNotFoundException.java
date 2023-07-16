package com.service.CourseService.Exception;

public class CourseNotFoundException extends RuntimeException {

    private Long courseId;

    public CourseNotFoundException(Long courseId) {
        super("Course not found with id: " + courseId);
        this.courseId = courseId;
    }

    public Long getCourseId() {
        return courseId;
    }
}

