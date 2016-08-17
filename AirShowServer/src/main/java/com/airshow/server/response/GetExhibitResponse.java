package com.airshow.server.response;

import java.util.List;

import com.airshow.server.db.pojo.Exhibit;

public class GetExhibitResponse {
    private int number;
	private int returnCode;
	private String returnMessage;
	private List<Exhibit> exhibit;
	
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
	public List<Exhibit> getExhibit() {
		return exhibit;
	}
	public void setExhibit(List<Exhibit> exhibit) {
		this.exhibit = exhibit;
	}
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
}
