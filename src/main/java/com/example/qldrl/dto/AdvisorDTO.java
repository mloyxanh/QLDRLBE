package com.example.qldrl.dto;

import com.example.qldrl.entities.Department;
import com.example.qldrl.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdvisorDTO {
    private String id;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String clazz;
    private String department;
    private String user;
}
