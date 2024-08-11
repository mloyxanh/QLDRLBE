package com.example.qldrl.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationDTO {
    private String id;
    private String Student;
    private String Advisor;
    private String clazz;
    private String createdAt;
    private Integer semester;
    private Boolean reviewedByAdvisor;
    private List<EvaluationDetailDTO> evaluationDetails;
}
