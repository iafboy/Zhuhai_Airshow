package com.food.index;

import com.food.config.Util;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

public class IndexController extends Controller {
	public void index() {
		render("index.html");
	}
	
	@Before(LoginValidator.class)
	public void login(){
		String username = getPara("username");
		String password = getPara("password");
		if("admin".equals(username) && "admin".equals(password)){
			setSessionAttr("admin", "admin");
			redirect(Util.getUrl("user","index"));
		}else{
			redirect(Util.getUrl("index","index"));
		}
	}
}
