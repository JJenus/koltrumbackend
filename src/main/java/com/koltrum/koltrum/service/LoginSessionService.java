package com.koltrum.koltrum.service;

import com.koltrum.koltrum.model.LoginSession;
import com.koltrum.koltrum.repository.LoginSessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginSessionService {
    @Autowired
    LoginSessionRepo sessionRepo;

    public LoginSession save(LoginSession session){
        return sessionRepo.save(session);
    }

    List<LoginSession> loginSessionsByUser(Long userId){
        return sessionRepo.findByUserId(userId);
    }
}
