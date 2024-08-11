package com.example.qldrl.controllers;

import com.example.qldrl.dto.AdvisorDTO;
import com.example.qldrl.dto.ClassDTO;
import com.example.qldrl.services.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/classes")
public class ClassController {
    private final ClassService classService;

    @Autowired
    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping
    public ResponseEntity<List<ClassDTO>> getAllClasses() {
        List<ClassDTO> clazz = classService.getAllClasses();
        return new ResponseEntity<>(clazz, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ClassDTO> getClassById(@PathVariable String id) {
        Optional<ClassDTO> clazz = classService.getClassById(id);
        if (clazz.isPresent()) {
            return ResponseEntity.ok(clazz.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dept/{deptId}")
    public ResponseEntity<List<ClassDTO>> getStudentByClass(@PathVariable String deptId) {
        List<ClassDTO> students = classService.getClassByDepartment(deptId);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClassDTO> createClass(@RequestBody ClassDTO classDTO) {
        ClassDTO newClass = classService.createClass(classDTO);
        return new ResponseEntity<>(newClass, HttpStatus.CREATED);
    }

    @PostMapping("/import")
    public ResponseEntity<Void> importClasses(@RequestParam("file") MultipartFile file) {
        try {
            classService.importClassesFromExcel(file);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassDTO> updateClass(@PathVariable String id, @RequestBody ClassDTO classDTO) {
        ClassDTO updatedClass = classService.updateClass(id, classDTO);
        return new ResponseEntity<>(updatedClass, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable String id) {
        classService.deleteClass(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
