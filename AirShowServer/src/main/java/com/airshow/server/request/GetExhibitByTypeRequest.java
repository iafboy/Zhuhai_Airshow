package com.airshow.server.request;

public class GetExhibitByTypeRequest {
    private int pavilion_id;
    private int type;
    private int page;

    public int getPavilion_id() {
        return pavilion_id;
    }

    public void setPavilion_id(int pavilion_id) {
        this.pavilion_id = pavilion_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

}
