package com.example.studentmanager.service.impl;

import com.example.studentmanager.dto.request.StudentRequest;
import com.example.studentmanager.entity.Student;
import com.example.studentmanager.repository.StudentRepository;
import com.example.studentmanager.service.StudentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public Student create(StudentRequest request) {

        try {

            log.info(
                    "Yêu cầu thêm sinh viên: fullName={}, email={}, gpa={}",
                    request.getFullName(),
                    request.getEmail(),
                    request.getGpa()
            );

            Student student = new Student();

            student.setFullName(request.getFullName());

            student.setEmail(request.getEmail());

            student.setGpa(request.getGpa());

            Student saved = studentRepository.save(student);

            log.info("Thêm sinh viên thành công: {}", saved);

            return saved;

        } catch (Exception e) {

            log.error("Lỗi hệ thống khi thêm sinh viên", e);

            return null;
        }
    }

    @Override
    public List<Student> findAll() {

        log.info("Lấy danh sách sinh viên");

        return studentRepository.findAll();
    }

    @Override
    public Student findById(Long id) {

        log.info("Tìm sinh viên theo id={}", id);

        return studentRepository.findById(id)
                .orElseGet(() -> {

                    log.warn("Không tìm thấy sinh viên id={}", id);

                    return null;
                });
    }

    @Override
    public Student update(Long id, StudentRequest request) {

        log.info("Yêu cầu cập nhật sinh viên id={}", id);

        return studentRepository.findById(id)
                .map(student -> {

                    student.setFullName(request.getFullName());

                    student.setEmail(request.getEmail());

                    student.setGpa(request.getGpa());

                    Student updated =
                            studentRepository.save(student);

                    log.info(
                            "Cập nhật sinh viên thành công: {}",
                            updated
                    );

                    return updated;

                })
                .orElseGet(() -> {
                    log.warn("Không tìm thấy sinh viên id={}", id);

                    return null;
                });
    }

    @Override
    public boolean delete(Long id) {

        log.info("Yêu cầu xóa sinh viên id={}", id);

        if(studentRepository.existsById(id)){

            studentRepository.deleteById(id);

            log.info("Xóa sinh viên thành công id={}", id);

            return true;
        }

        log.warn("Không tìm thấy sinh viên id={}", id);

        return false;
    }
}