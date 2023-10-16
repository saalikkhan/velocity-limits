package com.vault.velocity.limits.service.impl;

import com.vault.velocity.limits.entity.LoadFundsEntity;
import com.vault.velocity.limits.repository.LoadFundsRepository;
import com.vault.velocity.limits.service.util.LoadFundsServiceUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class LoadFundsServiceImpl {

    private final LoadFundsRepository loadFundsRepository;
    private final ExecutorService executorService;

    protected LoadFundsEntity loadFunds(LoadFundsEntity fundsEntity) {
        log.debug("inside LoadFundsServiceImpl.loadFunds()");
        fundsEntity.setAccepted(meetsLoadConditions(fundsEntity));
        return loadFundsRepository.save(fundsEntity);
    }

    private boolean meetsLoadConditions(LoadFundsEntity fundsEntity) {
        log.debug("inside LoadFundsServiceImpl.loadFunds()");
        log.debug("Customer id -> " + fundsEntity.getCustomerId());
        Assert.notNull(fundsEntity.getTime(), "Failed, Time cannot be empty");
        Future<Boolean> weekTask = executorService.submit(() -> meetWeeklyConditions(fundsEntity));
        Future<Boolean> dailyTask = executorService.submit(() -> meetDailyConditions(fundsEntity));
        try {
            if(weekTask.get() && dailyTask.get()){
                return true;
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error occured while loading the fund --> " + e);
        }
        return false;
    }

    private boolean meetWeeklyConditions(LoadFundsEntity fundsEntity) {
        log.debug("inside LoadFundsServiceImpl.meetWeeklyConditions()");
        LocalDateTime startOfWeek = fundsEntity.getTime()
                .with(DayOfWeek.MONDAY)
                .withHour(0)
                .withMinute(0)
                .withSecond(0);

        LocalDateTime endOfWeek = startOfWeek
                .plusDays(6)
                .withHour(23)
                .withMinute(59)
                .withSecond(59);

        BigDecimal amountDepositedAlreadyWeek = loadFundsRepository.sumDepositsWeekly(
                fundsEntity.getCustomerId(), startOfWeek, endOfWeek);

        if (amountDepositedAlreadyWeek == null) {
            amountDepositedAlreadyWeek = BigDecimal.ZERO;
        }

        if (LoadFundsServiceUtil.checkDepositAmountWeeklyLimit(amountDepositedAlreadyWeek.add(fundsEntity.getLoad_amount()))) {
            return false;
        }

        return true;
    }

    private boolean meetDailyConditions(LoadFundsEntity fundsEntity) {
        log.debug("inside LoadFundsServiceImpl.meetDailyConditions()");
        LocalDateTime startOfDay = fundsEntity.getTime()
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        LocalDateTime endOfDay = fundsEntity.getTime()
                .withHour(23)
                .withMinute(59)
                .withSecond(59);

        var depositHistoryDay = loadFundsRepository.findDepositsWithinDay(
                fundsEntity.getCustomerId(), startOfDay, endOfDay);

        if (LoadFundsServiceUtil.checkNumberOfDeposits(depositHistoryDay.size())) {
            return false;
        }

        BigDecimal amountDepositedAlreadyDay = depositHistoryDay.parallelStream()
                .map(LoadFundsEntity::getLoad_amount).reduce(BigDecimal.ZERO, BigDecimal::add);

        if (LoadFundsServiceUtil.checkDepositAmountDailyLimit(amountDepositedAlreadyDay.add(fundsEntity.getLoad_amount()))) {
            return false;
        }

        return true;
    }


    //Above code is improvement in runtime then below code.

//    private boolean meetsLoadConditions(LoadFundsEntity fundsEntity) {
//        log.debug("inside LoadFundsServiceImpl.loadFunds()");
//        Assert.notNull(fundsEntity.getTime(), "Failed, Time cannot be empty");
//        LocalDateTime startOfWeek = fundsEntity.getTime()
//                .with(DayOfWeek.MONDAY)
//                .withHour(0)
//                .withMinute(0)
//                .withSecond(0);
//
//        LocalDateTime endOfWeek = startOfWeek
//                .plusDays(6)
//                .withHour(23)
//                .withMinute(59)
//                .withSecond(59);
//
//        List<LoadFundsEntity> depositsHistoryWeek = loadFundsRepository.findByCustomerIdAndTimeBetweenOrderByTimeDesc(fundsEntity.getCustomerId(),
//                startOfWeek,
//                endOfWeek).get();//orElseGet(ArrayList::new);
//
//        if(depositsHistoryWeek.isEmpty()){
//            return true;
//        }
//
//        //BigDecimal amountDepositedAlreadyWeek = depositsHistoryWeek.stream()
//        // .map(elem -> elem.getLoad_amount().add(fundsEntity.getLoad_amount())).reduce(BigDecimal::add).get();
//        BigDecimal amountDepositedAlreadyWeek = fundsEntity.getLoad_amount();
//        for(LoadFundsEntity e : depositsHistoryWeek){
//            amountDepositedAlreadyWeek = amountDepositedAlreadyWeek.add(e.getLoad_amount());
//        }
//
//        if(LoadFundsServiceUtil.checkDepositAmountWeeklyLimit(amountDepositedAlreadyWeek)){
//            return false;
//        }
//
//        LocalDateTime startOfDay = fundsEntity.getTime()
//                .withHour(0)
//                .withMinute(0)
//                .withSecond(0)
//                .withNano(0);
//
//        LocalDateTime endOfDay = fundsEntity.getTime()
//                .withHour(23)
//                .withMinute(59)
//                .withSecond(59);
//
//        List<LoadFundsEntity> depositHistoryDay = depositsHistoryWeek.stream()
//                .filter(record -> record.getTime().isAfter(startOfDay) && record.getTime().isBefore(endOfDay))
//                .toList();
//
//        if(LoadFundsServiceUtil.checkNumberOfDeposits(depositHistoryDay.size())){
//            return false;
//        }
//
//        //depositHistoryDay.stream().map(elem -> elem.getLoad_amount().add(fundsEntity.getLoad_amount())).reduce(BigDecimal::add).get();
//        BigDecimal amountDepositedAlreadyDay = fundsEntity.getLoad_amount();
//        for(LoadFundsEntity e : depositHistoryDay){
//            amountDepositedAlreadyDay = amountDepositedAlreadyDay.add(e.getLoad_amount());
//        }
//
//        if(LoadFundsServiceUtil.checkDepositAmountDailyLimit(amountDepositedAlreadyDay)){
//            return false;
//        }
//
//        return true;
//    }

}
