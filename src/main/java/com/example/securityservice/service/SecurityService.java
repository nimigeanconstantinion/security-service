package com.example.securityservice.service;

import com.example.securityservice.model.User;
import com.example.securityservice.repository.UserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    UserRepo userRepo;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityService(UserRepo userRepo){
        this.userRepo=userRepo;
    }

    public User getUserFromEmail(String email){
//        String u= bCryptPasswordEncoder.encode();
        return userRepo.findUserByEmail(email).get();
    }





    public String signup(User user) {

//     return bCryptPasswordEncoder.encode(user);

        return null;
    }


}
