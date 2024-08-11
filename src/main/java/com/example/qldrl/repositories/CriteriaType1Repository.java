package com.example.qldrl.repositories;

import com.example.qldrl.entities.CriteriaType1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CriteriaType1Repository extends JpaRepository<CriteriaType1, String> {
}
