package com.airshow.server.response;

import com.airshow.server.db.pojo.Activity_People_Image;

import java.util.List;

public class GetActivityPeopleImageResponse {

    private int number;
	private int returnCode;
	private String returnMessage;
	private List<Activity_People_Image> activity_people_images;
	
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
    public List<Activity_People_Image> getActivity_people_images() {
        return activity_people_images;
    }
    public void setActivity_people_images(List<Activity_People_Image> activity_people_images) {
        this.activity_people_images = activity_people_images;
    }
}
