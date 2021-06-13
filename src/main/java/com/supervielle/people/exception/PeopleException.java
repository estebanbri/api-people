package com.supervielle.people.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class PeopleException extends RuntimeException {

    private ApiExceptionResponse apiExceptionResponse;



}
