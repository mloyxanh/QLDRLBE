package com.example.qldrl.mapper;

import com.example.qldrl.dto.CriteriaType1DTO;
import com.example.qldrl.dto.SubCriteriaType1DTO;
import com.example.qldrl.entities.CriteriaType1;
import com.example.qldrl.entities.SubCriteriaType1;

public class SubCriteriaType1Mapper {
    public static SubCriteriaType1DTO mapToSubCriteriaType1DTO(SubCriteriaType1 subCriteriaType1) {
        return new SubCriteriaType1DTO(
                subCriteriaType1.getId(),
                subCriteriaType1.getContent(),
                subCriteriaType1.getScore(),
                subCriteriaType1.getCriteriaType1() != null ? subCriteriaType1.getCriteriaType1().getId() : null
        );
    }

    public static SubCriteriaType1 mapToSubCriteriaType1(SubCriteriaType1DTO subCriteriaType1DTO, CriteriaType1 criteriaType1) {
        return new SubCriteriaType1(
                subCriteriaType1DTO.getId(),
                subCriteriaType1DTO.getContent(),
                subCriteriaType1DTO.getScore(),
                criteriaType1
        );
    }
}
