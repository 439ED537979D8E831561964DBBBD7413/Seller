package com.winsant.seller.model;

/**
 * Created by Developer on 5/12/2017.
 */

public class BrandStatusModel {

    private String brand_request_id;
    private String brand_name;
    private String category_name;
    private String status;
    private String request_datetime;
    private String admin_datetime;
    private String comment;

    public BrandStatusModel(String brand_request_id, String brand_name, String category_name, String status, String request_datetime, String admin_datetime, String comment) {
        this.brand_request_id = brand_request_id;
        this.brand_name = brand_name;
        this.category_name = category_name;
        this.status = status;
        this.request_datetime = request_datetime;
        this.admin_datetime = admin_datetime;
        this.comment = comment;
    }

    public String getBrand_request_id() {
        return this.brand_request_id;
    }

    public String getBrand_name() {
        return this.brand_name;
    }

    public String getCategory_name() {
        return this.category_name;
    }

    public String getStatus() {
        return this.status;
    }

    public String getRequest_datetime() {
        return this.request_datetime;
    }

    public String getAdmin_datetime() {
        return this.admin_datetime;
    }

    public String getComment() {
        return this.comment;
    }
}
