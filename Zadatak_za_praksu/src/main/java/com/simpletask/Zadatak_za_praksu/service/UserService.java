package com.simpletask.Zadatak_za_praksu.service;

import com.simpletask.Zadatak_za_praksu.domain.User;
import com.simpletask.Zadatak_za_praksu.dto.LoginRequestDTO;
import com.simpletask.Zadatak_za_praksu.exception.CustomException;
import com.simpletask.Zadatak_za_praksu.repository.UserRepository;
import com.simpletask.Zadatak_za_praksu.security.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import static com.simpletask.Zadatak_za_praksu.security.Konstante.TOKEN_BEARER_PREFIX;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTTokenProvider tokenProvider;

    public User find(String uniqueNumber) throws CustomException {
        if(userRepository.findByUsername(uniqueNumber) == null){
            throw new CustomException("No user with that number", HttpStatus.NOT_FOUND);
        }

        return userRepository.findByUsername(uniqueNumber);
    }

    public String login(LoginRequestDTO loginRequestDTO, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(),
                loginRequestDTO.getPassword());
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        Authentication authentication = authenticationManager.authenticate(auth);

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = TOKEN_BEARER_PREFIX + tokenProvider.generate(authentication);

        return jwt;
    }
}
