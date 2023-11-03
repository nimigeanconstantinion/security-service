package com.example.securityservice.web;

import com.example.securityservice.dto.LoginUserDTO;
import com.example.securityservice.jwt.JWTTokenProvider;
import com.example.securityservice.model.User;
import com.example.securityservice.repository.UserRepo;
import com.example.securityservice.security.UserRole;
import com.example.securityservice.service.UserService;
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

    private UserService securityService;
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JWTTokenProvider jwtTokenProvider;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityController(UserService userService, UserRepo userRepo, AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.jwtTokenProvider=jwtTokenProvider;
        this.securityService= userService;
        this.userService=userService;
        this.authenticationManager=authenticationManager;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public String login(@RequestBody LoginUserDTO user){
        User usr=userService.getUserFromEmail(user.getEmail());

        if(usr!=null){


            if(bCryptPasswordEncoder.matches(user.getPassword(),usr.getPassword())){

                    return jwtTokenProvider.generateJWTToken(usr);
            }else{
                throw new RuntimeException("Password did not match!!");
            }

        }else{
            throw new RuntimeException("User din not exists !! Please Sign-up!!");


        }

    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/register")
    public String signup(@RequestBody LoginUserDTO user){

        User usr=userService.getUserFromEmail(user.getEmail());

        if(usr==null){
            User newUser=new User();
            newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            newUser.setEmail(user.getEmail());
            newUser.setRole(UserRole.USER);
            newUser.setName(user.getName());
            userService.addUser(newUser);
            return jwtTokenProvider.generateJWTToken(newUser);


        }else{
            throw new RuntimeException("You can not sign-up !! User is already signed!!");


        }

    }


}
