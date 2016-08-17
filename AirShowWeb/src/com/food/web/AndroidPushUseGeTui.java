package com.food.web;

import com.gexin.rp.sdk.base.IIGtPush;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

import java.util.ArrayList;
import java.util.List;

public class AndroidPushUseGeTui {
	private static final String APPID = "YGjPcUZsEc702wP6aVWYd5";
	private static final String APPKEY = "rTfDIP2UKJ9wYRM3UcTSe2";
	private static final String MASTERSECRET = "Qe6bxHQaln64gIwPRXhdm6";
	private static final String API = "http://sdk.open.api.igexin.com/apiex.htm";

    
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
			System.out.println("个推结果:"+ret.getResponse().toString());
//			System.out.println("个推结果:"+ret.getResponse().toString());
			if(ret.getResponse().get("result").equals("ok")){
				return true;
			}
			return false;
		} catch (Exception e) {
            System.out.println("个推推送Error:");
            System.out.println(e.getStackTrace().toString());
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
            System.out.println("个推结果:"+ret.getResponse().toString());
			return true;
		} catch (Exception e) {
            System.out.println("个推推送Error:");
            System.out.println(e.getStackTrace().toString());
			return false;
		}
    }

    
    public static void main(String[] args){
        AndroidPushUseGeTui.sendMessageWithTag("all","测试通知");
    }
}