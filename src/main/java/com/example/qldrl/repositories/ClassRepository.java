package com.example.qldrl.repositories;

import aj.org.objectweb.asm.commons.Remapper;
import com.example.qldrl.entities.Advisor;
import com.example.qldrl.entities.Class;
import com.example.qldrl.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClassRepository extends JpaRepository<Class, String> {
    Optional<Class> findByUser_UserName(String user);
    List<Class> findByDepartment_Id(String departmentId);
}