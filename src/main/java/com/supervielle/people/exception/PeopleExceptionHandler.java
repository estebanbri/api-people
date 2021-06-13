package com.supervielle.people.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
@Slf4j
public class PeopleExceptionHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @ExceptionHandler({PeopleException.class})
    public ResponseEntity<?> personException(PeopleException e) throws JsonProcessingException {
        log.warn("personException",e);
        ApiExceptionResponse res = ApiExceptionResponse.createWithDetail(e.getApiExceptionResponse().getDetail())
                .setException(e.getClass().getSimpleName())
                .setMessageToDeveloper(e.getStackTrace()[0].toString());

        return ResponseEntity.badRequest().body(objectMapper.writeValueAsString(res));
    }

    @ExceptionHandler({ConstraintViolationException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<?> validationRequestException(Exception e){
        log.warn("validationRequestException",e);
        return ResponseEntity.badRequest().body(ApiExceptionResponse.createWithDetail(e.getMessage())
                                                    .setException(e.getClass().getSimpleName())
                                                    .setMessageToDeveloper("Bean validation failed"));
    }

    // TODO crear este handler RuntimeException
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<?> internalServerException(RuntimeException e){
        log.warn("internalServerException",e);
        return ResponseEntity.internalServerError().body(ApiExceptionResponse.createWithDetail("Internal Server Error Found")
                .setException(e.getClass().getSimpleName())
                .setDetail(e.getMessage()));
    }
}
