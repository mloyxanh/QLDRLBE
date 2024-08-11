package com.example.qldrl.repositories;

import com.example.qldrl.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUserName(String userName);
    Optional<User> findByUserNameAndPassWord(String userName, String passWord);
//
}
