package com.authservice.AuthService.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.authservice.AuthService.service.JWTService;
import com.authservice.AuthService.service.UserDetailSer;
import com.authservice.AuthService.utils.ApiResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    ApplicationContext context;  //to avoid cyclization error



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("JWT FILTER triggered for request: " + request.getRequestURI());

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        System.out.println("Incoming Request: " + request.getRequestURI());

        System.out.println("Authorization Header: " + authHeader);

        try{
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                username = jwtService.extractUserName(token);
                System.out.println("Extracted userName from token: " + username);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = context.getBean(UserDetailSer.class).loadUserByUsername(username);
                if (jwtService.validateToken(token, userDetails)) {
                    Claims claims = jwtService.extractAllClaims(token);
                    List<GrantedAuthority> authorities = ((List<String>) claims.get("roles")).stream()
                            .map(role -> (GrantedAuthority) () -> role)
                            .collect(Collectors.toList());


                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource()
                            .buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("User authenticated: " + username);
                } else {
                    System.out.println("Invalid JWT Token");
                }
            }else{
                System.out.println("USer is null or already authenticated");
            }
        }catch(ExpiredJwtException e){
            createErrorResponse(response, "Token has expired. Please login again.", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }catch(MalformedJwtException e){
            createErrorResponse(response, "Invalid JWT token format.", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }catch(SignatureException e){
            createErrorResponse(response, "Invalid JWT signature.", HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }catch(AuthenticationException e){
            createErrorResponse(response, "Authentication failed: " + e.getMessage(), HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void createErrorResponse(HttpServletResponse response, String s, int statusCode) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ApiResponse<String> res = new ApiResponse<>(s, null, false);
        String jsonRes = new ObjectMapper().writeValueAsString(res);

        response.getWriter().write(jsonRes);
    }
}