package com.airshow.server.request;

public class GetAllActivityPeopleImageRequest {
	private int page;
    private int pid;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

}
