package com.fotic.webproject.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.UUID;
import java.util.Vector;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class FtpJSch {
	
	private static ChannelSftp sftp = null;
	private static Session sshSession = null;
	
	//账号
	private static String user = "root";
	//主机ip
	private static String host =  "10.7.101.52";
	//密码
	private static String password = "root52";
	//端口
	private static int port = 22;
	//上传地址
	private static String directory = "/NAS/52-9080/rt/pub/balance/20190711";
	//下载目录
	private static String saveFile = "E:\\home\\t";
	
	public static FtpJSch getConnect(){
		FtpJSch ftp = new FtpJSch();
		try {
			JSch jsch = new JSch();
 
			//获取sshSession  账号-ip-端口
			sshSession =jsch.getSession(user, host,port);
			//添加密码
			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			//严格主机密钥检查
			sshConfig.put("StrictHostKeyChecking", "no");
			
			sshSession.setConfig(sshConfig);
			//开启sshSession链接
			sshSession.connect();
			//获取sftp通道
			Channel channel = sshSession.openChannel("sftp");
			//开启
			channel.connect();
			sftp = (ChannelSftp) channel;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ftp;
	}
	
	/**
	 * 
	 * @param uploadFile 上传文件的路径
	 * @return 服务器上文件名
	 */
	public String upload(String uploadFile) {
		File file = null;
		String fileName = null;
		try {
			sftp.cd(directory);
			file = new File(uploadFile);
			//获取随机文件名
			fileName  = UUID.randomUUID().toString() + file.getName().substring(file.getName().length()-5);
			//文件名是 随机数加文件名的后5位
			sftp.put(new FileInputStream(file), fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file == null ? null : fileName;
	}
	
	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件名
	 * @param saveFile
	 *            存在本地的路径
	 * @param sftp
	 */
	public void download(String downloadFileName) {
		try {
			sftp.cd(directory);
			
			File file = new File(saveFile);
			
			sftp.get(downloadFileName, new FileOutputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除文件
	 * 
	 * @param deleteFile
	 *            要删除的文件名字
	 * @param sftp
	 */
	public void delete(String deleteFile) {
		try {
			sftp.cd(directory);
			sftp.rm(deleteFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 列出目录下的文件
	 * 
	 * @param directory
	 *            要列出的目录
	 * @param sftp
	 * @return
	 * @throws SftpException
	 */
	public Vector listFiles(String directory)
			throws SftpException {
		Vector vector = sftp.ls(directory);
		return vector;
	}
	private static void closeChannel(Channel channel) {
        if (channel != null) {
            if (channel.isConnected()) {
                channel.disconnect();
            }
        }
    }
 
    private static void closeSession(Session session) {
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
        }
    }
    
    public static void recover(String str) 
            throws UnsupportedEncodingException{
        String[] charsets = new String[]{"windows-1252","GB18030","Big5","UTF-8", "GBK", "iso-8859-1"};
        for(int i=0;i<charsets.length;i++){
            for(int j=0;j<charsets.length;j++){
                if(i!=j){
                    String s = new String(str.getBytes(charsets[i]),charsets[j]);
                    System.out.println("---- 原来编码(A)假设是: "+charsets[j]+", 被错误解读为了(B): "+charsets[i]);
                    System.out.println(s);
                    System.out.println();    
                }
            }
        }
    } 
	 @Autowired
@SuppressWarnings("unchecked")
	 public static void main(String[] args) {
    	try {
    		
			/*
			 * String a = "广告"; System.out.println(a); String str = new
			 * String(a.getBytes("utf-8"), "gbk"); System.out.println(str);
			 */
    		
    		
    		
    		FtpJSch sch = getConnect();
    		
    		@SuppressWarnings("rawtypes")
    		Vector vector = sch.listFiles("/NAS/52-9080/rt/pub/balance/20190711");
    		vector.forEach(item->{
    			LsEntry entry = (LsEntry) item;
    			try {
    				System.out.println(entry.getFilename());
    				String str = new String(entry.getFilename().getBytes("utf-8"), "gbk");
//					System.out.println(new String(str));
//					sch.download(entry.getFilename());
    				
    				recover(str);
    			} catch (Exception e) {
					e.printStackTrace();
				}
    		});
    		
    	}catch (Exception e){
    		e.printStackTrace();
    	}finally {
    		closeChannel(sftp);
    		closeSession(sshSession);
    	}
    
    }
}
