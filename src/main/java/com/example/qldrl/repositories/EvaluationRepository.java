package com.example.qldrl.repositories;

import aj.org.objectweb.asm.commons.Remapper;
import com.example.qldrl.dto.EvaluationDTO;
import com.example.qldrl.entities.Evaluation;
import com.example.qldrl.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, String> {
    List<Evaluation> findBySemester_Id(Integer semester);

    Optional<Evaluation> findByStudentIdAndSemesterId(String student, Integer semester);

    List<Evaluation> findByClazzIdAndSemesterId(String clazz, Integer semester);
}
