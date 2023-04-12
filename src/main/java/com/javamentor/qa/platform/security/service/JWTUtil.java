package com.javamentor.qa.platform.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {


    @Value("${jwt.secret}")
    private String secret;

    // Method to sign and create a JWT using the injected secret
    public String generateToken(String email) throws IllegalArgumentException, JWTCreationException {
        return JWT.create()
                .withSubject("User Details")
                .withClaim("email", email)
                .withIssuedAt(new Date())
                .withIssuer("YOUR APPLICATION/PROJECT/COMPANY NAME")
                .sign(Algorithm.HMAC256(secret));
    }

    // Method to verify the JWT and then decode and extract the user email stored in the payload of the token
    public String validateTokenAndRetrieveSubject(String token)throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User Details")
                .withIssuer("YOUR APPLICATION/PROJECT/COMPANY NAME")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("email").asString();
    }

}





//package com.javamentor.qa.platform.security.service;
//
//import java.security.NoSuchAlgorithmException;
//import java.security.spec.InvalidKeySpecException;
//import java.util.Date;
//import java.util.GregorianCalendar;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//@Component
//public class JwtProvider {
//
//    @Value("${jwt.secret}")
//    private String secretKey;
//
//
//    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
//
//    public JwtProvider(String secretKey, SignatureAlgorithm SIGNATURE_ALGORITHM) {
//        this.secretKey = secretKey;
//        this.SIGNATURE_ALGORITHM = SIGNATURE_ALGORITHM;
//    }
//
//    public JwtProvider() {
//    }
//
//    private Claims getAllClaimsFromToken(String token) {
//        Claims claims;
//        try {
//            claims = Jwts.parser()
//                    .setSigningKey(secretKey)
//                    .parseClaimsJws(token)
//                    .getBody();
//        } catch (Exception e) {
//            claims = null;
//        }
//        return claims;
//    }
//
//    public String getUsernameFromToken(String token) {
//        String username;
//        try {
//            final Claims claims = this.getAllClaimsFromToken(token);
//            username = claims.getSubject();
//        } catch (Exception e) {
//            username = null;
//        }
//        return username;
//    }
//
//    public String generateToken(String username) throws InvalidKeySpecException, NoSuchAlgorithmException {
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(generateExpirationDate())
//                .signWith(SIGNATURE_ALGORITHM, secretKey)
//                .compact();
//    }
//
//    private Date generateExpirationDate() {
//        return new Date(3000, 1, 1);
//    }
//
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = getUsernameFromToken(token);
//        return (
//                username != null &&
//                        username.equals(userDetails.getUsername()) &&
//                        !isTokenExpired(token)
//        );
//    }
//
//    public boolean isTokenExpired(String token) {
//        Date expireDate = getExpirationDate(token);
//        return expireDate.before(new Date());
//    }
//
//
//    private Date getExpirationDate(String token) {
//        Date expireDate;
//        try {
//            final Claims claims = this.getAllClaimsFromToken(token);
//            expireDate = claims.getExpiration();
//        } catch (Exception e) {
//            expireDate = null;
//        }
//        return expireDate;
//    }
//
//
//    public Date getIssuedAtDateFromToken(String token) {
//        Date issueAt;
//        try {
//            final Claims claims = this.getAllClaimsFromToken(token);
//            issueAt = claims.getIssuedAt();
//        } catch (Exception e) {
//            issueAt = null;
//        }
//        return issueAt;
//    }
//
//    public String getToken(HttpServletRequest request) {
//        String authHeader = getAuthHeaderFromHeader(request);
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            return authHeader.substring(7);
//        }
//
//        return null;
//    }
//
//    public String getAuthHeaderFromHeader(HttpServletRequest request) {
//        return request.getHeader("Authorization");
//    }
//}