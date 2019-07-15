package com.fotic.webproject.business.IM;

public class MsgEntity {
	private String from;
	private String to;
	private String msg;
	
	private int online;
	private int login;
	
	public MsgEntity() {
		super();
	}
	
	
	public MsgEntity(String from, String msg) {
		super();
		this.from = from;
		this.msg = msg;
	}

	public MsgEntity(String from, String to, String msg) {
		super();
		this.from = from;
		this.to = to;
		this.msg = msg;
	}

	public MsgEntity(String to, int online, int login) {
		super();
		this.to = to;
		this.online = online;
		this.login = login;
	}


	public int getOnline() {
		return online;
	}
	public void setOnline(int online) {
		this.online = online;
	}


	public int getLogin() {
		return login;
	}
	public void setLogin(int login) {
		this.login = login;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
