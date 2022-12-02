package com.rab3tech.utils;

//An enum is a special "class" that represents a group of constants 
//(unchangeable variables, like final variables). To create an enum , 
public enum AccountStatusEnum {

	PENDING("AS01"), PROCESSING("AS02"), DORMANT("AS03"), APPROVED("AS04"), ACTIVE("AS05");

	private String code;

	AccountStatusEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
