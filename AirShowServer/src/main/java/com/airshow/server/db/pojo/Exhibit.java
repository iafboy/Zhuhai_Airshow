package com.airshow.server.db.pojo;

public class Exhibit {
	private int id;
    private int type;
	private String name;
	private String introduction;
	private String path;
	private int business_id;
	private int pavilion_id;
    private int priority;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getBusiness_id() {
		return business_id;
	}
	public void setBusiness_id(int business_id) {
		this.business_id = business_id;
	}
	public int getPavilion_id() {
		return pavilion_id;
	}
	public void setPavilion_id(int pavilion_id) {
		this.pavilion_id = pavilion_id;
	}
    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
}
