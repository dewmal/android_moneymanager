package com.moneymanager.entity;

import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8978746701418690916L;
	private int id;
	private Account accountid;
	private String transactiontype;
	private String description;
	private double amount;
	private Date datetime;

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Account getAccountid() {
		return accountid;
	}

	public void setAccountid(Account accountid) {
		this.accountid = accountid;
	}

	public String getTransactiontype() {
		return transactiontype;
	}

	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", accountid=" + accountid
				+ ", transactiontype=" + transactiontype + ", description="
				+ description + ", amount=" + amount + "]";
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	

	
	
}
