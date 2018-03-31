package com.cc.workflow.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cc.workflow.exceptions.ApplicationNotFound;
import com.cc.workflow.exceptions.AuthError;
import com.cc.workflow.exceptions.InvalidMortgageApplication;
import com.cc.workflow.exceptions.MortgageApplicationAlreadyExists;
import com.cc.workflow.exceptions.UserNotFound;

@ControllerAdvice
public class ErrorControllerAdvice {
    @ExceptionHandler(value = AuthError.class)
    public ResponseEntity<APIErrorResponse> authenticationException(AuthError e) {
        APIErrorResponse err = new APIErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                e.getErrorMessage()
        );

        return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = UserNotFound.class)
    public ResponseEntity<APIErrorResponse> userNotFoundError(UserNotFound e) {
        APIErrorResponse err = new APIErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                e.getErrorMessage()

        );

        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InvalidMortgageApplication.class)
    public ResponseEntity<APIErrorResponse> invalidApplicationError(InvalidMortgageApplication e) {
        APIErrorResponse err = new APIErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                e.getErrorMessage()
        );

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ApplicationNotFound.class)
    public ResponseEntity<APIErrorResponse> applicationNotFoundError(ApplicationNotFound e) {
        APIErrorResponse err = new APIErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                e.getErrorMessage()
        );

        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value =  MortgageApplicationAlreadyExists.class)
    public ResponseEntity<APIErrorResponse> applicationAlreadyExists(MortgageApplicationAlreadyExists e) {
        APIErrorResponse err = new APIErrorResponse(
                HttpStatus.CONFLICT.value(),
                e.getErrorMessage()
        );

        return new ResponseEntity<>(err, HttpStatus.CONFLICT);
    }

//    @ExceptionHandler(value = RuntimeException.class)
//    public ResponseEntity<ApolloErrorMessage> generalServerError() {
//        ApolloErrorMessage err = new ApolloErrorMessage(
//                HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
//        );
//
//        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}