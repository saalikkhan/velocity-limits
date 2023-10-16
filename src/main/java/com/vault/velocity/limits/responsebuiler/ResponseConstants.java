package com.vault.velocity.limits.responsebuiler;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResponseConstants {

    public static final String BUSINESS_EXCEPTION = "B";
    public static final Integer BUSINESS_EXCEPTION_CODE = 1;
    public static final String EXCEPTION = "E";
    public static final Integer EXCEPTION_CODE = -1;
    public static final String SUCCESS = "S";
    public static final Integer SUCCESS_CODE = 0;
    public static final String WARNING = "W";
    public static final Integer WARNING_CODE = 2;

}
