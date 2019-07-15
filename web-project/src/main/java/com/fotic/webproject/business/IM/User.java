package com.fotic.webproject.business.IM;

public class User {
	private String sessionid;
	
	private String username;
	
	private String  pwd;

	
	public User() {
		super();
	}
	public User(String sessionid, String username) {
		super();
		this.sessionid = sessionid;
		this.username = username;
	}
	public String getSessionid() {
		return sessionid;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}
