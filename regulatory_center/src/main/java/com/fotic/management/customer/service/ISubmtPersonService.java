package com.fotic.management.customer.service;

import java.text.ParseException;
import java.util.List;

public interface ISubmtPersonService {
	public void insertPersonFromCsv(List<String[]> list)  throws ParseException;
	
	public void deleteByCertTypeAndCertNo(String certType,String certNo,String dataSrc);
}
