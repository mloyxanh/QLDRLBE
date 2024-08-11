package com.example.qldrl.mapper;

import com.example.qldrl.dto.Criteria1DTO;
import com.example.qldrl.dto.EvaluationDTO;
import com.example.qldrl.entities.*;
import com.example.qldrl.entities.Class;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class EvaluationMapper {
    public static EvaluationDTO mapToEvaluationDTO(Evaluation evaluation) {
        return new EvaluationDTO(
                evaluation.getId(),
                evaluation.getStudent() != null ? evaluation.getStudent().getId() : null,
                evaluation.getAdvisor() != null ? evaluation.getAdvisor().getId() : null,
                evaluation.getClazz() != null ? evaluation.getClazz().getId() : null,
                evaluation.getCreatedAt().toString(),
                evaluation.getSemester() != null ? evaluation.getSemester().getId() : null,
                evaluation.getReviewedByAdvisor(),
                evaluation.getEvaluationDetails().stream()
                        .map(EvaluationDetailMapper::mapToEvaluationDetailDTO)
                        .collect(Collectors.toList())
        );
    }

    public static Evaluation mapToEvaluation(EvaluationDTO evaluationDTO, Student student, Advisor advisor, Class aClass, Semester semester, List<EvaluationDetail> evaluationDetails, LocalDateTime date) {
        return new Evaluation(
                evaluationDTO.getId(),
                student,
                advisor,
                aClass,
                date,
                semester,
                evaluationDTO.getReviewedByAdvisor(),
                evaluationDetails
        );
    }
}
