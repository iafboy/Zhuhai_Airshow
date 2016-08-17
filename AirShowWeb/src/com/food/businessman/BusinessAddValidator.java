package com.food.businessman;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class BusinessAddValidator extends Validator {
	protected void validate(Controller c) {
//		validateRequiredString("name", "passMsg", "请输入密码");
//		validateRequiredString("company", "nameMsg", "请输入用户名");
//		validateRequiredString("phone", "passMsg", "请输入密码");
//		validateRequiredString("email", "nameMsg", "请输入用户名");
//		validateRequiredString("introduction", "passMsg", "请输入密码");
	}

	protected void handleError(Controller c) {
		c.redirect("/businessman/add");
	}
}
