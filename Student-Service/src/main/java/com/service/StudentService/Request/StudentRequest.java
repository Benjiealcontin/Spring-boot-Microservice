package com.service.StudentService.Request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest {
    private String firstName;
    private String lastName;
    private String email;
    private Long courseId;
    private String password;
    private String roles;
}