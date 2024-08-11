package com.example.qldrl.controllers;

import com.example.qldrl.dto.EvaluationDTO;
import com.example.qldrl.dto.StudentDTO;
import com.example.qldrl.functions.CustomPasswordEncoder;
import com.example.qldrl.services.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/evaluate")
public class EvaluationController {
    private final EvaluationService evaluationService;

    @Autowired
    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @GetMapping
    public ResponseEntity<List<EvaluationDTO>> getAllEvaluations() {
        List<EvaluationDTO> eva = evaluationService.getAllEvaluations();
        return new ResponseEntity<>(eva, HttpStatus.OK);
    }

    @GetMapping("/semester/{id}")
    public ResponseEntity<List<EvaluationDTO>> getEvaBySemester(@PathVariable Integer id) {
        List<EvaluationDTO> evas = evaluationService.getEvaBySemester(id);
        if (evas.stream().count() > 0) {
            return new ResponseEntity<>(evas, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/stueva/{student}/{semester}")
    public ResponseEntity<EvaluationDTO> getEvaluationByStudentIdAndSemester(
            @PathVariable String student,
            @PathVariable Integer semester) {
        Optional<EvaluationDTO> evaluation = evaluationService.getEvaluationsByStudentAndSemester(student, semester);
        if (evaluation.isPresent()) {
            return ResponseEntity.ok(evaluation.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/claeva/{clazz}/{semester}")
    public ResponseEntity<List<EvaluationDTO>> getEvaluationByClassIdAndSemester(
            @PathVariable String clazz,
            @PathVariable Integer semester) {
        System.out.println(clazz + semester);
        List<EvaluationDTO> evaluation = evaluationService.getEvaluationsByClassAndSemester(clazz, semester);
        if (evaluation.stream().count() > 0) {
            return new ResponseEntity<>(evaluation, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<EvaluationDTO> createEvaluation(@RequestBody EvaluationDTO evaluationDTO) {
        evaluationDTO.setId(CustomPasswordEncoder.getCustomEvaluationId(evaluationDTO.getStudent(),evaluationDTO.getSemester()));
        evaluationDTO.setReviewedByAdvisor(false);
        EvaluationDTO newEvaluation = evaluationService.createEvaluation(evaluationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEvaluation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EvaluationDTO> updateEvaluation(@PathVariable String id, @RequestBody EvaluationDTO evaluationDTO) {
        System.out.println("Received id: " + id);
        EvaluationDTO updatedEvaluation = evaluationService.updateEvaluation(id, evaluationDTO);
        return new ResponseEntity<>(updatedEvaluation, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable String id) {
        evaluationService.deleteEvaluation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
