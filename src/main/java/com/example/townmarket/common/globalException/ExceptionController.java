package com.example.townmarket.common.globalException;

import com.example.townmarket.common.dto.StatusResponse;
import com.example.townmarket.common.util.SetHttpHeaders;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {

  private final SetHttpHeaders setHttpHeaders;

  @ExceptionHandler(IllegalArgumentException.class)
  private ResponseEntity<StatusResponse> illegalArgumentExceptionHandler(
      IllegalArgumentException e) {
    StatusResponse statusResponse = new StatusResponse(HttpStatus.BAD_REQUEST.value(),
        e.getMessage());
    return ResponseEntity.badRequest().headers(setHttpHeaders.setHeaderTypeJson())
        .body(statusResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<StatusResponse> methodValidException(MethodArgumentNotValidException e) {
    StatusResponse statusResponseDto = new StatusResponse(HttpStatus.BAD_REQUEST.value(),
        e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    return ResponseEntity.badRequest().headers(setHttpHeaders.setHeaderTypeJson())
        .body(statusResponseDto);
  }

  @ExceptionHandler(AccessDeniedException.class)
  private ResponseEntity<StatusResponse> AccessDeniedExceptionHandler(AccessDeniedException e) {
    StatusResponse statusResponse = new StatusResponse(HttpStatus.FORBIDDEN.value(),
        e.getMessage());
    return new ResponseEntity<>(statusResponse, setHttpHeaders.setHeaderTypeJson(),
        HttpStatus.FORBIDDEN);
  }


  @ExceptionHandler(JwtException.class)
  private ResponseEntity<StatusResponse> JwtExceptionHandler(JwtException e) {
    StatusResponse statusResponse = new StatusResponse(HttpStatus.UNAUTHORIZED.value(),
        e.getMessage());
    return new ResponseEntity<>(statusResponse, setHttpHeaders.setHeaderTypeJson(),
        HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(SecurityException.class)
  private ResponseEntity<StatusResponse> SecurityExceptionHandler(SecurityException e) {
    StatusResponse statusResponse = new StatusResponse(HttpStatus.UNAUTHORIZED.value(),
        e.getMessage());
    return new ResponseEntity<>(statusResponse, setHttpHeaders.setHeaderTypeJson(),
        HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(MalformedJwtException.class)
  private ResponseEntity<StatusResponse> MalformedJwtExceptionHandler(MalformedJwtException e) {
    StatusResponse statusResponseDto = new StatusResponse(HttpStatus.UNAUTHORIZED.value(),
        e.getMessage());
    return new ResponseEntity<>(statusResponseDto, setHttpHeaders.setHeaderTypeJson(),
        HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(SignatureException.class)
  private ResponseEntity<StatusResponse> SignatureExceptionHandler(SignatureException e) {
    StatusResponse statusResponseDto = new StatusResponse(HttpStatus.UNAUTHORIZED.value(),
        e.getMessage());
    return new ResponseEntity<>(statusResponseDto, setHttpHeaders.setHeaderTypeJson(),
        HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  private ResponseEntity<StatusResponse> ConstraintViolationExceptionHandler(
      ConstraintViolationException e) {
    String message = e.getConstraintViolations()
        .stream()
        .map(ConstraintViolation::getMessage)
        .toList()
        .get(0);
    StatusResponse statusResponseDto = new StatusResponse(HttpStatus.BAD_REQUEST.value(), message);
    return new ResponseEntity<>(statusResponseDto, setHttpHeaders.setHeaderTypeJson(),
        HttpStatus.BAD_REQUEST);
  }

}
