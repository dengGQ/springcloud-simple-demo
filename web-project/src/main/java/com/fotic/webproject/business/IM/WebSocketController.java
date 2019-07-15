package com.fotic.webproject.business.IM;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


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
		notifyFriendsOnline();
	}
	
	@OnClose
	public void onClose() {
		logger.info("---------------------连接已关闭");
		LocalMemory.onlineUsers.remove(session.getId());
		LocalMemory.logingUser.remove(username);
		notifyFriendsOnline();
	}
	
	@OnMessage
	public void onMessage(Session session, String msg) {
		try {
			MsgEntity msgEntity = JSON.parseObject(msg, MsgEntity.class);
			logger.info(String.format("---------------------收到%s发给%s的消息：%s", username, msgEntity.getTo(), msgEntity.getMsg()));
			sendMessage(msgEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(MsgEntity msgEntity){
		LocalMemory.onlineUsers.forEach((sessionId, value)->{
			if(!sessionId.equals(this.session.getId()) && ("full".equals(msgEntity.getTo()) 
					|| sessionId.equals(msgEntity.getTo()))) {
				MsgEntity entity = new MsgEntity(username, msgEntity.getMsg());
				value.session.getAsyncRemote().sendText(JSONObject.toJSONString(entity));
			}
		});
	}
	
	//好友上限通知
	public void notifyFriendsOnline() {
		LocalMemory.onlineUsers.forEach((sessionId, value)->{
			MsgEntity msgEntity = new MsgEntity("notice", LocalMemory.onlineUsers.size(), LocalMemory.logingUser.size());
			value.session.getAsyncRemote().sendText(JSONObject.toJSONString(msgEntity));
		});
	}

	public Session getSession() {
		return session;
	}

	public String getUsername() {
		return username;
	}
}
