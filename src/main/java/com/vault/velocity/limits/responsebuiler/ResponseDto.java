package com.vault.velocity.limits.responsebuiler;

import lombok.Data;

@Data
public class ResponseDto {

    Integer responseCode;
    String responseType;
    String message;
    Object data;

}
