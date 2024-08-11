package com.example.qldrl.services;

import com.example.qldrl.dto.ClassDTO;
import com.example.qldrl.dto.StudentDTO;
import com.example.qldrl.entities.Department;
import com.example.qldrl.entities.Student;
import com.example.qldrl.entities.Class;
import com.example.qldrl.entities.User;
import com.example.qldrl.mapper.StudentMapper;
import com.example.qldrl.repositories.DepartmentRepository;
import com.example.qldrl.repositories.StudentRepository;
import com.example.qldrl.repositories.ClassRepository;
import com.example.qldrl.repositories.UserRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;
    private final UserRepository userRepository;

    private final DepartmentRepository departmentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, ClassRepository classRepository, DepartmentRepository departmentRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.classRepository = classRepository;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
    }

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(StudentMapper::mapToStudentDTO)
                .collect(Collectors.toList());
    }

    public StudentDTO createStudent(StudentDTO studentDTO) {
        Class clazz = classRepository.findById(studentDTO.getClazz())
                .orElseThrow(() -> new IllegalArgumentException("Invalid class ID"));
        Department department = departmentRepository.findById(studentDTO.getDepartment())
                .orElseThrow(() -> new IllegalArgumentException("Invalid department ID"));
        User user = userRepository.findById(studentDTO.getUser())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        Student std = StudentMapper.mapToStudent(studentDTO, clazz, department, user);
        Student savedStudent = studentRepository.save(std);
        return StudentMapper.mapToStudentDTO(savedStudent);
    }

    public StudentDTO updateStudent(String id, StudentDTO studentDTO) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            Class clazz = classRepository.findById(studentDTO.getClazz())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid class ID"));
            User user = userRepository.findById(studentDTO.getUser())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
            Department department = departmentRepository.findById(studentDTO.getDepartment())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid department ID"));
            student.setFullName(studentDTO.getFullName());
            student.setPhoneNumber(studentDTO.getPhoneNumber());
            student.setAddress(studentDTO.getAddress());
            student.setClazz(clazz);
            student.setDepartment(department);
            student.setUser(user);

            Student updatedStudent = studentRepository.save(student);
            return StudentMapper.mapToStudentDTO(updatedStudent);
        } else {
            throw new RuntimeException("Student not found");
        }
    }

    public void deleteStudent(String id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Student not found");
        }
    }

    public Optional<StudentDTO> getStudentById(String id) {
        return studentRepository.findById(id).map(StudentMapper::mapToStudentDTO);
    }

    public Optional<StudentDTO> getStudentByUser(String user) {
        return studentRepository.findByUser_UserName(user).map(StudentMapper::mapToStudentDTO);
    }

    public List<StudentDTO> getStudentsByClass(String clazz) {
        return studentRepository.findByClazz_Id(clazz).stream()
                .map(StudentMapper::mapToStudentDTO)
                .collect(Collectors.toList());
    }

    public void importStudentsFromExcel(MultipartFile file) throws IOException {
        List<StudentDTO> studentDTOList = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            // Bỏ qua header
            if (rows.hasNext()) {
                rows.next();
            }

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                StudentDTO studentDTO = new StudentDTO();
                studentDTO.setId(getCellValueAsString(currentRow.getCell(0)));
                studentDTO.setSId(getCellValueAsString(currentRow.getCell(1)));
                studentDTO.setFullName(getCellValueAsString(currentRow.getCell(2)));
                studentDTO.setPhoneNumber(getCellValueAsString(currentRow.getCell(3)));
                studentDTO.setAddress(getCellValueAsString(currentRow.getCell(4)));
                studentDTO.setClazz(getCellValueAsString(currentRow.getCell(5)));
                studentDTO.setDepartment(getCellValueAsString(currentRow.getCell(6)));
                studentDTO.setUser(getCellValueAsString(currentRow.getCell(7)));
                studentDTOList.add(studentDTO);
            }
        }

        for (StudentDTO studentDTO : studentDTOList) {
            createStudent(studentDTO);
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                // Sử dụng BigDecimal để giữ chính xác hơn
                return new BigDecimal(cell.getNumericCellValue()).toString();
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }
}
