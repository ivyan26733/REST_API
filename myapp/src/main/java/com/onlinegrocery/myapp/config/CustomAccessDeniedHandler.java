package com.onlinegrocery.myapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinegrocery.myapp.utils.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ApiResponse<String> res = new ApiResponse<>("Access denied. You do not have permission to access this resource.", null, false);
        String jsonRes = new ObjectMapper().writeValueAsString(res);

        response.getWriter().write(jsonRes);
    }
}
