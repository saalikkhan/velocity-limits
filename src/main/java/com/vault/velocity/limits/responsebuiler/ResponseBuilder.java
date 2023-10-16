package com.vault.velocity.limits.responsebuiler;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResponseBuilder {

    public static ResponseEntity<ResponseDto> sendResponse(Object data,
                                                           HttpStatus httpStatus,
                                                           Integer responseCode,
                                                           String message) {

        ResponseDto responseDto = new ResponseDto();
        responseDto.setResponseCode(responseCode);
        responseDto.setResponseType(checkRespCode(responseCode));
        responseDto.setMessage(message);
        responseDto.setData(data);
        return new ResponseEntity<>(responseDto, new HttpHeaders(), httpStatus);
    }

    private static String checkRespCode(Integer responseCode) {
        return switch (responseCode) {
            case 1 -> ResponseConstants.BUSINESS_EXCEPTION;
            case 0 -> ResponseConstants.SUCCESS;
            case 2 -> ResponseConstants.WARNING;
            case -1 -> ResponseConstants.EXCEPTION;
            default -> "Unknown Code";
        };
    }

}
