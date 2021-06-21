package com.product.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ServiceException extends Exception {

    private ErrorCode errorCode;

    /** Helper function to build the {@link ServiceException} instance. */
    public static ServiceException build(Integer code, ErrorType type, String message) {
        return new ServiceException(ErrorCode.builder()
                .code(code)
                .type(type)
                .message(message)
                .build());
    }
}
