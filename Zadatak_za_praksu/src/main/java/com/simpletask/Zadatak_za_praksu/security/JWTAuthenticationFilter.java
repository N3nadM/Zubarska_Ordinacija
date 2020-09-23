package com.simpletask.Zadatak_za_praksu.security;

import com.simpletask.Zadatak_za_praksu.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.simpletask.Zadatak_za_praksu.security.Konstante.HEADER_BEARER_TOKEN;
import static com.simpletask.Zadatak_za_praksu.security.Konstante.TOKEN_BEARER_PREFIX;

public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authToken = getJWTFromRequest(httpServletRequest);

        if(StringUtils.hasText(authToken) && tokenProvider.validate(authToken)) {
            String username = tokenProvider.getUserUsernameFromJWT(authToken);
            if(username != null) {
                UserDetails korisnikDetails = customUserDetailsService.loadUserByUsername(username);

                TokenBasedAuthentication authentication = new TokenBasedAuthentication(korisnikDetails);
                authentication.setToken(authToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getJWTFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader(HEADER_BEARER_TOKEN);

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_BEARER_PREFIX)){
            return bearerToken.substring(7);
        }

        return null;
    }
}
