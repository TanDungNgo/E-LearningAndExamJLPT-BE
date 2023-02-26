package com.example.ElearningAndExamJNPT.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private Long id;
    private String token;
    private String type = "Bearer";
    private String firstname;
    private String lastname;
    private String avatar;
    private Collection<? extends GrantedAuthority> roles;


    public JwtResponse(Long id, String token, String firstname,String lastname, String avatar, Collection<? extends GrantedAuthority> roles) {
        this.id = id;
        this.token = token;
        this.firstname = firstname;
        this.lastname = lastname;
        this.avatar = avatar;
        this.roles = roles;
    }
}
