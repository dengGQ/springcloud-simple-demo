package com.dgq.crs.service;

import java.util.Map;

/*
* @Description: public class AreaNumberService{ }
* @author dgq 
* @date 2019年5月16日
*/
public interface AreaNumberService {
	
	Map<String, Object> queryAreaNumberForPage(String dataYear, int page, int limit) throws Exception;
}
	