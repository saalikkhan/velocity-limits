package com.vault.velocity.limits.exceptions;

import com.vault.velocity.limits.responsebuiler.ResponseBuilder;
import com.vault.velocity.limits.responsebuiler.ResponseConstants;
import com.vault.velocity.limits.responsebuiler.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;

@ControllerAdvice
@Slf4j
public class ExceptionsHandler {

    @Value("${spring.application.name}")
    private String appName;

    private static final String EXCEPTION_MESSAGE = "Error Occured while processing the Request/Response";
    private static final String LOG_MESSAGE = "Failed/Business Excpetion while processing the Request, Error Message";

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ResponseDto> handleBusinessExeptions(BusinessException ex, HttpServletRequest request) {
        var businessExceptionDto = buildBusinessException(ex, request);
        log.error("{} : {} , Cause : {}, Timestamp : {}", LOG_MESSAGE, ex.getMessage(), ex.getCause(), businessExceptionDto.getTimestamp());
        log.error("Error Id -> {},,, \n {}", businessExceptionDto.getErrorId(), ex.getStackTrace());
        return ResponseBuilder.sendResponse(businessExceptionDto, ex.getHttpStatus(), 1, EXCEPTION_MESSAGE);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDto> handleDataIntegrityViolationExceptionExeptions(DataIntegrityViolationException ex, HttpServletRequest request) {
        BusinessException businessException = new BusinessException(ex.getRootCause().getMessage(), "Duplicate Entry", HttpStatus.BAD_REQUEST);
        var businessExceptionDto = buildBusinessException(businessException, request);
        log.error("{} : {} , Cause : {}, Timestamp : {}", LOG_MESSAGE, ex.getMessage(), ex.getCause(), businessExceptionDto.getTimestamp());
        log.error("Error Id -> {},,, \n {}", businessExceptionDto.getErrorId(), ex.getStackTrace());
        return ResponseBuilder.sendResponse(businessExceptionDto, businessException.getHttpStatus(), ResponseConstants.BUSINESS_EXCEPTION_CODE, EXCEPTION_MESSAGE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        StringBuffer completeErrorMessageForEachField = new StringBuffer();
        Arrays.stream(ex.getDetailMessageArguments()).forEach(completeErrorMessageForEachField::append);
        BusinessException businessException = new BusinessException(completeErrorMessageForEachField.toString(), "Field Validation Error", HttpStatus.BAD_REQUEST);
        var businessExceptionDto = buildBusinessException(businessException, request);
        log.error("{} : {} , Cause : {}, Timestamp : {}", LOG_MESSAGE, ex.getMessage(), ex.getCause(), businessExceptionDto.getTimestamp());
        log.error("Error Id -> {},,, \n {}", businessExceptionDto.getErrorId(), ex.getStackTrace());
        return ResponseBuilder.sendResponse(businessExceptionDto, businessException.getHttpStatus(), ResponseConstants.BUSINESS_EXCEPTION_CODE, EXCEPTION_MESSAGE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> handleExeptions(Exception ex, HttpServletRequest request) {
        var exceptionResponseDto = buildException(ex, request);
        log.error("{} : {} , Cause : {}, Timestamp : {}", LOG_MESSAGE, ex.getCause().getMessage(), ex.getCause(), exceptionResponseDto.getTimestamp());
        log.error("Error Id -> {},,, \n {}", exceptionResponseDto.getErrorId(), ex.getStackTrace());
        return ResponseBuilder.sendResponse(exceptionResponseDto, HttpStatus.INTERNAL_SERVER_ERROR, ResponseConstants.EXCEPTION_CODE, EXCEPTION_MESSAGE);
    }

    private BusinessExceptionDto buildBusinessException(BusinessException ex, HttpServletRequest request) {
        return new BusinessExceptionDto(appName + "-" + Instant.now().getNano(),
                ex.getErrorCode(),
                ex.getLocalizedMessage(),
                ex.getHttpStatus().value(),
                ex.getHttpStatus().name(),
                request.getRequestURI(),
                request.getMethod(),
                LocalDateTime.now());
    }

    private ExceptionResponseDto buildException(Exception ex, HttpServletRequest request) {
        return new ExceptionResponseDto(appName + "-" + Instant.now().getNano(),
                ex.getClass().getName(),
                ex.getCause().getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                request.getRequestURI(),
                request.getMethod(),
                LocalDateTime.now());
    }

}
