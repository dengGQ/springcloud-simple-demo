package com.dgq.crs.entity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.ibatis.type.JdbcType;

import tk.mybatis.mapper.annotation.ColumnType;

/*
* @Description: 汇率管理
* @author dgq 
* @date 2018年12月7日
*/
@Entity
@Table(name="reg.exchange_rate_mgr")
public class ExchangeRateMgr {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="SELECT APP_SEQUENCE.nextval from dual")
	private Integer id;
	
	/**
	 * 废弃，序号在页面生成
	 */
	@Deprecated
	@ColumnType(jdbcType=JdbcType.INTEGER)
	private Integer num;
	
	/**
	 * 汇率时间
	 */
	@ColumnType(jdbcType=JdbcType.DATE)
	private Date datee;

	/**
	 * 汇率
	 */
	@ColumnType(jdbcType=JdbcType.NUMERIC)
	private BigDecimal exchangeRate;
	
	@Transient
	private String dateeStr;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Date getDatee() {
		return datee;
	}

	public void setDatee(Date datee) {
		this.datee = datee;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getDateeStr() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(datee);
	}

	public void setDateeStr(String dateeStr) {
		this.dateeStr = dateeStr;
	}
	
	
}
