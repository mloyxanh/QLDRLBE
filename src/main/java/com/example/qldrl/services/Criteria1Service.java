package com.example.qldrl.services;

import com.example.qldrl.dto.Criteria1DTO;
import com.example.qldrl.entities.Criteria1;
import com.example.qldrl.entities.SubCriteriaType1;
import com.example.qldrl.mapper.Criteria1Mapper;
import com.example.qldrl.repositories.Criteria1Repository;
import com.example.qldrl.repositories.SubCriteriaType1Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Criteria1Service {
    private final Criteria1Repository criteria1Repository;
    private final SubCriteriaType1Repository subCriteriaType1Repository;

    @Autowired
    public Criteria1Service(Criteria1Repository criteria1Repository, SubCriteriaType1Repository subCriteriaType1Repository) {
        this.criteria1Repository = criteria1Repository;
        this.subCriteriaType1Repository = subCriteriaType1Repository;
    }

    public List<Criteria1DTO> getAllCriteria1() {
        return criteria1Repository.findAll().stream()
                .map(Criteria1Mapper::mapToCriteria1DTO)
                .collect(Collectors.toList());
    }

    public Criteria1DTO createCriteria1(Criteria1DTO criteria1DTO) {
        SubCriteriaType1 subCriteriaType1 = subCriteriaType1Repository.findById(criteria1DTO.getSubCriteriaType1())
                .orElseThrow(() -> new IllegalArgumentException("Invalid subCriteriaType1 ID"));
        Criteria1 criteria1 = Criteria1Mapper.mapToCriteria1(criteria1DTO, subCriteriaType1);
        Criteria1 savedCriteria1 = criteria1Repository.save(criteria1);
        return Criteria1Mapper.mapToCriteria1DTO(savedCriteria1);
    }

    public Criteria1DTO updateCriteria1(String id, Criteria1DTO criteria1DTO) {
        Optional<Criteria1> optionalCriteria1 = criteria1Repository.findById(id);
        if (optionalCriteria1.isPresent()) {
            Criteria1 criteria1 = optionalCriteria1.get();
            criteria1.setContent(criteria1DTO.getContent());
            criteria1.setScore(criteria1DTO.getScore());
            Criteria1 updatedCriteria1 = criteria1Repository.save(criteria1);
            return Criteria1Mapper.mapToCriteria1DTO(updatedCriteria1);
        } else {
            throw new RuntimeException("Criteria1 Not Found");
        }
    }

    public void deleteCriteria1(String id) {
        if (criteria1Repository.existsById(id)) {
            criteria1Repository.deleteById(id);
        } else {
            throw new RuntimeException("Criteria1 Not Found");
        }
    }
}
