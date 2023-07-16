package com.service.CourseService.Controller;

import com.service.CourseService.Entity.Course;
import com.service.CourseService.Repository.CourseRepository;
import com.service.CourseService.Request.CourseRequest;
import com.service.CourseService.Response.CourseResponse;
import com.service.CourseService.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/course")
public class MainController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;


	@GetMapping("/")
	public ResponseEntity<?> create() {
		try {
			//TODO Implement Your Logic To Save Data And Return Result Through ResponseEntity
			return new ResponseEntity<>("Create Result", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

    //Create Course
    @PostMapping("/add")
    public ResponseEntity<?> createCourse(@RequestBody CourseRequest courseRequest) {
        try {
            return new ResponseEntity<>(courseService.AddCourse(courseRequest), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //GetAllCourse
    @GetMapping("/courselist")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<CourseResponse> courseList() {
        try {
            List<Course> course = courseService.getCourseList();
            CourseResponse response = new CourseResponse();
            response.setCourse(course);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //GetCourseById
    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<Course> getAllCourseById(@PathVariable Long courseId) {
        try {
            Course course = courseService.getCourseById(courseId);
            if (course != null) {
                return ResponseEntity.ok(course);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable("id") Long id){
        Course course = courseService.getCourseById2(id);
        return ResponseEntity.ok(course);
    }
}
