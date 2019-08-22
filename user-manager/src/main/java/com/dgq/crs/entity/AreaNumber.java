package com.dgq.crs.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.ColumnType;

/*
* @Description: public class AreaNumber{ }
* @author dgq 
* @date 2019年5月16日
*/
@Entity
@Table(name="REG.CRS_IMP_XZQH")
public class AreaNumber {
	
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String xzqhlx;
	
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String xzqhdm;
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String xzqhmc;
	@ColumnType(jdbcType=JdbcType.DATE)
	private LocalDate yeardt;
	@ColumnType(jdbcType=JdbcType.DATE)
	private LocalDateTime dataImpTime;
	@ColumnType(jdbcType=JdbcType.VARCHAR)
	private String dataImpUser;
	
	public String getXzqhlx() {
		return xzqhlx;
	}
	public void setXzqhlx(String xzqhlx) {
		this.xzqhlx = xzqhlx;
	}
	public String getXzqhdm() {
		return xzqhdm;
	}
	public void setXzqhdm(String xzqhdm) {
		this.xzqhdm = xzqhdm;
	}
	public String getXzqhmc() {
		return xzqhmc;
	}
	public void setXzqhmc(String xzqhmc) {
		this.xzqhmc = xzqhmc;
	}
	public LocalDate getYeardt() {
		return yeardt;
	}
	public void setYeardt(LocalDate yeardt) {
		this.yeardt = yeardt;
	}
	public LocalDateTime getDataImpTime() {
		return dataImpTime;
	}
	public void setDataImpTime(LocalDateTime dataImpTime) {
		this.dataImpTime = dataImpTime;
	}
	public String getDataImpUser() {
		return dataImpUser;
	}
	public void setDataImpUser(String dataImpUser) {
		this.dataImpUser = dataImpUser;
	}
}
