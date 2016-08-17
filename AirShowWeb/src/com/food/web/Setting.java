package com.food.web;

import com.jfinal.plugin.activerecord.Model;

public class Setting extends Model<Setting> {
	private static final long serialVersionUID = -1352873180757353816L;

	public static final Setting dao = new Setting();
}