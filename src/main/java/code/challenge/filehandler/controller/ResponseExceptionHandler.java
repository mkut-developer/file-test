package code.challenge.filehandler.controller;

import code.challenge.filehandler.constant.ErrorMessage;
import code.challenge.filehandler.excetption.BadArgumentException;
import code.challenge.filehandler.excetption.BadRequestException;
import code.challenge.filehandler.excetption.DbException;
import code.challenge.filehandler.excetption.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

import static code.challenge.filehandler.constant.ErrorMessage.ERROR;

@Slf4j
@ControllerAdvice
public class ResponseExceptionHandler {

    @ExceptionHandler(DbException.class)
    public ResponseEntity<?> handleDbException(DbException e) {
        log.info("[DbException] response is {}", HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap(ERROR, e.getMessage()));
    }

    @ExceptionHandler(BadArgumentException.class)
    public ResponseEntity<?> handleBadArgumentException(BadArgumentException e) {
        log.info("[BadArgumentException] response is {}", HttpStatus.BAD_REQUEST);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(ERROR, e.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException e) {
        log.info("[BadRequestException] response is {}", HttpStatus.BAD_REQUEST);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(ERROR, e.getMessage()));
    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<?> handleServerException(ServerException e) {
        log.info("[ServerException] response is {}", HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap(ERROR, e.getMessage()));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<?> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.info("[HttpMediaTypeNotSupportedException] response is {}", HttpStatus.BAD_REQUEST);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(ERROR, e.getMessage()));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnexpectedException(Exception e) {
        log.info("[Exception] unexpected exception occurred: {}", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap(ERROR, ErrorMessage.INTERNAL_ERROR));
    }
}
