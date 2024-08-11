package com.example.qldrl.services;

import com.example.qldrl.dto.EvaluationDTO;
import com.example.qldrl.entities.*;
import com.example.qldrl.entities.Class;
import com.example.qldrl.mapper.EvaluationMapper;
import com.example.qldrl.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluationService {
    private final EvaluationRepository evaluationRepository;
    private final StudentRepository studentRepository;
    private final AdvisorRepository advisorRepository;
    private final ClassRepository classRepository;
    private final SemesterRepository semesterRepository;
    private final EvaluationDetailRepository evaluationDetailRepository;
    private final SubCriteriaType1Repository subCriteriaType1Repository;

    @Autowired
    public EvaluationService(EvaluationRepository evaluationRepository, StudentRepository studentRepository,
                             AdvisorRepository advisorRepository, ClassRepository classRepository,
                             SemesterRepository semesterRepository, EvaluationDetailRepository evaluationDetailRepository,
                             SubCriteriaType1Repository subCriteriaType1Repository) {
        this.evaluationRepository = evaluationRepository;
        this.studentRepository = studentRepository;
        this.advisorRepository = advisorRepository;
        this.classRepository = classRepository;
        this.semesterRepository = semesterRepository;
        this.evaluationDetailRepository = evaluationDetailRepository;
        this.subCriteriaType1Repository = subCriteriaType1Repository;
    }

    public List<EvaluationDTO> getAllEvaluations() {
        return evaluationRepository.findAll().stream()
                .map(EvaluationMapper::mapToEvaluationDTO)
                .collect(Collectors.toList());
    }

    public EvaluationDTO createEvaluation(EvaluationDTO evaluationDTO) {
        Student student = studentRepository.findById(evaluationDTO.getStudent())
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID"));
        Advisor advisor = advisorRepository.findById(evaluationDTO.getAdvisor())
                .orElse(null);
        Class clazz = classRepository.findById(evaluationDTO.getClazz())
                .orElseThrow(() -> new IllegalArgumentException("Invalid class ID"));
        Semester semester = semesterRepository.findById(evaluationDTO.getSemester())
                .orElseThrow(() -> new IllegalArgumentException("Invalid semester ID"));

        Evaluation evaluation = new Evaluation();
        evaluation.setId(evaluationDTO.getId());
        evaluation.setStudent(student);
        evaluation.setAdvisor(advisor);
        evaluation.setClazz(clazz);
        evaluation.setCreatedAt(LocalDateTime.now());
        evaluation.setSemester(semester);

        List<EvaluationDetail> evaluationDetails = evaluationDTO.getEvaluationDetails().stream()
                .map(detailDTO -> {
                    if (detailDTO.getSubCriteriaType() == null) {
                        throw new IllegalArgumentException("Sub criteria type ID must not be null");
                    }
                    EvaluationDetail evaluationDetail = new EvaluationDetail();
                    evaluationDetail.setId(detailDTO.getId());
                    evaluationDetail.setEvaluation(evaluation);
                    SubCriteriaType1 subCriteriaType = subCriteriaType1Repository.findById(detailDTO.getSubCriteriaType())
                            .orElseThrow(() -> new IllegalArgumentException("Invalid sub criteria type ID"));
                    evaluationDetail.setSubCriteriaType(subCriteriaType);
                    evaluationDetail.setScore(detailDTO.getScore());
                    return evaluationDetail;
                })
                .collect(Collectors.toList());

        evaluation.setEvaluationDetails(evaluationDetails);

        Evaluation savedEvaluation = evaluationRepository.save(evaluation);
        return EvaluationMapper.mapToEvaluationDTO(savedEvaluation);
    }

    public EvaluationDTO updateEvaluation(String id, EvaluationDTO evaluationDTO) {
        if (id == null) {
            System.out.println(id+"3 \n");
        }
        System.out.println(evaluationDTO.getId() + " " + evaluationDTO.getStudent() + " " + evaluationDTO.getAdvisor() + " " +evaluationDTO.getClazz() + " " +evaluationDTO.getSemester());
        Optional<Evaluation> optionalEvaluation = evaluationRepository.findById(id);
        Advisor advisor = new Advisor();
        if (evaluationDTO.getAdvisor()!= null) {
            advisor = advisorRepository.findById(evaluationDTO.getAdvisor())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid advisor ID"));
        }
        else{
            advisor = null;
        }
        Student student = studentRepository.findById(evaluationDTO.getStudent())
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID"));

        Class clazz = classRepository.findById(evaluationDTO.getClazz())
                .orElseThrow(() -> new IllegalArgumentException("Invalid class ID"));
        Semester semester = semesterRepository.findById(evaluationDTO.getSemester())
                .orElseThrow(() -> new IllegalArgumentException("Invalid semester ID"));

        if (optionalEvaluation.isPresent()) {
            System.out.println("Found evaluation with id: " + id);
            Evaluation evaluation = optionalEvaluation.get();
            evaluation.setStudent(student);
            evaluation.setAdvisor(advisor);
            evaluation.setClazz(clazz);
            evaluation.setCreatedAt(LocalDateTime.parse(evaluationDTO.getCreatedAt()));
            evaluation.setReviewedByAdvisor(evaluationDTO.getReviewedByAdvisor());
            evaluation.setSemester(semester);

            List<EvaluationDetail> currentEvaluationDetails = new ArrayList<>(evaluation.getEvaluationDetails());
            List<EvaluationDetail> newEvaluationDetails = evaluationDTO.getEvaluationDetails().stream()
                    .map(evaluationDetailDTO -> {
                        SubCriteriaType1 subCriteriaType = subCriteriaType1Repository.findById(evaluationDetailDTO.getSubCriteriaType())
                                .orElseThrow(() -> new IllegalArgumentException("Invalid sub criteria type ID"));
                        EvaluationDetail evaluationDetail = new EvaluationDetail();
                        evaluationDetail.setId(evaluationDetailDTO.getId());
                        evaluationDetail.setEvaluation(evaluation);
                        evaluationDetail.setSubCriteriaType(subCriteriaType);
                        evaluationDetail.setScore(evaluationDetailDTO.getScore());
                        return evaluationDetail;
                    })
                    .collect(Collectors.toList());

            currentEvaluationDetails.forEach(existingDetail -> {
                if (!newEvaluationDetails.contains(existingDetail)) {
                    evaluation.removeEvaluationDetail(existingDetail);
                }
            });

            newEvaluationDetails.forEach(newDetail -> {
                if (!currentEvaluationDetails.contains(newDetail)) {
                    evaluation.addEvaluationDetail(newDetail);
                }
            });

            Evaluation updatedEvaluation = evaluationRepository.save(evaluation);
            return EvaluationMapper.mapToEvaluationDTO(updatedEvaluation);
        } else {
            throw new RuntimeException("Evaluation not found");
        }
    }

    public void deleteEvaluation(String id) {
        if (evaluationRepository.existsById(id)) {
            evaluationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Evaluation not found");
        }
    }

    public List<EvaluationDTO> getEvaBySemester(Integer semester) {
        return evaluationRepository.findBySemester_Id(semester).stream()
                .map(EvaluationMapper::mapToEvaluationDTO)
                .collect(Collectors.toList());
    }

    public Optional<EvaluationDTO> getEvaluationsByStudentAndSemester(String student, Integer semester) {
        return evaluationRepository.findByStudentIdAndSemesterId(student, semester).map(EvaluationMapper::mapToEvaluationDTO);
    }

    public List<EvaluationDTO> getEvaluationsByClassAndSemester(String clazz, Integer semester) {
        return evaluationRepository.findByClazzIdAndSemesterId(clazz, semester).stream()
                .map(EvaluationMapper::mapToEvaluationDTO)
                .collect(Collectors.toList());
    }

    public void saveDefaultEvaluations() {
        List<Student> students = studentRepository.findAll();
        List<Semester> semesters = semesterRepository.findAll();

        for (Semester semester : semesters) {
            LocalDate endDate = semester.getEnd().plusDays(7);
            LocalDate currentDate = LocalDate.now(ZoneId.systemDefault());

            if (currentDate.isAfter(endDate)) {
                for (Student student : students) {
                    List<Evaluation> existingEvaluations = evaluationRepository.findByClazzIdAndSemesterId(student.getClazz().getId(), semester.getId());
                    boolean studentHasEvaluation = existingEvaluations.stream()
                            .anyMatch(evaluation -> evaluation.getStudent().getId().equals(student.getId()));

                    if (!studentHasEvaluation) {
                        Evaluation evaluation = new Evaluation();
                        evaluation.setStudent(student);
                        evaluation.setAdvisor(null);
                        evaluation.setClazz(student.getClazz());
                        evaluation.setCreatedAt(LocalDateTime.now());
                        evaluation.setSemester(semester);

                        List<EvaluationDetail> evaluationDetails = new ArrayList<>();
                        List<SubCriteriaType1> subCriteriaTypes = subCriteriaType1Repository.findAll();

                        for (SubCriteriaType1 subCriteriaType : subCriteriaTypes) {
                            EvaluationDetail evaluationDetail = new EvaluationDetail();
                            evaluationDetail.setEvaluation(evaluation);
                            evaluationDetail.setSubCriteriaType(subCriteriaType);
                            evaluationDetail.setScore((float) 0);
                            evaluationDetails.add(evaluationDetail);
                        }

                        evaluation.setEvaluationDetails(evaluationDetails);
                        evaluationRepository.save(evaluation);
                    }
                }
            }
        }
    }
}