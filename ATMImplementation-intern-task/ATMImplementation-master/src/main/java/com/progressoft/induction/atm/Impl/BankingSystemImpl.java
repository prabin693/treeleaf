package com.progressoft.induction.atm.Impl;

import com.progressoft.induction.atm.BankingSystem;
import com.progressoft.induction.atm.Banknote;
import com.progressoft.induction.atm.exceptions.AccountNotFoundException;
import com.progressoft.induction.atm.exceptions.InsufficientFundsException;
import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class BankingSystemImpl implements BankingSystem {
   Map<String, BigDecimal> accountBalanceMap = new HashMap<String, BigDecimal>();
   EnumMap<Banknote,Integer> atmCashMap = new EnumMap<>(Banknote.class);

    public BankingSystemImpl() {
        atmCashMap.put(Banknote.FIFTY_JOD,10);
        atmCashMap.put(Banknote.TWENTY_JOD,20);
        atmCashMap.put(Banknote.TEN_JOD,100);
        atmCashMap.put(Banknote.FIVE_JOD,100);

        accountBalanceMap.put("123456789", BigDecimal.valueOf(1000.0));
        accountBalanceMap.put("111111111", BigDecimal.valueOf(1000.0));
        accountBalanceMap.put("222222222", BigDecimal.valueOf(1000.0));
        accountBalanceMap.put("333333333", BigDecimal.valueOf(1000.0));
        accountBalanceMap.put("444444444", BigDecimal.valueOf(1000.0));
    }

    public BigDecimal sumOfMoneyInAtm(){
        // your code
        BigDecimal sum = BigDecimal.valueOf(0.0);
        for (Map.Entry<Banknote, Integer> entry : atmCashMap.entrySet()) {
            sum = sum.add(entry.getKey().getValue().multiply(BigDecimal.valueOf(entry.getValue())));
        }
        return sum;
    }


    @Override
    public BigDecimal getAccountBalance(String accountNumber){
        //your code
        BigDecimal accountBalance = accountBalanceMap.get(accountNumber);
        if(accountBalance == null){
            throw new AccountNotFoundException("Account not found");
        }
        return accountBalance;

    }

    @Override
    public void debitAccount(String accountNumber, BigDecimal amount) {
        //your code
        BigDecimal accountBalance = accountBalanceMap.get(accountNumber);
        if(accountBalance == null){
            throw new AccountNotFoundException("Account not found");
        }
        if(accountBalance.compareTo(amount) < 0){
            throw new InsufficientFundsException("Insufficient funds");
        }
        accountBalanceMap.put(accountNumber,accountBalance.subtract(amount));
    }

    public int getBanknoteCount(Banknote banknote) {
        return atmCashMap.get(banknote);

    }
    public void setBanknoteCount(Banknote banknote, int count) {
        atmCashMap.put(banknote, count);
    }
}
