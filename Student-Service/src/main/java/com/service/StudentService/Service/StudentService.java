package com.service.StudentService.Service;


import com.service.CourseService.Entity.Course;
import com.service.StudentService.Entity.Student;
import com.service.StudentService.Repository.StudentRepository;
import com.service.StudentService.Request.StudentRequest;
import com.service.StudentService.Response.MessageResponse;
import com.service.StudentService.Response.StudentResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private WebClient.Builder webClientBuilder;


    //Add Student
    public MessageResponse AddStudent(StudentRequest studentRequest) {
        Student student = Student.builder()
                .email(studentRequest.getEmail())
                .firstName(studentRequest.getFirstName())
                .lastName(studentRequest.getLastName())
                .password(studentRequest.getPassword())
                .build();
        studentRepository.save(student);
        return new MessageResponse("Student Successfully Added!");
    }

    //GetAll student with courses
    public List<StudentResponse> AllStudent() {
        try {
            List<Student> studentEntities = studentRepository.findAll();
            List<StudentResponse> studentResponses = new ArrayList<>();
            for (Student studentEntity : studentEntities) {
                StudentResponse studentResponse = modelMapper.map(studentEntity, StudentResponse.class);
                Long courseId = studentEntity.getCourseId(); // Get the courseId from the Student entity
                Course course = webClientBuilder.build().get()
                        .uri("http://Course-Service/api/course/course/{courseId}", courseId)
                        .retrieve()
                        .bodyToMono(Course.class)
                        .block();
                List<Course> courseList = new ArrayList<>();
                courseList.add(course);
                studentResponse.setCourse(courseList);
                studentResponses.add(studentResponse);
            }
            return studentResponses;
        } catch (Exception e) {
            // Log the error or do something else with it
            throw new RuntimeException("An error occurred while fetching Student: " + e.getMessage(), e);
        }
    }

    //Get student by Id
    public StudentResponse getStudentbyId(Long id) {
        Student studentEntity = studentRepository.findByid(id);
        StudentResponse studentResponse = modelMapper.map(studentEntity, StudentResponse.class);

        Course course = webClientBuilder.build().get()
                .uri("http://Course-Service/api/course/" + studentEntity.getCourseId())
                .retrieve()
                .bodyToMono(Course.class)
                .block();

        List<Course> courses = new ArrayList<>();
        courses.add(course);
        studentResponse.setCourse(courses);

        return studentResponse;
    }
}
