package com.example.qldrl.repositories;

import com.example.qldrl.entities.Student;
import com.example.qldrl.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    Optional<Student> findById(String id);
    Optional<Student> findByUser_UserName(String user);
    List<Student> findByClazz_Id(String clazzId);
}

