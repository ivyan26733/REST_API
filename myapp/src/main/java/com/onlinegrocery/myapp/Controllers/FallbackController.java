package com.onlinegrocery.myapp.Controllers;

import com.onlinegrocery.myapp.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {
    @RequestMapping("/**")
    public ResponseEntity<ApiResponse<Object>> handleNotFound(HttpServletRequest request){
        ApiResponse<Object> res = new ApiResponse<>("The requested URL" + request.getRequestURI() + " is not found", null, false);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }
}
