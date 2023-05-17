package com.service.CourseService.Response;


import com.service.CourseService.Entity.Course;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponse {
    private List<Course> course;
}
