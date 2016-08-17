package com.food.user;

import com.jfinal.plugin.activerecord.Model;

public class User extends Model<User> {
	private static final long serialVersionUID = -1352873180757353816L;

	public static final User dao = new User();
}