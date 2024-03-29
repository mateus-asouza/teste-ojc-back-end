package com.ms.personApi.service;


import com.ms.personApi.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${apiTeste.jwt.expiration}")
    private String expiration;
    @Value("${apiTeste.jwt.secret}")
    private String secret;


    public String generateToken(Authentication authentication) {
       User logado = (User) authentication.getPrincipal();
       Date today = new Date();
       Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));
        return Jwts.builder()
                .setIssuer("API projeto teste")
                .setSubject(logado.getId().toString())
                .setIssuedAt(today)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {

        Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
        return true;
        }catch (Exception e){
            return false;
        }
    }

    public Long getIdUser(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }
}
