package com.fotic.management.trade.service;
import java.text.ParseException;
import java.util.List;

import com.fotic.management.trade.entity.SubmitTrade;
public interface SubmitTradeService{
	
	public void insertTradeFromCsv(List<String[]> list)  throws ParseException;
	
	/**
	 * 查询交易信息
	 * @param dataSrc 默认2 CSV
	 * @param certno
	 * @param dataStatus
	 * @param dataSrc
	 * @return
	 */
	public List<SubmitTrade> findList(String certno,String dataStatus, String dataSrc);
	
	public void deleteByAccountAndDataSrc(String account,String dataSrc);
}
