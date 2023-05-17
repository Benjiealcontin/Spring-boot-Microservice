package com.service.StudentService.Controller;



import com.service.StudentService.Request.StudentRequest;

import com.service.StudentService.Response.StudentResponse;
import com.service.StudentService.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/student")
public class MainController {
    @Autowired
    private StudentService studentService;

    //Create Student
    @PostMapping("/add")
    public ResponseEntity<?> createStudent(@RequestBody StudentRequest studentRequest) {
        try {
            return new ResponseEntity<>(studentService.AddStudent(studentRequest), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //GetAll student with courses
    @GetMapping("/studentlist")
    public ResponseEntity<?> getallStudent() {
        try {
            return new ResponseEntity<>(studentService.AllStudent(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    //Find Student by Id
    @GetMapping("{id}")
    public ResponseEntity<StudentResponse> getStudent(@PathVariable("id") Long id){
        StudentResponse studentResponse = studentService.getStudentbyId(id);
        return ResponseEntity.ok(studentResponse);
    }
}
