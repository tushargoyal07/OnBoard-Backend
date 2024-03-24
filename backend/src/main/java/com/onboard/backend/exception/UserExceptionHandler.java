package com.onboard.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(value = { UserNotFoundException.class })
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        UserException userException = new UserException(
                userNotFoundException.getMessage(),
                userNotFoundException.getCause(),
                HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(userException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { UserAlreadyExistsException.class })
    public ResponseEntity<Object> handleUserAlreadyExistsException(
            UserAlreadyExistsException userAlreadyExistsException) {
        UserException userException = new UserException(
                userAlreadyExistsException.getMessage(),
                userAlreadyExistsException.getCause(),
                HttpStatus.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(userException, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = { InvalidEmailException.class })
    public ResponseEntity<Object> handleInvalidEmailException(
            InvalidEmailException invalidEmailException) {
        UserException userException = new UserException(
                invalidEmailException.getMessage(),
                invalidEmailException.getCause(),
                HttpStatus.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(userException, HttpStatus.METHOD_NOT_ALLOWED);
    }

}
