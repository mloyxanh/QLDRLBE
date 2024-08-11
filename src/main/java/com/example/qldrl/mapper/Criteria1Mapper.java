package com.example.qldrl.mapper;

import com.example.qldrl.dto.Criteria1DTO;
import com.example.qldrl.entities.Criteria1;
import com.example.qldrl.entities.SubCriteriaType1;

public class Criteria1Mapper {
    public static Criteria1DTO mapToCriteria1DTO(Criteria1 criteria1) {
        return new Criteria1DTO(
                criteria1.getId(),
                criteria1.getContent(),
                criteria1.getScore(),
                criteria1.getSubCriteriaType1() != null ? criteria1.getSubCriteriaType1().getId() : null
        );
    }

    public static Criteria1 mapToCriteria1(Criteria1DTO criteria1DTO, SubCriteriaType1 subcriteriaType1) {
        return new Criteria1(
                criteria1DTO.getId(),
                criteria1DTO.getContent(),
                criteria1DTO.getScore(),
                subcriteriaType1
        );
    }
}
