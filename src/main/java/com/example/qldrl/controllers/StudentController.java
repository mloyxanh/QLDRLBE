package com.example.qldrl.controllers;

import com.example.qldrl.dto.StudentDTO;
import com.example.qldrl.entities.Student;
import com.example.qldrl.functions.CustomPasswordEncoder;
import com.example.qldrl.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> students = studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable String id) {
        Optional<StudentDTO> student = studentService.getStudentById(id);
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userName}")
    public ResponseEntity<StudentDTO> getStudentByUser(@PathVariable String userName) {
        Optional<StudentDTO> student = studentService.getStudentByUser(userName);
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/class/{clazzId}")
    public ResponseEntity<List<StudentDTO>> getStudentByClass(@PathVariable String clazzId) {
        List<StudentDTO> students = studentService.getStudentsByClass(clazzId);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PostMapping("/import")
    public ResponseEntity<Void> importStudents(@RequestParam("file") MultipartFile file) {
        try {
            studentService.importStudentsFromExcel(file);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        studentDTO.setId(CustomPasswordEncoder.getCustomStudentId(studentDTO.getSId()));
        StudentDTO newStudent = studentService.createStudent(studentDTO);
        return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateClass(@PathVariable String id, @RequestBody StudentDTO studentDTO) {
        studentDTO.setId(CustomPasswordEncoder.getCustomStudentId(studentDTO.getSId()));
        StudentDTO updatedStudent = studentService.updateStudent(id, studentDTO);
        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
