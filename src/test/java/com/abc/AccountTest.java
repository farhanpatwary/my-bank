package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Date;

public class AccountTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    
    // ----------------------------------------------------------
    // Create Account tests
    
    @Test
    public void CreateCheckingAccount() {
        Account a = new Account(Account.CHECKING);
        assertEquals(0, a.getAccountType());
    }

    @Test
    public void CreateSavingsAccount() {
        Account a = new Account(Account.SAVINGS);
        assertEquals(1, a.getAccountType());
    }

    @Test
    public void CreateMaxiSavingsAccount() {
        Account a = new Account(Account.MAXI_SAVINGS);
        assertEquals(2, a.getAccountType());
    }

    // ----------------------------------------------------------
    // Deposit Tests

    @Test
    public void DepositLessThan1() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("amount must be greater than zero");
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.deposit(0);
    }

    @Test
    public void DepositLessThan0() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("amount must be greater than zero");
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.deposit(-1);
    }

    // ----------------------------------------------------------
    // Withdraw Tests

    @Test
    public void ExceptionWhenWithdrawGreaterThanBalance() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("not enough balance to make transaction");
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.withdraw(200);
    }

    @Test
    public void ExceptionWhenWithdrawLessThanZero() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("amount must be greater than zero");
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.withdraw(-200);
    }

    @Test
    public void WithdrawEqualToBalance() {
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.deposit(200);
        checkingAccount.withdraw(200);
        assertEquals(0,checkingAccount.sumTransactions(),0);
    }

    // ----------------------------------------------------------
    // Interest Rate Tests

    @Test
    public void savingsLessThan1000Interest () {
        Account savingsAccount = new Account(Account.SAVINGS);
        savingsAccount.deposit(900);
        assertEquals(savingsAccount.interestEarned(),0.9,0);
    }

    @Test
    public void savings1000Interest() {
        Account savingsAccount = new Account(Account.SAVINGS);
        savingsAccount.deposit(1000);
        assertEquals(savingsAccount.interestEarned(),1,0);
    }

    @Test
    public void savingsMoreThan1000Interest() {
        Account savingsAccount = new Account(Account.SAVINGS);
        savingsAccount.deposit(1100);
        assertEquals(savingsAccount.interestEarned(),1.2,0);
    }

    // MaxiSavings gives 5% interest 
    // if the last withdrawal is older than 10d
    @Test
    public void maxiSavings5Interest() {
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        maxiSavingsAccount.deposit(1100);
        Transaction oldTransaction = new Transaction(-100);
        // 0 seconds from epoch time
        // i.e. epoch time -> January 1, 1970, 00:00:00 GMT
        long time = 0;
        Date oldDate = new Date(time);
        oldTransaction.setDate(oldDate);
        maxiSavingsAccount.addTransaction(oldTransaction);
        assertEquals(maxiSavingsAccount.interestEarned(),50,0);
    }

    // Interest rate should be 0.1%
    @Test
    public void maxiSavingsSmallInterest() {
        Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
        maxiSavingsAccount.deposit(1100);
        maxiSavingsAccount.withdraw(100);
        assertEquals(maxiSavingsAccount.interestEarned(),1,0);
    }

    @Test
    public void checkingInterest() {
        Account checkingAccount = new Account(Account.CHECKING);
        checkingAccount.deposit(1100);
        checkingAccount.withdraw(100);
        assertEquals(checkingAccount.interestEarned(),1,0);
    }

}

