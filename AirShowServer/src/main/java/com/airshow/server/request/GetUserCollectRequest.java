package com.airshow.server.request;

public class GetUserCollectRequest {
	private int type;
	private String uid;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
}
