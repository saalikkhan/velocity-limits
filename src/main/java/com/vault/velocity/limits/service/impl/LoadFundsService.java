package com.vault.velocity.limits.service.impl;

import com.vault.velocity.limits.dto.req.LoadFundsReqDto;
import com.vault.velocity.limits.dto.resp.LoadFundsResDto;
import com.vault.velocity.limits.entity.LoadFundsEntity;
import com.vault.velocity.limits.service.LoadFunds;
import com.vault.velocity.limits.service.util.LoadFundsServiceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoadFundsService implements LoadFunds {

    private final LoadFundsServiceImpl loadFundsServiceImpl;

    @Override
    public LoadFundsResDto loadFunds(LoadFundsReqDto funds) {
        log.debug("inside LoadFundsService.loadFunds()");
        LoadFundsEntity loadFundsEntity = loadFundsServiceImpl.loadFunds(LoadFundsServiceUtil.mapReqDtoToEntity(funds));
        return LoadFundsServiceUtil.mapEntityToResDto(loadFundsEntity);
    }
}
