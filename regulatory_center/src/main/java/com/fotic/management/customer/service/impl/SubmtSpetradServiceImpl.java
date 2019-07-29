package com.fotic.management.customer.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fotic.common.util.DateUtils;
import com.fotic.management.customer.dao.SubmtSpetradfMapper;
import com.fotic.management.customer.entity.SubmtSpetrad;
import com.fotic.management.customer.service.ISubmtSpetradService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class SubmtSpetradServiceImpl implements ISubmtSpetradService {

	@Autowired
	private SubmtSpetradfMapper submtSpetradfMapper;

	@Override
	public void insertSpetradFromCsv(List<String[]> list) throws ParseException {
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
		List<SubmtSpetrad> listInfo = new ArrayList<>();
		for (int row=0;row<list.size();row++) {
			SubmtSpetrad entity = new SubmtSpetrad();
			String col[] = list.get(row);//获取每行的数据
			entity.setAccount(col[0]);
			entity.setTradeid(col[1]);
			entity.setDeptcode(col[2]);
			entity.setSpectype(Integer.parseInt(col[3]));
			entity.setSpecdate(DateUtils.formatDate(col[4], "yyyyMMdd"));
			entity.setSpecmonth(Long.parseLong(col[5]));
			entity.setSpecmoney(Double.parseDouble(col[6]));
			entity.setSpecdetail("");
			entity.setDataStatus("1");//入库 默认1
			entity.setInsertDttm(date);
			entity.setDataSrc("2");//csv导入 默认2
			listInfo.add(entity);
		}
		submtSpetradfMapper.insertAll(listInfo);
	}

	@Override
	public void deleteByAccountAndDataSrc(String account, String dataSrc) {
		Example ea = new Example(SubmtSpetrad.class);
		Criteria crt = ea.createCriteria();
		crt.andCondition(" data_src ="+dataSrc);
		crt.andCondition(" account in("+account+")");
		submtSpetradfMapper.deleteByExample(ea);
	}
}
