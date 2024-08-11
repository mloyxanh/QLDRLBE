package com.example.qldrl.mapper;

import com.example.qldrl.dto.AdvisorDTO;
import com.example.qldrl.dto.ClassDTO;
import com.example.qldrl.entities.Advisor;
import com.example.qldrl.entities.Class;
import com.example.qldrl.entities.Department;
import com.example.qldrl.entities.User;

public class AdvisorMapper {
    public static AdvisorDTO mapToAdvisorDTO(Advisor advisor) {
        return new AdvisorDTO(
                advisor.getId(),
                advisor.getFullName(),
                advisor.getPhoneNumber(),
                advisor.getAddress(),
                advisor.getClazz() != null ? advisor.getClazz().getId() : null,
                advisor.getDepartment() != null ? advisor.getDepartment().getId() : null,
                advisor.getUser() != null ? advisor.getUser().getUserName() : null
        );
    }

    public static Advisor mapToAdvisor(AdvisorDTO advisorDTO, Class clazz, Department department, User user) {
        return new Advisor(
                advisorDTO.getId(),
                advisorDTO.getFullName(),
                advisorDTO.getPhoneNumber(),
                advisorDTO.getAddress(),
                clazz,
                department,
                user
        );
    }
}
