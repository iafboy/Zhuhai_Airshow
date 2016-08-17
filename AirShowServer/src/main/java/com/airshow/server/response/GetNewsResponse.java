package com.airshow.server.response;

import java.util.List;

import com.airshow.server.db.pojo.News;

public class GetNewsResponse {

    private int number;
	private int returnCode;
	private String returnMessage;
	private List<News> news;
	
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
	public List<News> getNews() {
		return news;
	}
	public void setNews(List<News> news) {
		this.news = news;
	}
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
	
}
