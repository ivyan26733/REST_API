package com.onlinegrocery.myapp.exceptions;

import com.onlinegrocery.myapp.model.User;
import com.onlinegrocery.myapp.utils.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(ExpiredJwtException.class)
//    public ResponseEntity<ApiResponse<String>> handleExpiredJwtException(ExpiredJwtException e){
//        ApiResponse<String> res = new ApiResponse<>("Token has expired. Please login again.", null, false);
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
//    }
//
//    @ExceptionHandler(MalformedJwtException.class)
//    public ResponseEntity<ApiResponse<String>> handleMalformedJwtException(MalformedJwtException e){
//        ApiResponse<String> res = new ApiResponse<>("Invalid JWT token format.", null, false);
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
//    }
//
//    @ExceptionHandler(SignatureException.class)
//    public ResponseEntity<ApiResponse<String>> handleSignatureException(SignatureException e){
//        ApiResponse<String> res = new ApiResponse<>("Invalid JWT signature.", null, false);
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
//    }
//
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<ApiResponse<String>> handleAccessDeniedException(AccessDeniedException e){
//        ApiResponse<String> res = new ApiResponse<>("Access denied. You do not have permission to access this resource.", null, false);
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(res);
//    }
//
//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<ApiResponse<String>> handleAuthenticationException(AuthenticationException e){
//        ApiResponse<String> res = new ApiResponse<>("Authentication failed: " + e.getMessage(), null, false);
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
//    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, HttpStatusCode status, WebRequest request){
        ApiResponse<String> res = new ApiResponse<>("Invalid request body format", null, false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException e, HttpHeaders headers, HttpStatusCode status, WebRequest request){
        ApiResponse<String> res = new ApiResponse<>("Missing path variable: "+e.getVariableName(), null, false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @Override
    public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e, HttpHeaders headers, HttpStatusCode status, WebRequest request){
        ApiResponse<String> res = new ApiResponse<>("Missing request parameter: "+e.getParameterName(), null, false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleIllegalArgumentException(IllegalArgumentException e){
        ApiResponse<String> res = new ApiResponse<>(e.getMessage(), null, false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }



//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ApiResponse<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
//        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
//        ApiResponse<String> res = new ApiResponse<>(errorMessage, null, false);
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
//    }
//
//    @ExceptionHandler(MissingServletRequestParameterException.class)
//    public ResponseEntity<ApiResponse<String>> handleMissingServletRequestParameterException(MissingServletRequestParameterException e){
//
//        ApiResponse<String> res = new ApiResponse<>("Missing parameter: "+e.getParameterName(), null, false);
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
//    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiResponse<String>> handleDataAccessException(DataAccessException e){

        ApiResponse<String> res = new ApiResponse<>("Database error: " + e.getMessage(), null, false);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiResponse<String>> handleNullPointerException(NullPointerException e){
        ApiResponse<String> res = new ApiResponse<>("Null pointer exception : " + e.getMessage(), null, false);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
    }

    @ExceptionHandler(TokenGenerationException.class)
    public ResponseEntity<ApiResponse<String>> handleTokenGenerationException(TokenGenerationException e){
        ApiResponse<String> res = new ApiResponse<>("Error generating token!", null, false);
        return ResponseEntity.status(500).body(res);
    }
//
//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<ApiResponse<String>> handleBadCredentialsException(BadCredentialsException e){
//        ApiResponse<String> res = new ApiResponse<>("Invalid username or password!", null, false);
//        return ResponseEntity.status(500).body(res);
//    }



    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<User>> handleEntityNotFoundException(EntityNotFoundException e){
        ApiResponse<User> res = new ApiResponse<>("Entity not found: " + e.getMessage(), null, false);
        return ResponseEntity.status(500).body(res);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<User>> handleDataIntegrityViolationException(DataIntegrityViolationException e){
        String errorMessage = "Database constraint violation occurred!";

        if(e.getCause() != null && e.getCause().getMessage() != null){
            String message = e.getCause().getMessage().toLowerCase();

            if(message.contains("unique constraint")){
                errorMessage = "Email already exist!Please choose another one!";
            }else if( message.contains("foreign key constraint") ){
                errorMessage = "Foreign key constraint violation!";
            }else if(message.contains("cannot be null")){
                errorMessage = "Required field is missing!";
            }
        }
        ApiResponse<User> res = new ApiResponse<>(errorMessage, null, false);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(res);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<User>> handleException(Exception e){
        ApiResponse<User> res = new ApiResponse<>("Something went wrong!", null, false);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
    }

}
