package com.example.qldrl.controllers;

import com.example.qldrl.dto.*;
import com.example.qldrl.entities.Advisor;
import com.example.qldrl.entities.Student;
import com.example.qldrl.entities.Token;
import com.example.qldrl.entities.User;
import com.example.qldrl.functions.CustomPasswordEncoder;
import com.example.qldrl.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private final UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private AdvisorService advisorService;

    @Autowired
    private ClassService classService;

    private CustomPasswordEncoder customPasswordEncoder;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> user = userService.getAllUsers();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<UserDTO> getStudentByUserName(@PathVariable String userName) {
        Optional<UserDTO> user = userService.getUserByUserName(userName);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/import")
    public ResponseEntity<Void> importUsers(@RequestParam("file") MultipartFile file) {
        try {
            userService.importUsersFromExcel(file);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO newUser = userService.createUser(userDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO loginRequest) {
        Optional<User> user = userService.authenticate(loginRequest.getUserName(), loginRequest.getPassWord());
        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        User user1 = user.get();
        if (!user1.getRole().equals(loginRequest.getRole())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        Token token = tokenService.createToken(loginRequest.getUserName());
        String fullName = "";
        String id = "";
        String cls = "";
        switch (loginRequest.getRole()) {
            case "student":
                Optional<StudentDTO> student = studentService.getStudentByUser(user1.getUserName());
                if (student.isPresent()) {
                    fullName = student.get().getFullName();
                    id = student.get().getId();
                    cls = student.get().getClazz();
                } else {
                    System.out.println("No student found for username: " + user1.getUserName());
                }
                break;

            case "advisor":
            case "admin":
                Optional<AdvisorDTO> advisor = advisorService.getAdvisorByUser(user1.getUserName());
                if (advisor.isPresent()) {
                    fullName = advisor.get().getFullName();
                    id = advisor.get().getId();
                    cls = advisor.get().getClazz();
                } else {
                    System.out.println("No advisor found for username: " + user1.getUserName());
                }
                break;
            case "clazz":
                Optional<ClassDTO> clazz = classService.getClassByUser(user1.getUserName());
                if (clazz.isPresent()) {
                    fullName = clazz.get().getName();
                    id = clazz.get().getId();
                    cls = clazz.get().getId();
                } else {
                    System.out.println("No class found for username: " + user1.getUserName());
                }
                break;
            default:
                break;
        }

        // Trả về response bao gồm token và fullName
        Map<String, Object> response = new HashMap<>();
        response.put("cls", cls);
        response.put("token", token);
        response.put("fullName", fullName);
        response.put("role", user1.getRole());
        response.put("id", id);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/change/{username}")
    public ResponseEntity<?> changePassword(@PathVariable String username, @RequestBody PasswordChangingDTO passRequest) {
        String pwd = userService.getUsersByUserName(username).get().getPassWord();
        String pwd2 = CustomPasswordEncoder.encode(passRequest.getOldPassword());
        String pwd3 = CustomPasswordEncoder.encode(passRequest.getNewPassword());
        if (pwd2.equals(pwd)) {
            userService.updatePass(username, pwd3);
            return ResponseEntity.ok("Password updated successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password is incorrect");
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}