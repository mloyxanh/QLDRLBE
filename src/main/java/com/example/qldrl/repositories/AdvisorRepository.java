package com.example.qldrl.repositories;

import com.example.qldrl.entities.Advisor;
import com.example.qldrl.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdvisorRepository extends JpaRepository<Advisor, String> {
    Optional<Advisor> findByUser_UserName(String user);

    Optional<Advisor> findByClazz_Id(String clazz);

    List<Advisor> findByDepartment_Id(String departmentId);
}
