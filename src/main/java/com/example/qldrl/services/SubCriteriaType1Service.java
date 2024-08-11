package com.example.qldrl.services;

import com.example.qldrl.dto.SubCriteriaType1DTO;
import com.example.qldrl.entities.CriteriaType1;
import com.example.qldrl.entities.SubCriteriaType1;
import com.example.qldrl.mapper.SubCriteriaType1Mapper;
import com.example.qldrl.repositories.CriteriaType1Repository;
import com.example.qldrl.repositories.SubCriteriaType1Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class SubCriteriaType1Service {
    private final SubCriteriaType1Repository subCriteriaType1Repository;
    private final CriteriaType1Repository criteriaType1Repository;
    @Autowired
    public SubCriteriaType1Service(SubCriteriaType1Repository subCriteriaType1Repository, CriteriaType1Repository criteriaType1Repository) {
        this.subCriteriaType1Repository = subCriteriaType1Repository;
        this.criteriaType1Repository = criteriaType1Repository;
    }

    public List<SubCriteriaType1DTO> getAllSCT1() {
        return subCriteriaType1Repository.findAll().stream()
                .map(SubCriteriaType1Mapper::mapToSubCriteriaType1DTO)
                .collect(Collectors.toList());
    }

    public SubCriteriaType1DTO createSCT1(SubCriteriaType1DTO subcriteriaType1DTO) {
        CriteriaType1 criteriaType1 = criteriaType1Repository.findById(subcriteriaType1DTO.getCriteriaType1())
                .orElseThrow(() -> new IllegalArgumentException("Invalid subCriteriaType1 ID"));
        SubCriteriaType1 subCriteriaType1 = SubCriteriaType1Mapper.mapToSubCriteriaType1(subcriteriaType1DTO, criteriaType1);
        SubCriteriaType1 savedsubCriteriaType1 = subCriteriaType1Repository.save(subCriteriaType1);
        return SubCriteriaType1Mapper.mapToSubCriteriaType1DTO(savedsubCriteriaType1);
    }

    public SubCriteriaType1DTO updateSCT1(String id, SubCriteriaType1DTO subCriteriaType1DTO) {
        Optional<SubCriteriaType1> optionalCriteriaType1 = subCriteriaType1Repository.findById(id);
        if (optionalCriteriaType1.isPresent()) {
            SubCriteriaType1 subcriteriaType1 = optionalCriteriaType1.get();
            subcriteriaType1.setContent(subCriteriaType1DTO.getContent());
            subcriteriaType1.setScore(subCriteriaType1DTO.getScore());
            SubCriteriaType1 updatedsubCriteriaType11 = subCriteriaType1Repository.save(subcriteriaType1);
            return SubCriteriaType1Mapper.mapToSubCriteriaType1DTO(updatedsubCriteriaType11);
        } else {
            throw new RuntimeException("SubCriteriaType1 Not Found");
        }
    }

    public void deleteSCT1(String id) {
        if (subCriteriaType1Repository.existsById(id)) {
            subCriteriaType1Repository.deleteById(id);
        } else {
            throw new RuntimeException("SubCriteriaType1 Not Found");
        }
    }
}
