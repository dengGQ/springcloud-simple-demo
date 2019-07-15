package com.fotic.webproject.business.IM;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fotic.webproject.business.IM.LocalMemory;


@ServerEndpoint(value="/ws/{username}")
@Component
public class WebSocketController {
	
	private static Logger logger = LoggerFactory.getLogger(WebSocketController.class);
	
	private Session session;
	
	private String username;
	
	
	
	@OnOpen
	public void onOpen(@PathParam("username")String username, Session session) {
		logger.info("---------------------连接已建立");
		logger.info("---------------------登录人数: "+LocalMemory.logingUser.size()+";在线人数: "+LocalMemory.onlineUsers.size());
		this.session = session;
		this.username = username;
		LocalMemory.onlineUsers.put(session.getId(), this);
	}
	
	@OnClose
	public void onClose() {
		logger.info("---------------------连接已关闭");
		LocalMemory.onlineUsers.remove(session.getId());
		LocalMemory.logingUser.remove(username);
	}
	
	@OnMessage
	public void onMessage(Session session, String msg) {
		try {
			logger.info("---------------------收到消息："+msg);
			sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String msg){
		LocalMemory.onlineUsers.forEach((sessionId, value)->{
			if(!sessionId.equals(this.session.getId())) {
				try {
					value.session.getBasicRemote().sendText("<span style=\"color: red\">"+username+": </span>"+msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

	}
}
