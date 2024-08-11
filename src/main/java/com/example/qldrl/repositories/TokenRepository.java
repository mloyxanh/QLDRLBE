package com.example.qldrl.repositories;

import com.example.qldrl.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);

    void deleteByUserName(String userName);
}

