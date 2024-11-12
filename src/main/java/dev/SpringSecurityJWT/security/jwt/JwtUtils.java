package dev.SpringSecurityJWT.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtils {
    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.time.expiration}")
    private String timeExpiration;

    //method for created method
    public String generateAccessToken(String username){

        System.out.println("__________====================== " + secretKey);
        System.out.println("__________====================== " + timeExpiration);


        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(timeExpiration)))
                .signWith(getSignatureKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //validate el token de accesso
    public boolean isTokenValid(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(getSignatureKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;

        }
    }

    //obtener el username del token
    public String getUsernameFromToken(String token){
        return getClaim(token, Claims::getSubject);
    }

    //Obtener un solo claim
    public <T> T getClaim(String token, Function<Claims, T> claimsFunctions){
        Claims claims = extractAllClaims(token);
        return claimsFunctions.apply(claims);
    }

    //obtener los claims del token
    public Claims extractAllClaims(String token){
        return  Jwts.parserBuilder()
                .setSigningKey(getSignatureKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    //obtener firma del token
    public Key getSignatureKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(keyBytes);

    }
}
