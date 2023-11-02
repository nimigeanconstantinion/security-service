package com.example.securityservice.model;

import com.example.securityservice.security.UserPermisson;
import com.example.securityservice.security.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import static com.example.securityservice.security.UserPermisson.*;
import static com.example.securityservice.security.UserRole.*;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "User")
@Table(name = "_user")
public class User implements UserDetails {
    @Id
    @SequenceGenerator(
            name="users_sequence",
            sequenceName = "users_sequence",
            allocationSize = 1

    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "users_sequence"
    )
    private Long id;

    private String name;

    private String email;


    private String password;

    @Enumerated
    private UserRole role;


    public User(String email,String password){

        this.email=email;
        this.password=password;
    }

    @Override
    public boolean equals(Object u){
        User usr=(User) u;
        return usr.email.equals(this.email);
    }

    @Override
    public String toString(){
        return "Name:"+this.name+", Email:"+this.email;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return USER.getGrantedAuthorities();
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
