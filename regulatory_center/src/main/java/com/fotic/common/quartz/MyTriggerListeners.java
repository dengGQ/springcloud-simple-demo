package com.fotic.common.quartz;

import java.util.concurrent.ConcurrentHashMap;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;
import org.springframework.stereotype.Component;

/*
* @Description: public class MyTriggerListeners{ }
* @author dgq 
* @date 2018年4月25日
*/
@Component("myTriggerListeners")
public class MyTriggerListeners implements TriggerListener{  
	  
	private static ConcurrentHashMap<String,Long> countHashMap=new ConcurrentHashMap<String,Long>();  
	@Override  
	public String getName() {  
	    return "MyTriggerListeners";  
	}  
	
	@Override  
	public void triggerFired(Trigger trigger, JobExecutionContext context) {  
	    // TODO Auto-generated method stub  
	      
	}  
	@Override  
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {  
	    // TODO Auto-generated method stub  
	    return false;  
	}  
	
	@Override  
	public void triggerMisfired(Trigger trigger) {  
	    // TODO Auto-generated method stub  
	      
	}  
	@Override  
	public void triggerComplete(Trigger trigger, JobExecutionContext context,  
	        CompletedExecutionInstruction triggerInstructionCode) {  
	    System.out.println("Trigger :"+trigger.getKey().getGroup()+"."+trigger.getKey().getName()+" completed with code:"+triggerInstructionCode);  
	    String key=trigger.getKey().getName();  
	    if(countHashMap.containsKey(key)){  
	        Long count=countHashMap.get(key)+1;  
	        countHashMap.put(key, count);  
	        System.out.println("任务执行次数=="+count);  
	    }else{  
	        countHashMap.put(key, (long) 1);  
	        System.out.println("任务执行次数=="+1);
	    }  
	      
	}
}
