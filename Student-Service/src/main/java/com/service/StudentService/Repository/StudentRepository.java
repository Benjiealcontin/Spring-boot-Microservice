package com.service.StudentService.Repository;

import com.service.StudentService.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {

    Student findByid(Long id);
}