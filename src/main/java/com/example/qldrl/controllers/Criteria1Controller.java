package com.example.qldrl.controllers;

import com.example.qldrl.dto.Criteria1DTO;
import com.example.qldrl.services.Criteria1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/criteria1")
public class Criteria1Controller {
    private final Criteria1Service criteria1Service;

    @Autowired
    public Criteria1Controller(Criteria1Service criteria1Service) {
        this.criteria1Service = criteria1Service;
    }

    @GetMapping
    public ResponseEntity<List<Criteria1DTO>> getAllCriteria1() {
        List<Criteria1DTO> criterias = criteria1Service.getAllCriteria1();
        return new ResponseEntity<>(criterias, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Criteria1DTO> createCriteria1(@RequestBody Criteria1DTO criteria1DTO) {
        Criteria1DTO newCriteria1 = criteria1Service.createCriteria1(criteria1DTO);
        return new ResponseEntity<>(newCriteria1, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Criteria1DTO> updateCriteria1(@PathVariable String id, @RequestBody Criteria1DTO criteria1DTO) {
        Criteria1DTO updatedCriteria1 = criteria1Service.updateCriteria1(id, criteria1DTO);
        return new ResponseEntity<>(updatedCriteria1, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCriteria1(@PathVariable String id) {
        criteria1Service.deleteCriteria1(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
