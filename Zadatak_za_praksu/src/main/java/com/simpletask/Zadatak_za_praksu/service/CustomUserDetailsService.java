package com.simpletask.Zadatak_za_praksu.service;

import com.simpletask.Zadatak_za_praksu.domain.User;
import com.simpletask.Zadatak_za_praksu.exception.UserException;
import com.simpletask.Zadatak_za_praksu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(s);
        if(user==null) throw new UserException("User not found");
        return user;
    }
}
