package com.rab3tech.vo;

import java.sql.Timestamp;

public class StatementVO {
	private String accountNumber;
	private String name;
	private Timestamp doe;
	private float amount;
	private float tavBalance;
	private String remarks;
	private String accountType;
	
	public Timestamp getDoe() {
		return doe;
	}
	public void setDoe(Timestamp doe) {
		this.doe = doe;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "StatementVO [accountNumber=" + accountNumber + ", name=" + name + ", doe=" + doe + ", amount=" + amount
				+ ", tavBalance=" + tavBalance + ", remarks=" + remarks + ", accountType=" + accountType + "]";
	}
	
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getTavBalance() {
		return tavBalance;
	}
	public void setTavBalance(float tavBalance) {
		this.tavBalance = tavBalance;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
	
	
}