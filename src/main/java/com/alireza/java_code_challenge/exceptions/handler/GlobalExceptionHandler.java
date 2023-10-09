package com.alireza.java_code_challenge.exceptions.handler;


import com.alireza.java_code_challenge.dto.exception.ResponseException;
import com.alireza.java_code_challenge.exceptions.confirmationcode.ConfirmationCodeExpiredException;
import com.alireza.java_code_challenge.exceptions.confirmationcode.ConfirmationCodeInvalidException;
import com.alireza.java_code_challenge.exceptions.user.UserAcceptedException;
import com.alireza.java_code_challenge.exceptions.user.UserExistException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final CallbackFactory callbackFactory;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {

        callbackFactory.handleException(ex);

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ResponseException> illegalArgumentException(IllegalArgumentException exception, HttpServletRequest request) {

        callbackFactory.handleException(exception);

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseException responseException = new ResponseException(
                new Timestamp(System.currentTimeMillis()),
                httpStatus.value(),
                httpStatus.name(),
                exception.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(responseException, httpStatus);
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<ResponseException> nullPointerException(NullPointerException exception, HttpServletRequest request) {

        callbackFactory.handleException(exception);

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseException responseException = new ResponseException(
                new Timestamp(System.currentTimeMillis()),
                httpStatus.value(),
                httpStatus.name(),
                exception.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(responseException, httpStatus);
    }

    @ExceptionHandler(value = IOException.class)
    public ResponseEntity<ResponseException> iOException(IOException exception, HttpServletRequest request) {

        callbackFactory.handleException(exception);

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseException responseException = new ResponseException(
                new Timestamp(System.currentTimeMillis()),
                httpStatus.value(),
                httpStatus.name(),
                exception.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(responseException, httpStatus);
    }

    @ExceptionHandler(value = DateTimeParseException.class)
    public ResponseEntity<ResponseException> dateTimeParseException(DateTimeParseException exception, HttpServletRequest request) {

        callbackFactory.handleException(exception);

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseException responseException = new ResponseException(
                new Timestamp(System.currentTimeMillis()),
                httpStatus.value(),
                httpStatus.name(),
                exception.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(responseException, httpStatus);
    }

    @ExceptionHandler(value = UserAcceptedException.class)
    public ResponseEntity<ResponseException> userAcceptedException(UserAcceptedException exception, HttpServletRequest request) {

        callbackFactory.handleException(exception);

        HttpStatus httpStatus = HttpStatus.CONFLICT;
        ResponseException responseException = new ResponseException(
                new Timestamp(System.currentTimeMillis()),
                httpStatus.value(),
                httpStatus.name(),
                exception.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(responseException, httpStatus);
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<ResponseException> usernameNotFoundException(UsernameNotFoundException exception, HttpServletRequest request) {

        callbackFactory.handleException(exception);

        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ResponseException responseException = new ResponseException(
                new Timestamp(System.currentTimeMillis()),
                httpStatus.value(),
                httpStatus.name(),
                exception.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(responseException, httpStatus);
    }

    @ExceptionHandler(value = UserExistException.class)
    public ResponseEntity<ResponseException> userExistException(UserExistException exception, HttpServletRequest request) {

        callbackFactory.handleException(exception);

        HttpStatus httpStatus = HttpStatus.CONFLICT;
        ResponseException responseException = new ResponseException(
                new Timestamp(System.currentTimeMillis()),
                httpStatus.value(),
                httpStatus.name(),
                exception.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(responseException, httpStatus);
    }

    @ExceptionHandler(value = ConfirmationCodeInvalidException.class)
    public ResponseEntity<ResponseException> confirmationCodeInvalidException(ConfirmationCodeInvalidException exception, HttpServletRequest request) {

        callbackFactory.handleException(exception);

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseException responseException = new ResponseException(
                new Timestamp(System.currentTimeMillis()),
                httpStatus.value(),
                httpStatus.name(),
                exception.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(responseException, httpStatus);
    }

    @ExceptionHandler(value = ConfirmationCodeExpiredException.class)
    public ResponseEntity<ResponseException> confirmationCodeExpiredException(ConfirmationCodeExpiredException exception, HttpServletRequest request) {

        callbackFactory.handleException(exception);

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResponseException responseException = new ResponseException(
                new Timestamp(System.currentTimeMillis()),
                httpStatus.value(),
                httpStatus.name(),
                exception.getMessage(),
                request.getRequestURI());
        return new ResponseEntity<>(responseException, httpStatus);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<ResponseException> badCredentialsException(BadCredentialsException exception, HttpServletRequest request) {

        callbackFactory.handleException(exception);

        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        ResponseException responseException = new ResponseException(
                new Timestamp(System.currentTimeMillis()),
                httpStatus.value(),
                httpStatus.name(),
                "Email or password does not match your information.",
                request.getRequestURI());
        return new ResponseEntity<>(responseException, httpStatus);
    }
}

