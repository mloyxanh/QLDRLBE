package com.example.qldrl.controllers;

import com.example.qldrl.dto.ClassDTO;
import com.example.qldrl.dto.DepartmentDTO;
import com.example.qldrl.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        List<DepartmentDTO> departments = departmentService.getAllDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDeptById(@PathVariable String id) {
        System.out.println(id);
        Optional<DepartmentDTO> departmentDTO = departmentService.getDeptById(id);
        if (departmentDTO.isPresent()) {
            return ResponseEntity.ok(departmentDTO.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO newDepartment = departmentService.createDepartment(departmentDTO);
        return new ResponseEntity<>(newDepartment, HttpStatus.CREATED);
    }

    @PostMapping("/import")
    public ResponseEntity<Void> importDepartments(@RequestParam("file") MultipartFile file) {
        try {
            departmentService.importDepartmentsFromExcel(file);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable String id, @RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO updatedDepartment = departmentService.updateDepartment(id, departmentDTO);
        return new ResponseEntity<>(updatedDepartment, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable String id) {
        departmentService.deleteDepartment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}