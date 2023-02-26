package com.example.ElearningAndExamJNPT.security.userprincal;

import com.example.ElearningAndExamJNPT.entity.User.GenderType;
import com.example.ElearningAndExamJNPT.entity.User.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserPrinciple implements UserDetails {

    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private GenderType gender;
    private String email;
    @JsonIgnore
    private String password;
    private String avatar;
    private Collection<? extends GrantedAuthority> roles;


    public static UserPrinciple build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
        return new UserPrinciple(user.getId(), user.getFirstname(), user.getLastname(), user.getUsername(), user.getGender(), user.getEmail(), user.getPassword(), user.getAvatar(), authorities);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
