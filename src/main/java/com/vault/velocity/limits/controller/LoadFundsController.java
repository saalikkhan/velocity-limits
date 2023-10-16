package com.vault.velocity.limits.controller;

import com.vault.velocity.limits.constants.AppConstants;
import com.vault.velocity.limits.dto.req.LoadFundsReqDto;
import com.vault.velocity.limits.dto.resp.LoadFundsResDto;
import com.vault.velocity.limits.responsebuiler.ResponseBuilder;
import com.vault.velocity.limits.responsebuiler.ResponseConstants;
import com.vault.velocity.limits.responsebuiler.ResponseDto;
import com.vault.velocity.limits.service.LoadFunds;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/funds")
@Slf4j
@Validated
@RequiredArgsConstructor
@CrossOrigin("*")
public class LoadFundsController {

    private final LoadFunds loadFunds;

    @PostMapping("/load")
    public ResponseEntity<ResponseDto> loadFunds(@RequestBody @Valid LoadFundsReqDto request) {
        LoadFundsResDto response = loadFunds.loadFunds(request);
        if(!response.isAccepted()){
            var message = AppConstants.FAILED_TO_LOAD_FUND;
            return ResponseBuilder.sendResponse(response, HttpStatus.OK, ResponseConstants.WARNING_CODE, message);
        }
        var message = AppConstants.FUND_SUCCESSFULLY_LOADED;
        return ResponseBuilder.sendResponse(response, HttpStatus.OK, ResponseConstants.SUCCESS_CODE, message);
    }
}
