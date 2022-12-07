package com.rab3tech.vo;


public class StatementVO {
	private String accountNumber;
	private String name;
	private float debit;
	private float credit;
	private float tavBalance;
	private String remarks;
	private final float initialBalance=1000;
	
	
	
	
	public float getInitialBalance() {
		return initialBalance;
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
	public float getDebit() {
		return debit;
	}
	public void setDebit(float debit) {
		this.debit = debit;
	}
	public float getCredit() {
		return credit;
	}
	public void setCredit(float credit) {
		this.credit = credit;
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