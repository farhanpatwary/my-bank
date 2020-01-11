package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

	public void withdraw(double amount) {
		double accountBalance = sumTransactions();
		if(accountBalance - amount < 0){
			throw new IllegalArgumentException("not enough balance to make transaction");
		} else if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } else {
	        transactions.add(new Transaction(-amount));
	    }
	}

    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
	//            case SUPER_SAVINGS:
	//                if (amount <= 4000)
	//                    return 20;
            case MAXI_SAVINGS:
                int days = daysSinceLastWithdrawal();
                if(days != -1 && days > 10){
                    return amount * 0.05;
                }
                return amount * 0.001;
            default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

    public int daysSinceLastWithdrawal(){
        for (int i = list.size(); i > 0; i--) {
            Transaction currentTransaction = list.get(i);
            if(currentTransaction.getAmount() < 0){
                Date currentDate = DateProvider.getInstance().now();
                Date transactionDate = currentTransaction.getDate();
                long diffTime = currentDate.getTime() - transactionDate.getTime();
                long diffDays = diffTime / (1000 * 60 * 60 * 24);
                return diffDays;
            }
        }
        return -1;
    }
}
