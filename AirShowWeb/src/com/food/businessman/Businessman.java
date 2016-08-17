package com.food.businessman;

import com.jfinal.plugin.activerecord.Model;

public class Businessman extends Model<Businessman> {
	private static final long serialVersionUID = -1352873180757353816L;

	public static final Businessman dao = new Businessman();
}