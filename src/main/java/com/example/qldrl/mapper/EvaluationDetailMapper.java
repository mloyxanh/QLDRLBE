package com.example.qldrl.mapper;

import com.example.qldrl.dto.EvaluationDTO;
import com.example.qldrl.dto.EvaluationDetailDTO;
import com.example.qldrl.entities.Evaluation;
import com.example.qldrl.entities.EvaluationDetail;
import com.example.qldrl.entities.SubCriteriaType1;

import java.time.LocalDateTime;

public class EvaluationDetailMapper {
    public static EvaluationDetailDTO mapToEvaluationDetailDTO(EvaluationDetail evaluationDetail) {
        return new EvaluationDetailDTO(
                evaluationDetail.getId(),
                evaluationDetail.getEvaluation() != null ? evaluationDetail.getEvaluation().getId() : null,
                evaluationDetail.getSubCriteriaType() != null ? evaluationDetail.getSubCriteriaType().getId() : null,
                evaluationDetail.getScore()
        );
    }

    public static EvaluationDetail mapToEvaluationDetail(EvaluationDetailDTO evaluationDetailDTO,Evaluation evaluation, SubCriteriaType1 subcriteriaType1) {
        return new EvaluationDetail(
                evaluationDetailDTO.getId(),
                evaluation,
                subcriteriaType1,
                evaluationDetailDTO.getScore()
        );
    }
}
