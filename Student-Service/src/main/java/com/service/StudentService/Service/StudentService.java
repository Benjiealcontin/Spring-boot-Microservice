package com.service.StudentService.Service;


import com.service.CourseService.Entity.Course;
import com.service.StudentService.Entity.Student;
import com.service.StudentService.Exception.CourseServiceException;
import com.service.StudentService.Exception.StudentDataRetrievalException;
import com.service.StudentService.Repository.StudentRepository;
import com.service.StudentService.Request.StudentRequest;
import com.service.StudentService.Response.MessageResponse;
import com.service.StudentService.Response.StudentResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private WebClient.Builder webClientBuilder;

    private final DiscoveryClient discoveryClient;

    public StudentService(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

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
//    public List<StudentResponse> AllStudent(String token) {
//        try {
//            List<Student> studentEntities = studentRepository.findAll();
//            List<StudentResponse> studentResponses = new ArrayList<>();
//
//            for (Student studentEntity : studentEntities) {
//                StudentResponse studentResponse = modelMapper.map(studentEntity, StudentResponse.class);
//                Long courseId = studentEntity.getCourseId(); // Get the courseId from the Student entity
//                try {
//
//
//                // Make the request to the external API with the token in the header
//                Course course = WebClient.create()
//                        .get()
//                        .uri("http://Course-Service/api/course/course/{courseId}", courseId)
//                        .headers(headers -> headers.setBearerAuth(token))
//                        .retrieve()
//                        .bodyToMono(Course.class)
//                        .block();
//
//                List<Course> courseList = new ArrayList<>();
//                courseList.add(course);
//                studentResponse.setCourse(courseList);
//                studentResponses.add(studentResponse);
//
//                } catch (WebClientResponseException ex) {
//                    // Handle the specific error from the external API
//                    String errorMessage = "Failed to fetch course with ID " + courseId + ": " + ex.getRawStatusCode()
//                            + " - " + ex.getStatusText();
//                    throw new CourseServiceException(errorMessage, ex);
//                }
//            }
//            return studentResponses;
//        } catch (Exception e) {
//            // Log the error or do something else with it
//            throw new StudentDataRetrievalException("An error occurred while fetching Student: " + e.getMessage(), e);
//        }
//    }

    public List<StudentResponse> getAllStudentsWithCourses(String token) {
        List<Student> students = studentRepository.findAll();
        List<StudentResponse> studentResponses = new ArrayList<>();

        for (Student student : students) {
            StudentResponse studentResponse = new StudentResponse();
            studentResponse.setId(student.getId());
            studentResponse.setToken(token);
            studentResponse.setEmail(student.getEmail());
            studentResponse.setLastName(student.getLastName());
            studentResponse.setFirstName(student.getFirstName());

            Long courseId = student.getCourseId();
            if (courseId != null) {
                Course course = fetchCourseInformation(courseId, token);
                studentResponse.setCourse(course);
            }

            studentResponses.add(studentResponse);
        }

        return studentResponses;
    }

    //Get student by Id
    public StudentResponse getStudentWithCourse(Long studentId, String token) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            StudentResponse studentResponse = new StudentResponse();
            studentResponse.setId(student.getId());
            studentResponse.setToken(token);
            studentResponse.setEmail(student.getEmail());
            studentResponse.setLastName(student.getLastName());
            studentResponse.setFirstName(student.getFirstName());

            Long courseId = student.getCourseId();
            if (courseId != null) {
                Course course = fetchCourseInformation(courseId, token);
                studentResponse.setCourse(course);
            }

            return studentResponse;
        } else {
            return null; // Student not found
        }
    }

    private Course fetchCourseInformation(Long courseId, String token) {
        List<ServiceInstance> instances = discoveryClient.getInstances("course-service");
        if (instances.isEmpty()) {
            // Handle error: Course-Service not found or not registered with Eureka
            return null;
        }

        ServiceInstance courseServiceInstance = instances.get(0); // Assuming single instance
        String courseServiceBaseUrl = "http://" + courseServiceInstance.getHost() + ":" + courseServiceInstance.getPort();

        WebClient webClient = WebClient.builder()
                .baseUrl(courseServiceBaseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, token)
                .build();

        Mono<Course> courseMono = webClient.get()
                .uri("/api/course/course/{courseId}", courseId)
                .retrieve()
                .bodyToMono(Course.class);

        return courseMono.block();
    }
}