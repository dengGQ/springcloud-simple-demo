package com.fotic.common.util;

import java.io.IOException;
import java.io.InputStream;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/*
* @Description: 执行系统命令
* @author 邓国泉 
* @date 2017年9月28日
*/
public class ExecCmdUtil {
	private static final String DEFAULTCHART = "UTF-8";
	/**
	 * 创建连接并认证
	 * @param host
	 * @param port
	 * @param userName
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static Connection getConnection(String host, int port, String userName, String password) throws Exception{
		Connection conn = new Connection(host, port);
		conn.connect();
		boolean auth = conn.authenticateWithPassword(userName, password);
		if(!auth){
			throw new Exception("认证失败");
		}
		return conn;
	}
	
	/**
	 * 调用远程系统执行命令
	 * @param cmd  待执行命令
	 * @param conn 与远程系统建立的链接
	 * @return
	 */
	public static String exec(String cmd, Connection conn){
		Session session = null;
		System.out.println(cmd);
		try {
			session = conn.openSession();
			session.execCommand(cmd);
			String result = DsJobUtil.processStdout(session.getStdout(), DEFAULTCHART);
			System.out.println("初次："+result);
			if(StringUtils.isBlank(result)){
                result=DsJobUtil.processStdout(new StreamGobbler(session.getStderr()),DEFAULTCHART); 
                System.out.println("再次："+result);
                
            }
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				session.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 调用本地系统执行命令
	 * @param cmd： 可以是多个命令的string数组 或 单个命令
	 * @return
	 */
	public static String exec(Object cmd){
		InputStream is = null;
		Process process = null;
		try {
			if(cmd instanceof String[]){
				process = Runtime.getRuntime().exec((String[])cmd);
			}else{
				process = Runtime.getRuntime().exec((String)cmd);
			}
			is = process.getInputStream();
			String result = DsJobUtil.processStdout(is, DEFAULTCHART);
			if(StringUtils.isBlank(result)){
                result=DsJobUtil.processStdout(new StreamGobbler(process.getErrorStream()),DEFAULTCHART);  
            }
			
			int exitValue = process.waitFor();
			System.out.println(exitValue);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
