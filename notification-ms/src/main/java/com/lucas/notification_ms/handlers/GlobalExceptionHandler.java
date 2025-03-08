package com.lucas.notification_ms.handlers;

import com.lucas.notification_ms.domains.notification.exceptions.ErrorSendingNotification;
import com.lucas.notification_ms.domains.notification.exceptions.InvalidDataCreateNotificationException;
import com.lucas.notification_ms.domains.notification.exceptions.NotificationNotFoundException;
import com.lucas.notification_ms.handlers.dto.CustomErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    private ResponseEntity<CustomErrorResponse> handleGeneralError(Exception e, HttpServletRequest request){
        CustomErrorResponse response = new CustomErrorResponse(
                500,
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.internalServerError().body(response);
    }
//    @ExceptionHandler({ErrorSendingNotification.class})
//    private ResponseEntity<CustomErrorResponse> handleErrorSendingNotification(Exception e, HttpServletRequest request){
//        CustomErrorResponse response = new CustomErrorResponse(
//                500,
//                e.getMessage(),
//                request.getRequestURI()
//        );
//
//        return ResponseEntity.internalServerError().body(response);
//    }

    @ExceptionHandler({InvalidDataCreateNotificationException.class})
    private ResponseEntity<CustomErrorResponse> handleInvalidDataCreateNotification(Exception e, HttpServletRequest request){
        CustomErrorResponse response = new CustomErrorResponse(
                400,
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler({NotificationNotFoundException.class})
    private ResponseEntity<CustomErrorResponse> handleNotificationNotFound(Exception e, HttpServletRequest request){
        CustomErrorResponse response = new CustomErrorResponse(
                404,
                e.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
