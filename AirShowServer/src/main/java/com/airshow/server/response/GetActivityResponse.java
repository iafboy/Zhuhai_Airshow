package com.airshow.server.response;

import java.util.List;

import com.airshow.server.db.pojo.Activity;

public class GetActivityResponse {
	private int returnCode;
	private String returnMessage;
	private List<Activity> activity;
	
	public int getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMessage() {
		return returnMessage;
	}
	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}
	public List<Activity> getActivity() {
		return activity;
	}
	public void setActivity(List<Activity> activity) {
		this.activity = activity;
	}
}
