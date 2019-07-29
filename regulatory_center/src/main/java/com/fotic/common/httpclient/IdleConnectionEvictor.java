package com.fotic.common.httpclient;

import org.apache.http.conn.HttpClientConnectionManager;

/*
* @Description: public class IdleConnectionEvictor{ }
* @author dgq 
* @date 2018年4月20日
*/
public class IdleConnectionEvictor extends Thread{
	
	private final HttpClientConnectionManager connMgr;
	
	private volatile boolean shutdown;
	
	private Integer waitTime;
	
	public IdleConnectionEvictor(HttpClientConnectionManager connMgr, Integer waitTime) {
		this.connMgr = connMgr;
		this.setWaitTime(waitTime);
		this.start();
	}
	
	@Override
	public void run() {
		try {
			while(!shutdown){
				synchronized (this) {
					wait(5000);
					connMgr.closeExpiredConnections();
				}
			}
		} catch (Exception e) {
			shutdown = true;
			synchronized (this) {
				notifyAll();
			}
		}
	}
	
	 /** 
     * 销毁释放资源 
     */  
    public void shutdown() {  
        shutdown = true;  
        synchronized (this) {  
            notifyAll();  
        }  
    }

	public Integer getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(Integer waitTime) {
		this.waitTime = waitTime;
	} 
}
