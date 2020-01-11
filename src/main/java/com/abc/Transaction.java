package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

  	public setDate(Date date){
  		this.transactionDate = date;
  	}

    public Date getDate(){
    	return transactionDate;
    }

    public double getAmount(){
    	return amount;
    }
}
