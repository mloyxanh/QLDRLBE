package com.example.qldrl.mapper;

import com.example.qldrl.dto.CriteriaType1DTO;
import com.example.qldrl.entities.CriteriaType1;

public class CriteriaType1Mapper {
    public static CriteriaType1DTO mapToCriteriaType1DTO(CriteriaType1 criteriaType1) {
        return new CriteriaType1DTO(
                criteriaType1.getId(),
                criteriaType1.getMainContent(),
                criteriaType1.getScore()
        );
    }

    public static CriteriaType1 mapToCriteriaType1(CriteriaType1DTO criteriaType1DTO) {
        return new CriteriaType1(
                criteriaType1DTO.getId(),
                criteriaType1DTO.getMainContent(),
                criteriaType1DTO.getScore()
        );
    }
}
