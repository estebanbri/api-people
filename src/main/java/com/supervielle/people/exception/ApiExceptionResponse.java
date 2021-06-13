package com.supervielle.people.exception;

import lombok.*;

@Data
@RequiredArgsConstructor
public class ApiExceptionResponse {

    private String exception;

    @NonNull
    private String messageToDeveloper;

    private String detail;

    private ApiExceptionResponse() {}

    public ApiExceptionResponse setException(String exception) {
        this.exception = exception;
        return this;
    }

    public ApiExceptionResponse setMessageToDeveloper(String messageToDeveloper) {
        this.messageToDeveloper = messageToDeveloper;
        return this;
    }

    public ApiExceptionResponse setDetail(String detail) {
        this.detail = detail;
        return this;
    }

    public static ApiExceptionResponse createWithDetail(String detail) {
        ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse();
        apiExceptionResponse.setDetail(detail);
        return apiExceptionResponse;
    }
}