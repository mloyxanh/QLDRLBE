package com.example.qldrl.controllers;

import com.example.qldrl.dto.SemesterDTO;
import com.example.qldrl.services.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/semesters")
public class SemesterController {

    private final SemesterService semesterService;

    @Autowired
    public SemesterController(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    @GetMapping
    public ResponseEntity<List<SemesterDTO>> getAllSemesters() {
        List<SemesterDTO> semesters = semesterService.getAllSemesters();
        return new ResponseEntity<>(semesters, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SemesterDTO> getSemesterById(@PathVariable Integer id) {
        Optional<SemesterDTO> semester = semesterService.getSemesterById(id);
        return semester.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SemesterDTO> createSemester(@RequestBody SemesterDTO semesterDTO) {
        SemesterDTO newSemester = semesterService.createSemester(semesterDTO);
        return new ResponseEntity<>(newSemester, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SemesterDTO> updateSemester(@PathVariable Integer id, @RequestBody SemesterDTO semesterDTO) {
        try {
            SemesterDTO updatedSemester = semesterService.updateSemester(id, semesterDTO);
            return new ResponseEntity<>(updatedSemester, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSemester(@PathVariable Integer id) {
        try {
            semesterService.deleteSemester(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
