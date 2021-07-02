package com.travel.authservice.service.Util;

import com.travel.authservice.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expirationAccessTokenMs}")
    private long accessTokenSessionTime;

    private Key key;

    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    private Map<String, Object> createClaims(User user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", user.getUsername());
        claims.put("email", user.getEmail());
        claims.put("role", user.getRoleList());
        claims.put("country", user.getCountry());

        return claims;
    }

    public Claims getAllClaimsFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String generateToken(User user){
        Date currentTime = new Date(System.currentTimeMillis());
        Date expirationTime = new Date(System.currentTimeMillis() + accessTokenSessionTime);

        Map<String, Object> claims = createClaims(user);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(currentTime)
                .setExpiration(expirationTime)
                .signWith(key)
                .compact();
    }


    public Boolean validateToken(String token){
        final Date expiration = getAllClaimsFromToken(token).getExpiration();
        boolean result = expiration.before(new Date());
        return result;
    }
}
