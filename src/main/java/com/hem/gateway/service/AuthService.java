package com.hem.gateway.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hem.gateway.filter.AuthConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

    @Autowired
    private AuthConfig config;

    public String issueToken(String email, String password) {
        if(!email.equals("test@email.com") || !password.equals("1234")) {
            return "";
        }
        try {
//            Algorithm algorithm = Algorithm.RSA256(config.getRSAPublicKey(), config.getRSAPrivateKey());
            Algorithm algorithm = Algorithm.HMAC256("secret");
            Date now = new Date();
            Date expiresAt = new Date();
            expiresAt.setTime(expiresAt.getTime() + 20000);
            return JWT.create()
                    .withIssuer(config.getIssuer())
                    .withClaim("hem", "woah")
                    .withIssuedAt(now)
                    .withExpiresAt(expiresAt)
                    .sign(algorithm);
        } catch (JWTCreationException exception){
           return null;
        }
    }

    public DecodedJWT verifyHEMJTKN(String HEMJTKN) {
//        System.out.println(config.getIssuer());
        try {
//            Algorithm algorithm = Algorithm.RSA256(config.getRSAPublicKey(), config.getRSAPrivateKey());
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("hem")
                    .build();
            return verifier.verify(HEMJTKN);
        }
        catch (JWTVerificationException e) {
            return null;
        }
    }
}
