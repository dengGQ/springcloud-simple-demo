package com.fotic.management.customer.service;
import java.text.ParseException;
import java.util.List;
public interface ISubmitEmpService {
	
	public void insertEmpFromCsv(List<String[]> list)  throws ParseException;
	
	public void deleteByCertTypeAndCertNo(String certType,String certNo,String dataSrc);
}
