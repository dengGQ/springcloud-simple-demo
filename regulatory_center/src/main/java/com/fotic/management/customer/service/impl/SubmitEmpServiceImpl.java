package com.fotic.management.customer.service.impl;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fotic.common.util.PubMethod;
import com.fotic.management.customer.dao.SubmitEmpMapper;
import com.fotic.management.customer.entity.SubmitEmp;
import com.fotic.management.customer.service.ISubmitEmpService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
@Service
public class SubmitEmpServiceImpl implements ISubmitEmpService{
	@Autowired
	private SubmitEmpMapper submitEmpMapper;

	@Override
	public void insertEmpFromCsv(List<String[]> list) throws ParseException {
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
		List<SubmitEmp> listInfo = new ArrayList<>();
		for (int row=0;row<list.size();row++) {
			SubmitEmp entity = new SubmitEmp();
			String col[] = list.get(row);//获取每行的数据
			entity.setName(col[0]);
			entity.setCerttype(col[1]);
			entity.setCertno(col[2]);
			entity.setDeptcode(col[3]);
			entity.setOccupation(col[4]);
			entity.setCompany(col[5]);
			entity.setIndustry(col[6]);
			
			if(PubMethod.isEmpty(col[7])) {
				entity.setOccaddress("");
			}else{
				entity.setOccaddress(col[7]);
			}
			
			if(PubMethod.isEmpty(col[8])) {
				entity.setOcczip("");
			}else{
				entity.setOcczip(col[8]);
			}
			
			if(PubMethod.isEmpty(col[9])) {
				entity.setStartyear(0);
			}else{
				entity.setStartyear(Integer.parseInt(col[9]));
			}
			entity.setDuty(Integer.parseInt(col[10]));
			entity.setCaste(Integer.parseInt(col[11]));
			if(PubMethod.isEmpty(col[12])) {
				entity.setAnnualincome(0.00);
			}else {
				entity.setAnnualincome(Double.parseDouble(col[12]));
			}
			entity.setDataStatus("1");//入库 默认1
			entity.setInsertDttm(date);
			entity.setDataSrc("2");//csv导入 默认2
			listInfo.add(entity);
		}
		submitEmpMapper.insertAll(listInfo);
	}

	@Override
	public void deleteByCertTypeAndCertNo(String certType, String certNo, String dataSrc) {
		Example ea = new Example(SubmitEmp.class);
		Criteria crt = ea.createCriteria();
		crt.andCondition(" data_src ="+dataSrc);
		crt.andCondition(" certtype in("+certType+")");
		crt.andCondition(" certno in("+certNo+")");
		submitEmpMapper.deleteByExample(ea);
	}

	
    
}
