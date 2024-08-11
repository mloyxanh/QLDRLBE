package com.example.qldrl.mapper;

import com.example.qldrl.dto.DepartmentDTO;
import com.example.qldrl.entities.Department;

public class DepartmentMapper {
    public static DepartmentDTO mapToDepartmentDTO(Department department) {
        return new DepartmentDTO(
                department.getId(),
                department.getName()
        );
    }

    public static Department mapToDepartment(DepartmentDTO departmentDTO) {
        return new Department(
                departmentDTO.getId(),
                departmentDTO.getName()
        );
    }
}
