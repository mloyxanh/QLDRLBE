package com.example.qldrl.repositories;

import com.example.qldrl.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, String> {
}

