package com.fotic.management.creditinfo.jdbc;

public interface JdbcTmeplateDao {

	/**
	 * 无返回值的存储过程调用
	 * @param procedureName	存储过程名称
	 * @param params	参数(可选)
	 * @return
	 */
	public boolean procedureInvoke(String procedureName, String ... params);
	
	
	/**
	 * 调用存储过程, 出参(返回值)为字符串
	 * @param procedureName	存储过程名称
	 * @param params	参数(可选)
	 * @return
	 */
	public String procedureInvokeRetString(String procedureName, String ... params);
}
