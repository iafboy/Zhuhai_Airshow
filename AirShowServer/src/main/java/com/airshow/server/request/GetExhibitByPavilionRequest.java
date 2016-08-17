package com.airshow.server.request;

public class GetExhibitByPavilionRequest {
	private int pavilion_id;
    private int page;

	public int getPavilion_id() {
		return pavilion_id;
	}

	public void setPavilion_id(int pavilion_id) {
		this.pavilion_id = pavilion_id;
	}

    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
}
