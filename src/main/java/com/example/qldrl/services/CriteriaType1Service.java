package com.example.qldrl.services;

import com.example.qldrl.dto.ClassDTO;
import com.example.qldrl.dto.CriteriaType1DTO;
import com.example.qldrl.entities.Class;
import com.example.qldrl.entities.CriteriaType1;
import com.example.qldrl.entities.Department;
import com.example.qldrl.mapper.ClassMapper;
import com.example.qldrl.mapper.CriteriaType1Mapper;
import com.example.qldrl.repositories.CriteriaType1Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CriteriaType1Service {
    private final CriteriaType1Repository criteriaType1Repository;

    @Autowired
    public CriteriaType1Service(CriteriaType1Repository criteriaType1Repository) {
        this.criteriaType1Repository = criteriaType1Repository;
    }

    public List<CriteriaType1DTO> getAllCT1() {
        return criteriaType1Repository.findAll().stream()
                .map(CriteriaType1Mapper::mapToCriteriaType1DTO)
                .collect(Collectors.toList());
    }

    public CriteriaType1DTO creatCT1(CriteriaType1DTO criteriaType1DTO) {
        CriteriaType1 criteriaType1 = CriteriaType1Mapper.mapToCriteriaType1(criteriaType1DTO);
        CriteriaType1 savedCriteriaType1 = criteriaType1Repository.save(criteriaType1);
        return CriteriaType1Mapper.mapToCriteriaType1DTO(savedCriteriaType1);
    }

    public CriteriaType1DTO updateCT1(String id, CriteriaType1DTO criteriaType1DTO) {
        Optional<CriteriaType1> optionalCriteriaType1 = criteriaType1Repository.findById(id);
        if (optionalCriteriaType1.isPresent()) {
            CriteriaType1 criteriaType1 = optionalCriteriaType1.get();
            criteriaType1.setMainContent(criteriaType1DTO.getMainContent());
            criteriaType1.setScore(criteriaType1DTO.getScore());
            CriteriaType1 updatedCriteriaType11 = criteriaType1Repository.save(criteriaType1);
            return CriteriaType1Mapper.mapToCriteriaType1DTO(updatedCriteriaType11);
        } else {
            throw new RuntimeException("CriteriaType1 Not Found");
        }
    }

    public void deleteCT1(String id) {
        if (criteriaType1Repository.existsById(id)) {
            criteriaType1Repository.deleteById(id);
        } else {
            throw new RuntimeException("CriteriaType1 Not Found");
        }
    }
}
