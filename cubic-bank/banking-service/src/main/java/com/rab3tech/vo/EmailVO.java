package com.rab3tech.vo;

public class EmailVO {
	private String name;
	private String to;
	private String[] too;
	private String from;
	private String subject;
	private String body;
	private String registrationlink;
	private String username;
	private String password;
	
	public EmailVO() {}

	public EmailVO(String to, String from, String subject, String body,String name) {
		this.to = to;
		this.from = from;
		this.subject = subject;
		this.name=name;
		this.body = body;
	}
	public EmailVO(String[] too, String from, String subject, String body,String name) {
		this.too = too;
		this.from = from;
		this.subject = subject;
		this.name=name;
		this.body = body;
	}
	
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRegistrationlink() {
		return registrationlink;
	}

	public void setRegistrationlink(String registrationlink) {
		this.registrationlink = registrationlink;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String[] getToo() {
		return too;
	}

	public void setToo(String[] too) {
		this.too = too;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
