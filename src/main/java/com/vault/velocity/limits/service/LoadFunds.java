package com.vault.velocity.limits.service;

import com.vault.velocity.limits.dto.req.LoadFundsReqDto;
import com.vault.velocity.limits.dto.resp.LoadFundsResDto;

public interface LoadFunds {

    LoadFundsResDto loadFunds(LoadFundsReqDto funds);

}
