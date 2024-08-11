package com.example.qldrl.repositories;

import com.example.qldrl.entities.Evaluation;
import com.example.qldrl.entities.EvaluationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluationDetailRepository  extends JpaRepository<EvaluationDetail, String> {
    List<EvaluationDetail> findByEvaluation_Id(String evaluation);
}
