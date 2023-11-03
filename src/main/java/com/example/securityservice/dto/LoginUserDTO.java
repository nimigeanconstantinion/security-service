package com.example.securityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LoginUserDTO {
    private String name;
    private String email;
    private String password;

    public LoginUserDTO(String email,String password){
        this.email=email;
        this.password=password;
    }
}
