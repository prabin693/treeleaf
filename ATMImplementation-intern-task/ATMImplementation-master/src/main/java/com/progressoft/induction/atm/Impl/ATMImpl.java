package com.progressoft.induction.atm.Impl;

import com.progressoft.induction.atm.ATM;
import com.progressoft.induction.atm.Banknote;
import com.progressoft.induction.atm.exceptions.InsufficientFundsException;
import com.progressoft.induction.atm.exceptions.NotEnoughMoneyInATMException;

import java.math.BigDecimal;
import java.util.*;

public class ATMImpl implements ATM {
    private final BankingSystemImpl bankingSystem = new BankingSystemImpl();


    @Override
    public List<Banknote> withdraw(String accountNumber, BigDecimal amount) {
        BigDecimal accountBalance = bankingSystem.getAccountBalance(accountNumber);
        if (accountBalance.compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds in account");
        }

        BigDecimal remainingAmount = amount;
        EnumMap<Banknote, Integer> withdrawnBanknotes = new EnumMap<>(Banknote.class);

        Banknote[] sortedBanknotes = sortBanknotesDescending();

        for (Banknote banknote : sortedBanknotes) {
            int banknoteCount = bankingSystem.getBanknoteCount(banknote);
            while (remainingAmount.compareTo(banknote.getValue()) >= 0 && banknoteCount > 0) {
                if (!withdrawnBanknotes.containsKey(banknote)) {
                    withdrawnBanknotes.put(banknote, 0);
                }
                withdrawnBanknotes.put(banknote, withdrawnBanknotes.get(banknote) + 1);
                remainingAmount = remainingAmount.subtract(banknote.getValue());
                banknoteCount--;
            }
        }

        if (remainingAmount.compareTo(BigDecimal.ZERO) == 0) {
            bankingSystem.debitAccount(accountNumber, amount);
            updateATMCash(withdrawnBanknotes);
            return convertWithdrawnBanknotesToList(withdrawnBanknotes);
        } else {
            throw new NotEnoughMoneyInATMException("Not enough money in ATM");
        }
    }

    private Banknote[] sortBanknotesDescending() {
        Banknote[] banknotes = Banknote.values();
        Arrays.sort(banknotes, Comparator.comparing(Banknote::getValue).reversed());
        return banknotes;
    }

    private void updateATMCash(EnumMap<Banknote, Integer> withdrawnBanknotes) {
        for (Map.Entry<Banknote, Integer> entry : withdrawnBanknotes.entrySet()) {
            int remainingCount = bankingSystem.getBanknoteCount(entry.getKey()) - entry.getValue();
            bankingSystem.setBanknoteCount(entry.getKey(), remainingCount);
        }
    }

    private List<Banknote> convertWithdrawnBanknotesToList(EnumMap<Banknote, Integer> withdrawnBanknotes) {
        List<Banknote> result = new ArrayList<>();
        for (Map.Entry<Banknote, Integer> entry : withdrawnBanknotes.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    @Override
    public BigDecimal checkBalance(String accountNumber) {
        return bankingSystem.getAccountBalance(accountNumber);
    }
}
