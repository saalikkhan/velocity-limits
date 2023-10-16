package com.vault.velocity.limits.service.util;

import com.vault.velocity.limits.dto.req.LoadFundsReqDto;
import com.vault.velocity.limits.dto.resp.LoadFundsResDto;
import com.vault.velocity.limits.entity.LoadFundsEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LoadFundsServiceUtil {

    //I am checking all the conditions of adding the funds here
    //In case of any condition failure like mentioned below I can throw BusinessException
    //But currenlty not throwing because we have to add the record either way (accepted true & false)
    //---A maximum of $5,000 can be loaded per day
    //---A maximum of $20,000 can be loaded per week
    //---A maximum of 3 loads can be performed per day, regardless of amount
    //But for now I commented the code, But I believe the better practice is to throw it.

    private static final BigDecimal DAILY_DEPOSIT_LIMIT = BigDecimal.valueOf(5000);
    private static final BigDecimal WEEKLY_DEPOSIT_LIMIT = BigDecimal.valueOf(20000);
    private static final Integer MAX_DEPOSIT_DAILY = 3;

    public static LoadFundsEntity mapReqDtoToEntity(LoadFundsReqDto dto) {
        LoadFundsEntity entity = new LoadFundsEntity();
        //BeanUtils.copyProperties(dto, entity);
        entity.setTime(dto.getTime());
        entity.setCurrencyFormat(extractCurrencyType(dto.getLoad_amount()));
        entity.setLoad_amount(convertCurrencyStringToBigDecimal(dto.getLoad_amount()));
        entity.setId(Long.valueOf(dto.getId()));
        entity.setCustomerId(Long.valueOf(dto.getCustomer_id()));
        return entity;
    }

    public static LoadFundsResDto mapEntityToResDto(LoadFundsEntity entity) {
        LoadFundsResDto dto = new LoadFundsResDto();
        //BeanUtils.copyProperties(entity, dto);
        dto.setId(entity.getId().toString());
        dto.setCustomer_id(entity.getCustomerId().toString());
        dto.setAccepted(entity.isAccepted());
        return dto;
    }

    public static boolean checkNumberOfDeposits(Integer depositsAlreadyDone) {
        Integer i = depositsAlreadyDone + 1;
//        if (i.compareTo(MAX_DEPOSIT_DAILY) >= 0) {
//            throw new BusinessException("deposit-limit-exceeded",
//            "Deposits quota allowed (Max 3) for per day exceeded for the day", HttpStatus.BAD_REQUEST);
//        }
        return i.compareTo(MAX_DEPOSIT_DAILY) > 0;
    }

    public static boolean checkDepositAmountDailyLimit(BigDecimal amount) {
//        if (amount.compareTo(DAILY_DEPOSIT_LIMIT) >= 0) {
//            throw new BusinessException("deposit-limit-exceeded",
//            "Deposit amount limit exceeded for the day", HttpStatus.BAD_REQUEST);
//        }
        return amount.compareTo(DAILY_DEPOSIT_LIMIT) > 0;
    }

    public static boolean checkDepositAmountWeeklyLimit(BigDecimal amount) {
//        if (amount.compareTo(WEEKLY_DEPOSIT_LIMIT) >= 0) {
//            throw new BusinessException("deposit-limit-exceeded",
//            "Deposit amount limit exceeded for the week", HttpStatus.BAD_REQUEST);
//        }
        return amount.compareTo(WEEKLY_DEPOSIT_LIMIT) > 0;
    }

    public static BigDecimal convertCurrencyStringToBigDecimal(String amount) {
        Assert.hasText(amount, "Please provide the amount in the currency format");
        return new BigDecimal(amount.trim().replaceAll("[^0-9.]+", ""));
    }

    public static String extractCurrencyType(String amount) {
        Assert.hasText(amount, "Please provide the amount in the currency format");
        //Can put currency symbol extractor regex and validator
        return String.valueOf(amount.trim().charAt(0));
    }

}
