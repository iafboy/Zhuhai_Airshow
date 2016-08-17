package com.airshow.server.db.pojo;

import java.sql.Timestamp;

public class Pavilion {
	private int id;
	private String name;
	private String introduction;
	private Timestamp time;
    private String path;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
}
