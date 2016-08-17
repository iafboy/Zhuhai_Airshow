package com.food.news;

import com.jfinal.plugin.activerecord.Model;

public class News extends Model<News> {
	private static final long serialVersionUID = -1352873180757353816L;

	public static final News dao = new News();
}