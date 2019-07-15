package com.fotic.webproject.util;

import java.util.Date;

import com.fotic.webproject.business.exception.BusinessException;
import com.fotic.webproject.exception.ExceptionCodeEnum;

public class DateUtil {

	/**
	 * @Description: 比较两个时间段是否相等
	 * @param @param startDate1
	 * @param @param endDate1
	 * @param @param startDate2
	 * @param @param endDate2
	 * @param @return
	 * @param @throws Exception    参数
	 * @return boolean    返回类型
	 * @throws
	 */
	public static boolean equals(Date startDate1, Date endDate1, Date startDate2, Date endDate2) throws Exception {
		boolean flag = false;// 默认无交集
		long s1 = startDate1.getTime();
		long e1 = endDate1.getTime();
		long s2 = startDate2.getTime();
		long e2 = endDate2.getTime();
	
		if((s1>e1)||(s2>e2))
			throw new BusinessException(ExceptionCodeEnum.EXCEPTION_HTTP_STATUS_400.getErrorCode(), ExceptionCodeEnum.EXCEPTION_HTTP_STATUS_400.getErrorMsg());
		if (s1==s2 && e1 == e2) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * @Description: 比较两个时间段是否有交集
	 * @param @param startDate1
	 * @param @param endDate1
	 * @param @param startDate2
	 * @param @param endDate2
	 * @param @return
	 * @param @throws Exception    参数
	 * @return boolean    返回类型
	 * @throws
	 */
	public static boolean isDateCross(Date startDate1, Date endDate1, Date startDate2, Date endDate2) throws Exception {
		boolean flag = false;// 默认无交集
		long s1 = startDate1.getTime();
		long e1 = endDate1.getTime();
		long s2 = startDate2.getTime();
		long e2 = endDate2.getTime();
	
		if((s1>e1)||(s2>e2))
			throw new BusinessException(ExceptionCodeEnum.EXCEPTION_HTTP_STATUS_400.getErrorCode(), ExceptionCodeEnum.EXCEPTION_HTTP_STATUS_400.getErrorMsg());
		if (((s1 < s2) && (s2 < e1)) || ((s1 < e2) && (e2 < e1))
				|| ((s2 < s1) && (s1 < e2)) || ((s2 < e1) && (e1 < e2))) {
			flag = true;
		}
		return flag;
	}
	
	
}
