package com.lucas.profile_ms.handlers;

import com.lucas.profile_ms.domains.profile.exceptions.*;
import com.lucas.profile_ms.handlers.dto.CustomErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler {

@ExceptionHandler({Exception.class})
    private ResponseEntity<CustomErrorResponse> handleGeneralError(Exception e, HttpServletRequest request){
        CustomErrorResponse response = new CustomErrorResponse(
                500,
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.internalServerError().body(response);
    }


    @ExceptionHandler({ConfirmationCodeDoesntExists.class})
    private ResponseEntity<CustomErrorResponse> handleConfirmCodeDoesntExists(Exception e, HttpServletRequest request){
        CustomErrorResponse response = new CustomErrorResponse(
                404,
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler({InvalidDataCreateProfileException.class})
    private ResponseEntity<CustomErrorResponse> handleInvalidDataCreate(Exception e, HttpServletRequest request){
        CustomErrorResponse response = new CustomErrorResponse(
                400,
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler({InvalidJwtTokenException.class})
    private ResponseEntity<CustomErrorResponse> handleInvalidJwtToken(Exception e, HttpServletRequest request){
        CustomErrorResponse response = new CustomErrorResponse(
                401,
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(401).body(response);
    }

    @ExceptionHandler({MismatchAuthDataException.class})
    private ResponseEntity<CustomErrorResponse> handleMismatchAuthData(Exception e, HttpServletRequest request){
        CustomErrorResponse response = new CustomErrorResponse(
                401,
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(401).body(response);
    }

    @ExceptionHandler({ProfileAlredyExistsException.class})
    private ResponseEntity<CustomErrorResponse> handleProfileAlreadyExists(Exception e, HttpServletRequest request){
        CustomErrorResponse response = new CustomErrorResponse(
                401,
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(401).body(response);
    }

    @ExceptionHandler({ProfileNotFoundException.class})
    private ResponseEntity<CustomErrorResponse> handleProfileNotFound(Exception e, HttpServletRequest request){
        CustomErrorResponse response = new CustomErrorResponse(
                404,
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(404).body(response);
    }
    @ExceptionHandler({TokenGenerationException.class})
    private ResponseEntity<CustomErrorResponse> handleTokenGeneration(Exception e, HttpServletRequest request){
        CustomErrorResponse response = new CustomErrorResponse(
                500,
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.internalServerError().body(response);
    }

}
