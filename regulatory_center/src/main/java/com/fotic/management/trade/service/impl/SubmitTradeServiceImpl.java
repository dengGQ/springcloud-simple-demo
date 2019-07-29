package com.fotic.management.trade.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fotic.common.util.DateUtils;
import com.fotic.management.trade.dao.SubmitTradeMapper;
import com.fotic.management.trade.entity.SubmitTrade;
import com.fotic.management.trade.service.SubmitTradeService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class SubmitTradeServiceImpl implements SubmitTradeService {

	@Autowired
	private SubmitTradeMapper submitTradeMapper;

	@Override
	public void insertTradeFromCsv(List<String[]> list) throws ParseException {
		Date date = new Date();
		int pointsDataLimit = 1000;//限制条数
		Integer size = list.size();
		//判断是否有必要分批
		if(pointsDataLimit<size) {
			int part = size/pointsDataLimit;//分批数
			System.out.println("共有 ： "+size+"条，！"+" 分为 ："+part+"批");
			for (int i = 0; i < part; i++) {
				//1000条
				List<String[]> listPage = list.subList(0, pointsDataLimit);
				limitInsertCsvData(listPage,date);
				list.subList(0, pointsDataLimit).clear();
			}
			if(!list.isEmpty()) {//表示最后剩下的数据
				limitInsertCsvData(list,date);
			}
		}else{
			limitInsertCsvData(list,date);
		}
	}

	/**
	 * @param list
	 * @param listInfo
	 * @throws NumberFormatException
	 */
	private void limitInsertCsvData(List<String[]> list,Date date) throws NumberFormatException {
		List<SubmitTrade> listInfo = new ArrayList<>();
		for (int row=0;row<list.size();row++) {
			SubmitTrade entity = new SubmitTrade();
			String col[] = list.get(row);//获取每行的数据
			
			entity.setName(col[0]);
			entity.setCerttype(col[1]);
			entity.setCertno(col[2]);
			entity.setDeptcode(col[3]);
			entity.setGeneraltype(col[4]);
			entity.setType(col[5]);
			
			entity.setAccount(col[6]);
			entity.setTradeid(col[7]);
			entity.setAreacode(col[8]);
			entity.setDateopened(DateUtils.formatDate(col[9], "yyyyMMdd"));
			entity.setDateclosed(DateUtils.formatDate(col[10], "yyyyMMdd"));
			entity.setCurrency(col[11]);
			
			entity.setCreditlimit(new BigDecimal(col[12]));
			entity.setShareaccount(new BigDecimal(col[13]));
			entity.setMaxdebt(new BigDecimal(col[14]));
			entity.setGuaranteeway(Integer.parseInt(col[15]));
			entity.setTermsfreq(col[16]);
			entity.setMonthduration(Integer.parseInt(col[17]));
			
			entity.setMonthunpaid(Integer.parseInt(col[18]));
			entity.setBillingdate(DateUtils.formatDate(col[19], "yyyyMMdd"));
			entity.setRecentpaydate(DateUtils.formatDate(col[20], "yyyyMMdd"));
			entity.setScheduledamount(new BigDecimal(col[21]));
			entity.setActualpayamount(new BigDecimal(col[22]));
			entity.setBalance(new BigDecimal(col[23]));
			
			entity.setCurtermspastdue(Integer.parseInt(col[24]));
			entity.setAmountpastdue(new BigDecimal(col[25]));
			entity.setAmountpastdue30(new BigDecimal(col[26]));
			entity.setAmountpastdue60(new BigDecimal(col[27]));
			entity.setAmountpastdue90(new BigDecimal(col[28]));
			entity.setApastdue180(new BigDecimal(col[29]));
			
			entity.setTermspastdue(Integer.parseInt(col[30]));
			entity.setMaxtermspastdue(Integer.parseInt(col[31]));
			entity.setClass5stat(Integer.parseInt(col[32]));
			entity.setAccountstat(Integer.parseInt(col[33]));
			entity.setPaystat24month(col[34]);
			
			entity.setDataStatus("1");//入库 默认1
			entity.setInsertDttm(date);
			entity.setDataSrc("2");//csv导入 默认2
			listInfo.add(entity);
		}
		submitTradeMapper.insertAll(listInfo);
	}

	@Override
	public List<SubmitTrade> findList(String certno,String dataStatus, String dataSrc) {
		return submitTradeMapper.findList(certno, dataStatus, dataSrc);
	}

	@Override
	public void deleteByAccountAndDataSrc(String account, String dataSrc) {
		Example ea = new Example(SubmitTrade.class);
		Criteria crt = ea.createCriteria();
		crt.andCondition(" data_src ="+dataSrc);
		crt.andCondition(" account in("+account+")");
		submitTradeMapper.deleteByExample(ea);
	}
}
