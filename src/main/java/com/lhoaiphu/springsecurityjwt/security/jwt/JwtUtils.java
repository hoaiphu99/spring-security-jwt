package com.lhoaiphu.springsecurityjwt.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lhoaiphu.springsecurityjwt.service.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${lhoaiphu.app.jwtSecret}")
    private String jwtSecret;

    @Value("${lhoaiphu.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        logger.info(userPrincipal.getUsername());
        return JWT.create()
                .withSubject(userPrincipal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .sign(HMAC512(jwtSecret.getBytes()));
    }

    public String getUsernameFromJwtToken(String token) {
        return JWT.require(HMAC512(jwtSecret.getBytes()))
                .build()
                .verify(token)
                .getSubject();
    }

//    public boolean validateJwtToken(String token) {
//        try {
//            DecodedJWT jwt = JWT.decode(token);
//            logger.info(jwt.toString());
//            return true;
//        } catch (JWTVerificationException exception){
//            //Invalid signature/claims
//            logger.error("Invalid token {}", exception.getMessage());
//            return false;
//        }
//    }
}
