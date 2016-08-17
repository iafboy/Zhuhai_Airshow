package com.food.config;

public class Util {
	public static String root = "/";
	public static final int pageLines = 10;
	
	public static String getUrl(String controller, String method){
		return root+controller+"/"+method;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
