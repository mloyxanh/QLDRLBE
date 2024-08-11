package com.example.qldrl.controllers;

import com.example.qldrl.dto.SubCriteriaType1DTO;
import com.example.qldrl.services.SubCriteriaType1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scts1")
public class SubCriteriaType1Controller {
    private final SubCriteriaType1Service subCriteriaType1Service;

    @Autowired
    public SubCriteriaType1Controller(SubCriteriaType1Service subCriteriaType1Service) {
        this.subCriteriaType1Service = subCriteriaType1Service;
    }

    @GetMapping
    public ResponseEntity<List<SubCriteriaType1DTO>> getAllSCT1() {
        List<SubCriteriaType1DTO> subCT1 = subCriteriaType1Service.getAllSCT1();
        return new ResponseEntity<>(subCT1, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SubCriteriaType1DTO> createSubCriteriaType1(@RequestBody SubCriteriaType1DTO subCriteriaType1DTO) {
        SubCriteriaType1DTO newSCT1 = subCriteriaType1Service.createSCT1(subCriteriaType1DTO);
        return new ResponseEntity<>(newSCT1, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubCriteriaType1DTO> updateSubCriteriaType1(@PathVariable String id, @RequestBody SubCriteriaType1DTO subCriteriaType1DTO) {
        SubCriteriaType1DTO updatedSCT1 = subCriteriaType1Service.updateSCT1(id, subCriteriaType1DTO);
        return new ResponseEntity<>(updatedSCT1, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubCriteriaType1(@PathVariable String id) {
        subCriteriaType1Service.deleteSCT1(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
