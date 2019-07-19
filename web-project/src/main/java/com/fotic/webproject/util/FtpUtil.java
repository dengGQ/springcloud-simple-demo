package com.fotic.webproject.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUtil {
	public static String url = "10.7.101.52";
	 
    public static int port = 22;
 
    public static String user = "root";
 
    public static String pwd = "root52";
 
    public static void connectServer(FTPClient ftpClient){
 
        try{
            int reply;
            ftpClient.connect(url, port);// 连接FTP服务器
            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            ftpClient.login(user, pwd);// 登录
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            reply = ftpClient.getReplyCode();
 
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
 
    /**
     * Description: 向FTP服务器上传文件
     *
     * @param url
     *            FTP服务器hostname
     * @param port
     *            FTP服务器端口
     * @param username
     *            FTP登录账号
     * @param password
     *            FTP登录密码
     * @param path
     *            FTP服务器保存目录
     * @param filename
     *            上传到FTP服务器上的文件名
     * @param input
     *            输入流
     * @return 成功返回true，否则返回false
     * @throws Exception
     */
    public static boolean uploadFile(String path, String filename, InputStream input) throws Exception {
 
        boolean success = false;
        FTPClient ftpClient= new FTPClient();
        try {
            connectServer(ftpClient);
 
            ftpClient.changeWorkingDirectory(path);
            ftpClient.storeFile(filename, input);
            input.close();
            ftpClient.logout();
            success = true;
 
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("文件上传过程中发生异常!"+e.getMessage());
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return success;
    }
 
    /**
     * Description: 从FTP服务器下载文件
     *
     * @param url
     *            FTP服务器hostname
     * @param port
     *            FTP服务器端口
     * @param username
     *            FTP登录账号
     * @param password
     *            FTP登录密码
     * @param remotePath
     *            FTP服务器上的相对路径
     * @param fileName
     *            要下载的文件名
     * @param localPath
     *            下载后保存到本地的路径
     * @return
     * @throws Exception
     */
    public static boolean downFile(String remotePath, String ftpName,
            String fileName, HttpServletResponse response) throws Exception {
 
        boolean success = false;
        OutputStream out = null;
        FTPClient ftpClient= new FTPClient();
        try {
            connectServer(ftpClient);
            ftpClient.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
 
            FTPFile[] fs = ftpClient.listFiles(remotePath);
            for (int i = 0; i < fs.length; i++) {
                if (fs[i].getName().equals(ftpName)) {
 
                    // ByteArrayOutputStream os = new ByteArrayOutputStream();
                    File f = new File(ftpName);
                    FileOutputStream fos = new FileOutputStream(f);
                    boolean bf = ftpClient.retrieveFile(ftpName, fos);
                    if (bf) {
                        FileInputStream fis = new FileInputStream(ftpName);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        int   ch;
                        while((ch=fis.read())!=-1)   {
                           bos.write(ch);
                        }
 
                        response.reset();
                        response.setContentType("application/octet-stream;charset=UTF-8");
                        response.setHeader("Content-Disposition", "attachment;"
                                + " filename="+ new String(fileName.getBytes(), "ISO-8859-1"));
                        byte[] bt=bos.toByteArray();
                        out = response.getOutputStream();
                        out.write(bt, 0, bt.length);
                         
                        fis.close();
                    }
                    fos.close();
                     
                    out.flush();
                    out.close();
                }
            }
 
            ftpClient.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("文件下载过程中发生异常!"+e.getMessage());
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }
 
    
 
    public static void main(String[] args) {
    	try {
    		
    		FTPClient ftpClient= new FTPClient();
    		//ftpClient.connect("10.7.101.52", 22);
            //ftpClient.login("root", "root52");
    		connectServer(ftpClient);
    		FTPFile[] fs = ftpClient.listFiles("/NAS/52-9080/rt/pub/balance/20190711");
    		
    		for (int i = 0; i < fs.length; i++) {
    			System.out.print(fs[i].getName());
    		}
    	}catch (Exception e){
    		e.printStackTrace();
    	}
    
    }
 
}
