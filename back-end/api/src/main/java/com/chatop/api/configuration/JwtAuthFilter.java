package com.chatop.api.configuration;

import com.chatop.api.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request, //requete du front
            @NonNull HttpServletResponse response, //reponse au front
            @NonNull FilterChain filterChain //suite des autres filtres à appliquer
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization"); //récupération du header de la requete
        final String jwt;
        final String userEmail;

        // 1. Si pas de header ou pas de header avec Bearer (convention jwt), on passe au filtre suivant
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); //on passe la main aux filtres suivant sans rien faire
            return;
        }

        // 2. Extraire le token (substring 7 pour retirer "Bearer ")
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt); // On lit l'email dans le token

        // 3. Si on a un email et que l'utilisateur n'est pas déjà authentifié dans le contexte
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtService.isTokenValid(jwt)) { //SI token non expiré et signature correcte
                // Créer l'objet d'authentification pour Spring Security
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userEmail,
                        null,
                        Collections.emptyList() // Liste des rôles (vide pour l'instant)
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // On dit à Spring : "C'est bon, cet utilisateur est validé !" et on enregistre le token dans le contexte
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response); //On continue d'appliquer les filtres suivants
    }

}
