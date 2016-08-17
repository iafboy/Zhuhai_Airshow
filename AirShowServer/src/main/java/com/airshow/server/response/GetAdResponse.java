package com.airshow.server.response;

import java.util.List;

import com.airshow.server.db.pojo.Ad;

public class GetAdResponse {
	private int returnCode;
	private String returnMessage;
	private List<Ad> ad;
	
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
	public List<Ad> getAd() {
		return ad;
	}
	public void setAd(List<Ad> ad) {
		this.ad = ad;
	}
	
}
