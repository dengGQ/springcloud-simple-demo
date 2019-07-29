package com.fotic.management.sms.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author h
 *
 */
@Entity
@Table(name="RHZX_SUBMT_SMS_TIME")
public class RhzxSubmtSmsTime {
	
	private String starttime;
	
	private String stoptime;

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getStoptime() {
		return stoptime;
	}

	public void setStoptime(String stoptime) {
		this.stoptime = stoptime;
	}
	

}
