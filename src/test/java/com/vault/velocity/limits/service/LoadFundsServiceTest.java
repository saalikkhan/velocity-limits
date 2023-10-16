package com.vault.velocity.limits.service;

import com.vault.velocity.limits.dto.req.LoadFundsReqDto;
import com.vault.velocity.limits.dto.resp.LoadFundsResDto;
import com.vault.velocity.limits.service.impl.LoadFundsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

class LoadFundsServiceTest {

    @Mock
    private LoadFundsService loadFundsService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testLoadFunds() {
        LoadFundsReqDto request = setLoadFundsReqDto();
        LoadFundsResDto expectedResponse = setLoadFundsResDto();
        when(loadFundsService.loadFunds(request)).thenReturn(expectedResponse);
    }

    LoadFundsResDto setLoadFundsResDto() {
        LoadFundsResDto response = new LoadFundsResDto();
        response.setAccepted(true);
        response.setCustomer_id("1");
        response.setId("1");
        return response;
    }

    LoadFundsReqDto setLoadFundsReqDto() {
        LoadFundsReqDto request = new LoadFundsReqDto();
        request.setTime(LocalDateTime.now());
        request.setId("1");
        request.setCustomer_id("1");
        request.setLoad_amount("$12.00");
        return request;
    }

}
