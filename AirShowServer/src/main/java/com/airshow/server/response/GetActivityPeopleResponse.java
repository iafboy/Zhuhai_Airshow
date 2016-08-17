package com.airshow.server.response;

import com.airshow.server.db.pojo.Activity_People;
import java.util.List;

public class GetActivityPeopleResponse {

    private int number;
	private int returnCode;
	private String returnMessage;
	private List<Activity_People> activity_peoples;
	
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
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public List<Activity_People> getActivity_peoples() {
        return activity_peoples;
    }
    public void setActivity_peoples(List<Activity_People> activity_peoples) {
        this.activity_peoples = activity_peoples;
    }
}
