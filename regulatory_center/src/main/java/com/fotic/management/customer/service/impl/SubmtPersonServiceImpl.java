package com.fotic.management.customer.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fotic.common.util.DateUtils;
import com.fotic.common.util.PubMethod;
import com.fotic.management.customer.dao.SubmtPersonMapper;
import com.fotic.management.customer.entity.SubmtPerson;
import com.fotic.management.customer.service.ISubmtPersonService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class SubmtPersonServiceImpl implements ISubmtPersonService {
	@Autowired
	private SubmtPersonMapper submtPersonMapper;

	@Override
	public void insertPersonFromCsv(List<String[]> list) throws ParseException {
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

	
	/**拼接实体
	 * @param list
	 * @param listInfo
	 * @throws NumberFormatException
	 */
	private void limitInsertCsvData(List<String[]> list,Date date) throws NumberFormatException {
		List<SubmtPerson> listInfo = new ArrayList<>();
		for (int row=0;row<list.size();row++) {
			SubmtPerson entity = new SubmtPerson();
			String col[] = list.get(row);//获取每行的数据
			entity.setName(col[0]);
			entity.setCerttype(col[1]);
			entity.setCertno(col[2]);
			entity.setDeptcode(col[3]);
			entity.setGender(col[4]);
			entity.setBirthday(DateUtils.formatDate(col[5], "yyyyMMdd"));
			entity.setMarriage(Integer.parseInt(col[6]));
			entity.setEdulevel(Integer.parseInt(col[7]));
			entity.setEdudegree(Integer.parseInt(col[8]));
			if(PubMethod.isEmpty(col[9])) {
				entity.setSpouse_name("");
			}else {
				entity.setSpouse_name(col[9]);
			}
			if(PubMethod.isEmpty(col[10])) {
				entity.setSpouse_certno("");
			}else {
				entity.setSpouse_certno(col[10]);
			}
			if(PubMethod.isEmpty(col[11])) {
				entity.setSpouse_certtype("");
			}else {
				entity.setSpouse_certtype(col[11]);
			}
			if(PubMethod.isEmpty(col[12])) {
				entity.setSpouse_office("");
			}else {
				entity.setSpouse_office(col[12]);
			}
			if(PubMethod.isEmpty(col[13])) {
				entity.setSpouse_tel("");
			}else {
				entity.setSpouse_tel(col[13]);
			}
			if(PubMethod.isEmpty(col[14])) {
				entity.setHometel("");
			}else {
				entity.setHometel(col[14]);
			}
			if(PubMethod.isEmpty(col[15])) {
				entity.setMobiletel("");
			}else {
				entity.setMobiletel(col[15]);
			}
			if(PubMethod.isEmpty(col[16])) {
				entity.setOfficetel("");
			}else {
				entity.setOfficetel(col[16]);
			}
			if(PubMethod.isEmpty(col[17])) {
				entity.setEmail("");
			}else {
				entity.setEmail(col[17]);
			}
			entity.setAddress(col[18]);
			entity.setZip(col[19]);
			entity.setResidence(col[20]);
			entity.setDataStatus("1");//入库 默认1
			entity.setInsertDttm(date);
			entity.setDataSrc("2");//csv导入 默认2
			listInfo.add(entity);
		}
		submtPersonMapper.insertAll(listInfo);
	}


	@Override
	public void deleteByCertTypeAndCertNo(String certType, String certNo, String dataSrc) {
		Example ea = new Example(SubmtPerson.class);
		Criteria crt = ea.createCriteria();
		crt.andCondition(" data_src ="+dataSrc);
		crt.andCondition(" certtype in("+certType+")");
		crt.andCondition(" certno in("+certNo+")");
		submtPersonMapper.deleteByExample(ea);
		
	}
	

	
}
