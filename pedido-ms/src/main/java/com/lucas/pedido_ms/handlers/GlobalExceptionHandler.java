package com.lucas.pedido_ms.handlers;

import com.lucas.pedido_ms.domains.order.exception.InvalidOrderCreationException;
import com.lucas.pedido_ms.domains.order.exception.InvalidOrderUpdateException;
import com.lucas.pedido_ms.domains.order.exception.OrderNotFoundException;
import com.lucas.pedido_ms.domains.order.exception.SendingOrderMailException;
import com.lucas.pedido_ms.domains.orderitem.exception.InvalidOrderItemDataException;
import com.lucas.pedido_ms.domains.orderitem.exception.OrderItemNotFoundException;
import com.lucas.pedido_ms.domains.orderitem.exception.OrderItemRestaurantNotFound;
import com.lucas.pedido_ms.handlers.dto.CustomErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler({Exception.class})
    private ResponseEntity<Object> handleGeneralError(Exception e, WebRequest request) {
        CustomErrorResponse error = new CustomErrorResponse();
        error.setError(e.getMessage());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setUrl(request.getContextPath());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(e, error, headers, HttpStatus.BAD_REQUEST, request);
    }


    @ExceptionHandler({InvalidOrderItemDataException.class })
    private ResponseEntity<Object> handleInvalidOrderCreation(Exception e, HttpServletRequest request){
        CustomErrorResponse errorResponse = new CustomErrorResponse();

        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setError(e.getMessage());
        errorResponse.setUrl(request.getRequestURI());

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler({OrderItemNotFoundException.class })
    private ResponseEntity<Object> handleOrderItemNotFound(Exception e, HttpServletRequest request){
        CustomErrorResponse errorResponse = new CustomErrorResponse();

        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setError(e.getMessage());
        errorResponse.setUrl(request.getRequestURI());

        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler({InvalidOrderCreationException.class })
    private ResponseEntity<Object> invalidOrderCreation(Exception e, HttpServletRequest request){
        CustomErrorResponse errorResponse = new CustomErrorResponse();

        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setError(e.getMessage());
        errorResponse.setUrl(request.getRequestURI());

        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler({InvalidOrderUpdateException.class })
    private ResponseEntity<Object> invalidOrderUpdate(Exception e, HttpServletRequest request){
        CustomErrorResponse errorResponse = new CustomErrorResponse();

        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setError(e.getMessage());
        errorResponse.setUrl(request.getRequestURI());

        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }
    @ExceptionHandler({OrderNotFoundException.class })
    private ResponseEntity<Object> orderNotFound(Exception e, HttpServletRequest request){
        CustomErrorResponse errorResponse = new CustomErrorResponse();

        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setError(e.getMessage());
        errorResponse.setUrl(request.getRequestURI());

        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler({SendingOrderMailException.class })
    private ResponseEntity<Object> sendingOrderMail(Exception e, HttpServletRequest request){
        CustomErrorResponse errorResponse = new CustomErrorResponse();

        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setError(e.getMessage());
        errorResponse.setUrl(request.getRequestURI());

        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler({OrderItemRestaurantNotFound.class })
    private ResponseEntity<Object> orderItemRestaurantNotFound(Exception e, HttpServletRequest request){
        CustomErrorResponse errorResponse = new CustomErrorResponse();

        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setError(e.getMessage());
        errorResponse.setUrl(request.getRequestURI());

        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }
}
