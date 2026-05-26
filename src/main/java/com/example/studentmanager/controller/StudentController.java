package com.example.studentmanager.controller;

import com.example.studentmanager.dto.request.StudentRequest;
import com.example.studentmanager.dto.response.ApiResponse;
import com.example.studentmanager.entity.Student;
import com.example.studentmanager.service.StudentService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Slf4j
public class StudentController {

    private final StudentService studentService;

    // thêm
    @PostMapping
    public ResponseEntity<?> create(

            @Valid @RequestBody StudentRequest request,

            BindingResult result
    ){

        // validation fail
        if(result.hasErrors()){
            result.getFieldErrors()
                    .forEach(error ->
                            log.warn("Validation lỗi field={} , message={}", error.getField(), error.getDefaultMessage())
                    );

            return ResponseEntity.badRequest().body(ApiResponse.builder()
                                    .success(false)
                                    .message("Validation failed")
                                    .build()
                    );
        }

        Student student = studentService.create(request);
        return ResponseEntity.ok(ApiResponse.builder()
                        .success(true)
                        .message("Thêm sinh viên thành công")
                        .data(student)
                        .build()
        );
    }

    // lấy tất cả
    @GetMapping
    public ResponseEntity<ApiResponse<List<Student>>> findAll(){
        return ResponseEntity.ok(ApiResponse.<List<Student>>builder()
                        .success(true)
                        .message("Danh sách sinh viên")
                        .data(studentService.findAll())
                        .build()
        );
    }

    // lấy theo Id
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Student student = studentService.findById(id);
        if(student == null){
            return ResponseEntity.badRequest().body(ApiResponse.builder().success(false).message("Không tìm thấy sinh viên").build());
        }

        return ResponseEntity.ok(ApiResponse.builder().success(true).message("Tìm sinh viên thành công").data(student).build());
    }

    // Sửa
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequest request,
            BindingResult result
    ){
        if(result.hasErrors()){
            result.getFieldErrors().forEach(error -> log.warn("Validation lỗi field={} , message={}", error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body("Validation failed");
        }
        Student student = studentService.update(id, request);
        if(student == null){
            return ResponseEntity.badRequest().body("Không tìm thấy sinh viên");
        }
        return ResponseEntity.ok(student);
    }

    // Xóa
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Long id
    ){
        boolean deleted = studentService.delete(id);
        if(!deleted){
            return ResponseEntity.badRequest().body("Không tìm thấy sinh viên");
        }
        return ResponseEntity.ok("Xóa thành công");
    }
}