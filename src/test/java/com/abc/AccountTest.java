package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

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
}

