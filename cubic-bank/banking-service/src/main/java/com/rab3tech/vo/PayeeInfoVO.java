package com.rab3tech.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.sql.Timestamp;


public class PayeeInfoVO {
	
	private int id;
	private String payeeAccountNo;
	
	@NotNull
	@Size(min = 5, max = 50)
	private String payeeName;
	@NotNull
	@Size(min = 5, max = 30)
	private String payeeNickName;
	private String accNumberConfirm;
	private String customerId;
	private Timestamp doe;
	private Timestamp dom;
	private String remarks;
	private String payeeStatus;
	private int urn;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPayeeAccountNo() {
		return payeeAccountNo;
	}
	public void setPayeeAccountNo(String payeeAccountNo) {
		this.payeeAccountNo = payeeAccountNo;
	}
	public String getPayeeName() {
		return payeeName;
	}
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	public String getPayeeNickName() {
		return payeeNickName;
	}
	public void setPayeeNickName(String payeeNickName) {
		this.payeeNickName = payeeNickName;
	}
	public String getAccNumberConfirm() {
		return accNumberConfirm;
	}
	public void setAccNumberConfirm(String accNumberConfirm) {
		this.accNumberConfirm = accNumberConfirm;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public Timestamp getDoe() {
		return doe;
	}
	public void setDoe(Timestamp doe) {
		this.doe = doe;
	}
	public Timestamp getDom() {
		return dom;
	}
	public void setDom(Timestamp dom) {
		this.dom = dom;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getPayeeStatus() {
		return payeeStatus;
	}
	public void setPayeeStatus(String payeeStatus) {
		this.payeeStatus = payeeStatus;
	}
	public int getUrn() {
		return urn;
	}
	public void setUrn(int urn) {
		this.urn = urn;
	}
	
}