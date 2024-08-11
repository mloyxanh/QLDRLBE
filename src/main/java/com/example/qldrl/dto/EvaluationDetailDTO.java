package com.example.qldrl.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationDetailDTO {
    private Integer id;
    private String evaluation;
    private String subCriteriaType;
    private Float score;
}
