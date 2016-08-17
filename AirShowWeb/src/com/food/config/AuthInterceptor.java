package com.food.config;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

public class AuthInterceptor implements Interceptor {

	@Override
	public void intercept(ActionInvocation ai) {
		if(!"/index".equals(ai.getControllerKey()) && !"/web".equals(ai.getControllerKey()) && !"/share".equals(ai.getControllerKey())){
			if(ai.getController().getSessionAttr("admin") == null){
				ai.getController().redirect("/index/index");
			}else{
				ai.invoke();
			}
		}else{
			ai.invoke();
		}
	}

}
