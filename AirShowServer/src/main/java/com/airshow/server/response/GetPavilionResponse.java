package com.airshow.server.response;

import java.util.List;

import com.airshow.server.db.pojo.Pavilion;

public class GetPavilionResponse {
	private int returnCode;
	private String returnMessage;
	private List<Pavilion> pavilion;
	
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
	public List<Pavilion> getPavilion() {
		return pavilion;
	}
	public void setPavilion(List<Pavilion> pavilion) {
		this.pavilion = pavilion;
	}
	
}
