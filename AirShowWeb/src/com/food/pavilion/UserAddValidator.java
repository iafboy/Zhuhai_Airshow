package com.food.pavilion;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class UserAddValidator extends Validator {
	protected void validate(Controller c) {
		validateRequiredString("phone", "nameMsg", "请输入用户名");
		validateRequiredString("password", "passMsg", "请输入密码");
	}

	protected void handleError(Controller c) {
		c.redirect("/user/add");
	}
}
