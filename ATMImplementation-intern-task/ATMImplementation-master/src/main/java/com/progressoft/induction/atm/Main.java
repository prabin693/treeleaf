package com.progressoft.induction.atm;


import com.progressoft.induction.atm.Impl.ATMImpl;
import com.progressoft.induction.atm.Impl.BankingSystemImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String args[]){

    BankingSystemImpl bankingSystem=new BankingSystemImpl();
    ATM atm=new ATMImpl();
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter your account number");
        String accountNumber=scanner.nextLine();
        //amount to withdraw
        System.out.println("Enter amount to withdraw");
        BigDecimal amount=scanner.nextBigDecimal();

        //withdraw
        List<Banknote> banknotes=atm.withdraw(accountNumber,amount);
        //print withdraw notes and balance
      System.out.println("Your balance is "+atm.checkBalance(accountNumber));
        System.out.println("Your banknotes are "+banknotes);




    }
}
