package com.example.ElearningAndExamJLPT.security.userprincal;

import com.example.ElearningAndExamJLPT.entity.User.User;
import com.example.ElearningAndExamJLPT.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class userDetailService implements UserDetailsService {
    @Autowired
    IUserRepository userResponsitory;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userResponsitory.findUserByDeletedFalseAndUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found -> username or password"+username));
        return UserPrinciple.build(user);
    }
}
