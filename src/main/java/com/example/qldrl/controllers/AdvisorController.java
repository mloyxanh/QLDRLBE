package com.example.qldrl.controllers;

import com.example.qldrl.dto.AdvisorDTO;
import com.example.qldrl.dto.StudentDTO;
import com.example.qldrl.functions.CustomPasswordEncoder;
import com.example.qldrl.services.AdvisorService;
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
@RequestMapping("/api/advisors")
public class AdvisorController {
    private final AdvisorService advisorService;

    @Autowired
    public AdvisorController(AdvisorService advisorService) {
        this.advisorService = advisorService;
    }

    @GetMapping
    public ResponseEntity<List<AdvisorDTO>> getAllAdvisors() {
        List<AdvisorDTO> adv = advisorService.getAllAdvisors();
        return new ResponseEntity<>(adv, HttpStatus.OK);
    }

    @GetMapping("/dept/{departmentId}")
    public ResponseEntity<List<AdvisorDTO>> getAdvisorsByDepartment(@PathVariable String departmentId) {
        List<AdvisorDTO> advisors = advisorService.getStudentsByDepartment(departmentId);
        return new ResponseEntity<>(advisors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvisorDTO> getAdvisorById(@PathVariable String id) {
        Optional<AdvisorDTO> advisor = advisorService.getAdvisorById(id);
        if (advisor.isPresent()) {
            return ResponseEntity.ok(advisor.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<AdvisorDTO> getAdvisorByUser(@PathVariable String username) {
        Optional<AdvisorDTO> advisor = advisorService.getAdvisorByUser(username);
        if (advisor.isPresent()) {
            return ResponseEntity.ok(advisor.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/class/{clazzId}")
    public ResponseEntity<AdvisorDTO> getAdvisorByClass(@PathVariable String clazzId) {
        Optional<AdvisorDTO> advisor = advisorService.getAdvisorByClass(clazzId);
        if (advisor.isPresent()) {
            return ResponseEntity.ok(advisor.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<AdvisorDTO> createAdvisor(@RequestBody AdvisorDTO advisorDTO) {
        advisorDTO.setId(CustomPasswordEncoder.getCustomAdvisorId(advisorDTO.getClazz()));
        AdvisorDTO newAdvisor = advisorService.createAdvisor(advisorDTO);
        return new ResponseEntity<>(newAdvisor, HttpStatus.CREATED);
    }

    @PostMapping("/import")
    public ResponseEntity<Void> importStudents(@RequestParam("file") MultipartFile file) {
        try {
            advisorService.importAdvisorsFromExcel(file);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdvisorDTO> updateAdvisor(@PathVariable String id, @RequestBody AdvisorDTO advisorDTO) {
        advisorDTO.setId(CustomPasswordEncoder.getCustomAdvisorId(advisorDTO.getClazz()));
        AdvisorDTO updatedAdvisor = advisorService.updateAdvisor(id, advisorDTO);
        return new ResponseEntity<>(updatedAdvisor, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdvisor(@PathVariable String id) {
        advisorService.deleteAdvisor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
