package com.example.studentmanager.service;

import com.example.studentmanager.dto.request.StudentRequest;
import com.example.studentmanager.entity.Student;

import java.util.List;

public interface StudentService {

    Student create(StudentRequest request);

    List<Student> findAll();

    Student findById(Long id);

    Student update(Long id, StudentRequest request);

    boolean delete(Long id);
}