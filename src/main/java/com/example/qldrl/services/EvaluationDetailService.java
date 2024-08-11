package com.example.qldrl.services;

import com.example.qldrl.dto.EvaluationDetailDTO;
import com.example.qldrl.entities.Evaluation;
import com.example.qldrl.entities.EvaluationDetail;
import com.example.qldrl.entities.SubCriteriaType1;
import com.example.qldrl.mapper.EvaluationDetailMapper;
import com.example.qldrl.mapper.StudentMapper;
import com.example.qldrl.repositories.EvaluationDetailRepository;
import com.example.qldrl.repositories.EvaluationRepository;
import com.example.qldrl.repositories.SubCriteriaType1Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluationDetailService {
    private final EvaluationDetailRepository evaluationDetailRepository;
    private final EvaluationRepository evaluationRepository;
    private final SubCriteriaType1Repository subCriteriaType1Repository;

    @Autowired
    public EvaluationDetailService(EvaluationDetailRepository evaluationDetailRepository, EvaluationRepository evaluationRepository, SubCriteriaType1Repository subCriteriaType1Repository) {
        this.evaluationDetailRepository = evaluationDetailRepository;
        this.evaluationRepository = evaluationRepository;
        this.subCriteriaType1Repository = subCriteriaType1Repository;
    }

    public List<EvaluationDetailDTO> getAllEvaluationDetails() {
        return evaluationDetailRepository.findAll().stream()
                .map(EvaluationDetailMapper::mapToEvaluationDetailDTO)
                .collect(Collectors.toList());
    }

    public EvaluationDetailDTO createEvaluationDetail(EvaluationDetailDTO evaluationDetailDTO) {
        Evaluation evaluation = evaluationRepository.findById(evaluationDetailDTO.getEvaluation())
                .orElseThrow(() -> new IllegalArgumentException("Invalid evaluation ID"));
        SubCriteriaType1 subCriteriaType1 = subCriteriaType1Repository.findById(evaluationDetailDTO.getEvaluation())
                .orElseThrow(() -> new IllegalArgumentException("Invalid evaluation ID"));
        EvaluationDetail evaluationDetail = EvaluationDetailMapper.mapToEvaluationDetail(evaluationDetailDTO, evaluation, subCriteriaType1);
        EvaluationDetail savedEvaluationDetail = evaluationDetailRepository.save(evaluationDetail);
        return EvaluationDetailMapper.mapToEvaluationDetailDTO(savedEvaluationDetail);
    }

    public EvaluationDetailDTO updateEvaluationDetail(String id, EvaluationDetailDTO evaluationDetailDTO) {
        Evaluation evaluation = evaluationRepository.findById(evaluationDetailDTO.getEvaluation())
                .orElseThrow(() -> new IllegalArgumentException("Invalid evaluation ID"));
        SubCriteriaType1 subCriteriaType1 = subCriteriaType1Repository.findById(evaluationDetailDTO.getSubCriteriaType())
                .orElseThrow(() -> new IllegalArgumentException("Invalid sub criteria type ID"));
        Optional<EvaluationDetail> optionalEvaluationDetail = evaluationDetailRepository.findById(id);
        if (optionalEvaluationDetail.isPresent()) {
            EvaluationDetail evaluationDetail = optionalEvaluationDetail.get();
            evaluationDetail.setScore(evaluationDetailDTO.getScore());
            evaluationDetail.setEvaluation(evaluation);
            evaluationDetail.setSubCriteriaType(subCriteriaType1);
            EvaluationDetail updatedEvaluationDetail = evaluationDetailRepository.save(evaluationDetail);
            return EvaluationDetailMapper.mapToEvaluationDetailDTO(updatedEvaluationDetail);
        } else {
            throw new RuntimeException("Evaluation Detail not found");
        }
    }

    public void deleteEvaluationDetail(String id) {
        if (evaluationDetailRepository.existsById(id)) {
            evaluationDetailRepository.deleteById(id);
        } else {
            throw new RuntimeException("Evaluation Detail not found");
        }
    }

    public List<EvaluationDetailDTO> getEvaluationDetailsByEvaluation(String evaluation) {
        return evaluationDetailRepository.findByEvaluation_Id(evaluation).stream()
                .map(EvaluationDetailMapper::mapToEvaluationDetailDTO)
                .collect(Collectors.toList());
    }
}
