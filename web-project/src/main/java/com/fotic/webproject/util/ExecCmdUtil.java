package com.fotic.webproject.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
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
		try {
			session = conn.openSession();
			session.execCommand(cmd);
			String result = processStdout(session.getStdout(), DEFAULTCHART);
			
			if(StringUtil.isEmpty(result)){
                result=processStdout(new StreamGobbler(session.getStderr()),DEFAULTCHART);  
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
			String result = processStdout(is, DEFAULTCHART);
			if(StringUtil.isEmpty(result)){
                result=processStdout(new StreamGobbler(process.getErrorStream()),DEFAULTCHART);  
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
	
	public static String processStdout(InputStream in, String charset) throws IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String stemp;
		while ((stemp = br.readLine()) != null ) {
			sb.append(stemp);
		}
		return sb.toString();
	}
	
	public static void main(String[] args) throws Exception {
		Connection conn = getConnection("10.7.101.52", 22, "root", "root52");
		
		SCPClient client = conn.createSCPClient();
		
	}
}
