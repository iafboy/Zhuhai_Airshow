package com.airshow.server.response;

import java.util.List;

import com.airshow.server.db.pojo.Businessman;

public class GetBusinessmanResponse {
    private int number;
	private int returnCode;
	private String returnMessage;
	private List<Businessman> businessman;
	
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
	public List<Businessman> getBusinessman() {
		return businessman;
	}
	public void setBusinessman(List<Businessman> businessman) {
		this.businessman = businessman;
	}
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
}
