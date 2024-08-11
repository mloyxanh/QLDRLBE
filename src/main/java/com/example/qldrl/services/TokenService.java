package com.example.qldrl.services;


import com.example.qldrl.entities.Token;
import com.example.qldrl.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public Token createToken(String userName) {
        Token token = new Token();
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(LocalDateTime.now().plusDays(1));
        token.setUserName(userName);
        return tokenRepository.save(token);
    }

    public Optional<Token> getToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public void deleteToken(String userName) {
        tokenRepository.deleteByUserName(userName);
    }
}
