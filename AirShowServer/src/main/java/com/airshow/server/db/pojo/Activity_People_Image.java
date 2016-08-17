package com.airshow.server.db.pojo;

public class Activity_People_Image {
	private int id;
    private int pid;
	private String image_path;
    private String image_name;
    private String image_introduction;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getPid() {
        return pid;
    }
    public void setPid(int pid) {
        this.pid = pid;
    }
    public String getImage_path() {
        return image_path;
    }
    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
    public String getImage_name() {
        return image_name;
    }
    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }
    public String getImage_introduction() {
        return image_introduction;
    }
    public void setImage_introduction(String image_introduction) {
        this.image_introduction = image_introduction;
    }
}
