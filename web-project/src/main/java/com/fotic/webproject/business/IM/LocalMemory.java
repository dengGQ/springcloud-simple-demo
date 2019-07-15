package com.fotic.webproject.business.IM;

import java.util.concurrent.ConcurrentHashMap;


public class LocalMemory {
	
	public static ConcurrentHashMap<String, WebSocketController> onlineUsers 
		= new ConcurrentHashMap<>();

	public static ConcurrentHashMap<String, User> logingUser = new ConcurrentHashMap<>();
	
}
