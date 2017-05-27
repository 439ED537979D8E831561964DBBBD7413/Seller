package com.winsant.seller.model;

/**
 * Created by Developer on 5/12/2017.
 */

public class CategoryModel {

    private String category_id;
    private String category_name;
    private String request_status;
    private String friendly_url;
    private String isChecked;

    public CategoryModel(String category_id, String category_name, String request_status) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.request_status = request_status;
    }

    public CategoryModel(String category_id, String category_name, String friendly_url, String isChecked) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.friendly_url = friendly_url;
        this.isChecked = isChecked;
    }

    public String getCategory_id() {
        return this.category_id;
    }

    public String getCategory_name() {
        return this.category_name;
    }

    public String getRequest_status() {
        return this.request_status;
    }

    public String getFriendly_url() {
        return this.friendly_url;
    }

    public String getIsChecked() {
        return this.isChecked;
    }
}
