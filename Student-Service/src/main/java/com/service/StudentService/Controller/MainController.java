package com.service.StudentService.Controller;



import com.service.StudentService.Request.StudentRequest;

import com.service.StudentService.Response.StudentResponse;
import com.service.StudentService.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/student")
public class MainController {
    @Autowired
    private StudentService studentService;

    //Create Student
    @PostMapping("/add")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<?> createStudent(@RequestBody StudentRequest studentRequest) {
        try {
            return new ResponseEntity<>(studentService.AddStudent(studentRequest), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    GetAll student with courses
    @GetMapping("/studentlist")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<?> getallStudent(@RequestHeader("Authorization") String token) {
        try {
            List<StudentResponse> studentResponses = studentService.getAllStudentsWithCourses(token);
            return ResponseEntity.ok(studentResponses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    //Find Student by Id
//    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('client_admin')")
//    public ResponseEntity<StudentResponse> getStudentWithCourse(@PathVariable Long id, @RequestHeader("Authorization") String token) {
//        StudentResponse studentResponse = studentService.getStudentWithCourse(id, token);
//        if (studentResponse != null) {
//            return ResponseEntity.ok(studentResponse);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
}