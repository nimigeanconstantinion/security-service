package com.example.securityservice.web;

import com.example.securityservice.jwt.JWTTokenProvider;
import com.example.securityservice.model.User;
import com.example.securityservice.repository.UserRepo;
import com.example.securityservice.service.SecurityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/security")
@CrossOrigin
@Slf4j
public class SecurityController {

    private SecurityService securityService;
    private UserRepo userRepo;
    private AuthenticationManager authenticationManager;
    private JWTTokenProvider jwtTokenProvider;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityController(SecurityService securityService,UserRepo userRepo,AuthenticationManager authenticationManager,JWTTokenProvider jwtTokenProvider,BCryptPasswordEncoder bCryptPasswordEncoder){
        this.jwtTokenProvider=jwtTokenProvider;
        this.securityService=securityService;
        this.userRepo=userRepo;
        this.authenticationManager=authenticationManager;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public String login(@RequestBody User user){
        Optional<User> usr=userRepo.findUserByEmail(user.getEmail());

        if(!usr.isEmpty()){


            if(bCryptPasswordEncoder.matches(user.getPassword(),usr.get().getPassword())){

                    return jwtTokenProvider.generateJWTToken(user);
            }else{
                throw new RuntimeException("Password did not match!!");
            }

        }else{
            throw new RuntimeException("User din not exists !! Please Sign-up!!");


        }

    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/register")
    public String signup(@RequestBody User user){
        Optional<User> usr=userRepo.findUserByEmail(user.getEmail());

        if(usr.isEmpty()){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepo.save(user);
            return jwtTokenProvider.generateJWTToken(user);


        }else{
            throw new RuntimeException("You can not sign-up !! User is already signed!!");


        }

    }


}
