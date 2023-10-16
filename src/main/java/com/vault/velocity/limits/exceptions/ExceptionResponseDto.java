package com.vault.velocity.limits.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ExceptionResponseDto {

    private final String errorId;
    private final String errorType;
    private final String message;
    private final Integer statusCode;
    private final String statusName;
    private final String path;
    private final String method;
    private final LocalDateTime timestamp;

}
