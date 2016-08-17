package com.food.exit;

import com.food.config.Util;
import com.jfinal.core.Controller;

public class ExitController extends Controller {
	public void index() {
		getSession().removeAttribute("admin");
		redirect(Util.getUrl("index","index"));
	}
}
