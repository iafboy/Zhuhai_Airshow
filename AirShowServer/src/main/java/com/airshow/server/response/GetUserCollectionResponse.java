package com.airshow.server.response;

import java.util.List;

import com.airshow.server.db.pojo.UserCollection;

public class GetUserCollectionResponse {
	private int returnCode;
	private String returnMessage;
	private List<UserCollection> userCollections;
	
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
	public List<UserCollection> getUserCollections() {
		return userCollections;
	}
	public void setUserCollections(List<UserCollection> userCollections) {
		this.userCollections = userCollections;
	}
	
}
