package com.example.qldrl.controllers;

import com.example.qldrl.dto.CriteriaType1DTO;
import com.example.qldrl.services.CriteriaType1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ct1s")
public class CriteriaType1Controller {
    private final CriteriaType1Service criteriaType1Service;
    @Autowired
    public CriteriaType1Controller(CriteriaType1Service criteriaType1Service) {
        this.criteriaType1Service = criteriaType1Service;
    }
    @GetMapping
    public ResponseEntity<List<CriteriaType1DTO>> getAllCT1() {
        List<CriteriaType1DTO> criteriaType1DTOS = criteriaType1Service.getAllCT1();
        return new ResponseEntity<>(criteriaType1DTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CriteriaType1DTO> createCT1(@RequestBody CriteriaType1DTO criteriaType1DTO) {
        CriteriaType1DTO newCriteriaType1 = criteriaType1Service.creatCT1(criteriaType1DTO);
        return new ResponseEntity<>(newCriteriaType1, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CriteriaType1DTO> updateCT1(@PathVariable String id, @RequestBody CriteriaType1DTO criteriaType1DTO) {
        CriteriaType1DTO updatedCriteriaType1 = criteriaType1Service.updateCT1(id, criteriaType1DTO);
        return new ResponseEntity<>(updatedCriteriaType1, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCT1(@PathVariable String id) {
        criteriaType1Service.deleteCT1(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
