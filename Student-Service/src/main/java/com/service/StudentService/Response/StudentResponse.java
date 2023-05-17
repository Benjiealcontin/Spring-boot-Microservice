package com.service.StudentService.Response;

import com.service.CourseService.Entity.Course;
import com.service.CourseService.Response.CourseResponse;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {
    private Long id;
    private String token;
    private String firstName;
    private String lastName;
    private String email;
    private List<Course> course;
}
