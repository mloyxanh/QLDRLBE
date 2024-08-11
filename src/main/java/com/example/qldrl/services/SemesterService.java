package com.example.qldrl.services;

import com.example.qldrl.dto.SemesterDTO;
import com.example.qldrl.entities.Semester;
import com.example.qldrl.mapper.SemesterMapper;
import com.example.qldrl.repositories.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SemesterService {

    private final SemesterRepository semesterRepository;

    @Autowired
    public SemesterService(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    public List<SemesterDTO> getAllSemesters() {
        return semesterRepository.findAll().stream()
                .map(SemesterMapper::mapToSemesterDTO)
                .collect(Collectors.toList());
    }

    public Optional<SemesterDTO> getSemesterById(Integer id) {
        return semesterRepository.findById(id)
                .map(SemesterMapper::mapToSemesterDTO);
    }

    public SemesterDTO createSemester(SemesterDTO semesterDTO) {
        Semester semester = SemesterMapper.mapToSemester(semesterDTO);
        Semester savedSemester = semesterRepository.save(semester);
        return SemesterMapper.mapToSemesterDTO(savedSemester);
    }

    public SemesterDTO updateSemester(Integer id, SemesterDTO semesterDTO) {
        if (semesterRepository.existsById(id)) {
            Semester semester = SemesterMapper.mapToSemester(semesterDTO);
            semester.setName(semesterDTO.getName());
            semester.setBegin(semesterDTO.getBegin());
            semester.setEnd(semesterDTO.getEnd());
            semester.setAcademicYear(semesterDTO.getAcademicYear());
            Semester updatedSemester = semesterRepository.save(semester);
            return SemesterMapper.mapToSemesterDTO(updatedSemester);
        } else {
            throw new RuntimeException("Semester not found");
        }
    }

    public void deleteSemester(Integer id) {
        if (semesterRepository.existsById(id)) {
            semesterRepository.deleteById(id);
        } else {
            throw new RuntimeException("Semester not found");
        }
    }
}
