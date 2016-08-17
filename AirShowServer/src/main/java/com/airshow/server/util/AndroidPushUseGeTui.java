package com.airshow.server.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.gexin.rp.sdk.base.IIGtPush;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

public class AndroidPushUseGeTui {
	private static final String APPID = "YGjPcUZsEc702wP6aVWYd5";
	private static final String APPKEY = "rTfDIP2UKJ9wYRM3UcTSe2";
	private static final String MASTERSECRET = "Qe6bxHQaln64gIwPRXhdm6";
	private static final String API = "http://sdk.open.api.igexin.com/apiex.htm";
    
    private static Logger logger = Logger.getLogger(AndroidPushUseGeTui.class);
    
    public static boolean sendMessageWithTag(final String tag,final String messageStr) {

    	IIGtPush push = new IGtPush(API, APPKEY, MASTERSECRET);
		try {
			AppMessage message = new AppMessage();
			
			TransmissionTemplate template = new TransmissionTemplate();
			template.setAppId(APPID); 
			template.setAppkey(APPKEY);
			template.setTransmissionContent(messageStr);
			template.setTransmissionType(2);
			message.setData(template);
			
			List<String> tagList = new ArrayList<String>();
			tagList.add(tag);
			message.setTagList(tagList);
			List<String> appIdList = new ArrayList<String>();
			appIdList.add(APPID);
			message.setAppIdList(appIdList);
			
			IPushResult ret = push.pushMessageToApp(message);
			logger.warn("个推结果:"+ret.getResponse().toString());
//			System.out.println("个推结果:"+ret.getResponse().toString());
			if(ret.getResponse().get("result").equals("ok")){
				return true;
			}
			return false;
		} catch (Exception e) {
			logger.warn("个推推送Error:");
			logger.warn(e.getStackTrace().toString());
			return false;
		}
    }
    
    public static boolean sendMessageWithTagList(final List<String> tagList,final String messageStr) {

    	IIGtPush push = new IGtPush(API, APPKEY, MASTERSECRET);
		try {
			AppMessage message = new AppMessage();
			
			NotificationTemplate template = new NotificationTemplate();
			template.setAppId(APPID); 
			template.setAppkey(APPKEY);
			template.setTitle("notice");
			template.setText(messageStr);
			template.setTransmissionType(2);
			message.setData(template);
			message.setTagList(tagList);

			IPushResult ret = push.pushMessageToApp(message);
			logger.warn("个推结果:"+ret.getResponse().toString());
			return true;
		} catch (Exception e) {
			logger.warn("个推推送Error:");
			logger.warn(e.getStackTrace().toString());
			return false;
		}
    }

    
    public static void main(String[] args){
    	TestGuiTui testGuiTui1 = new TestGuiTui(1, 6, "1");
    	TestGuiTui testGuiTui2 = new TestGuiTui(7, 13, "2");
    	TestGuiTui testGuiTui3 = new TestGuiTui(14, 20, "3");
    	Thread teThread1 = new Thread(testGuiTui1);
    	Thread teThread2 = new Thread(testGuiTui2);
    	Thread teThread3 = new Thread(testGuiTui3);
    	teThread1.start();
    	teThread2.start();
    	teThread3.start();
    }
}

class TestGuiTui implements Runnable{
	private int start;
	private int end;
	private String name;
	
	public TestGuiTui(int start, int end, String name){
		this.start = start;
		this.end = end;
		this.name = name;
	}

	@Override
	public void run() {
		for(int i=start;i<=end;i++){
			System.out.println(name+":"+i+":"+AndroidPushUseGeTui.sendMessageWithTag("18600468901", "This is "+i));
		}
	}
	
}
