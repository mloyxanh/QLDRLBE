package com.example.qldrl.controllers;

import com.example.qldrl.dto.EvaluationDetailDTO;
import com.example.qldrl.dto.StudentDTO;
import com.example.qldrl.dto.SubCriteriaType1DTO;
import com.example.qldrl.services.EvaluationDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/eva-details")
public class EvaluationDetailController {
    private final EvaluationDetailService evaluationDetailService;

    @Autowired
    public EvaluationDetailController(EvaluationDetailService evaluationDetailService) {
        this.evaluationDetailService = evaluationDetailService;
    }

    @GetMapping
    public ResponseEntity<List<EvaluationDetailDTO>> getAllEvaluationDetail() {
        List<EvaluationDetailDTO> evade = evaluationDetailService.getAllEvaluationDetails();
        return new ResponseEntity<>(evade, HttpStatus.OK);
    }

    @GetMapping("/eva/{evaluationId}")
    public ResponseEntity<List<EvaluationDetailDTO>> getEvaluationDetailsByEvaId(@PathVariable String evaluationId) {
        List<EvaluationDetailDTO> evaluationDetail = evaluationDetailService.getEvaluationDetailsByEvaluation(evaluationId);
        return new ResponseEntity<>(evaluationDetail, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EvaluationDetailDTO> createEvaluationDetail(@RequestBody EvaluationDetailDTO evaluationDetailDTO) {
        EvaluationDetailDTO newevade = evaluationDetailService.createEvaluationDetail(evaluationDetailDTO);
        return new ResponseEntity<>(newevade, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EvaluationDetailDTO> updateEvaluationDetail(@PathVariable String id, @RequestBody EvaluationDetailDTO evaluationDetailDTO) {
        EvaluationDetailDTO updatedEvade = evaluationDetailService.updateEvaluationDetail(id, evaluationDetailDTO);
        return new ResponseEntity<>(updatedEvade, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvaluationDetail(@PathVariable String id) {
        evaluationDetailService.deleteEvaluationDetail(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
