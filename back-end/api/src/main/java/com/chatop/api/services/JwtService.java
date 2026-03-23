package com.chatop.api.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    @Value("${JWT_SECRET}")
    private String privateKey;

    public String generateToken(String email) {
        return Jwts.builder() //Builder constructor
                .setSubject(email) // Définit un sujet qui indentifie le user
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1h de temps d'expiration
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) //signature
                .compact(); // Transforme le tout en une chaine de caracteres
    }

    //transforme la clé (String) en signature
    private Key getSignInKey() {
        System.out.println("secret : "+privateKey);
        // byte[] keyBytes = Decoders.BASE64.decode(privateKey); // Si la clé privée est en base 64
        //return Keys.hmacShaKeyFor(keyBytes);
        return Keys.hmacShaKeyFor(privateKey.getBytes()); // Sinon on prend chaque octets
    }
    //lis le token JWT et son champ subject pour savoir qui envoie la requète (utile au filtre et pour certaines routes API /auth/me)
    public String extractUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //check l'integrité, l'expiration et la validité du format du token dans les requètes
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
