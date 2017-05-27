package com.winsant.seller.model;

/**
 * Created by Developer on 5/12/2017.
 */

public class BrandModel {

    private String brand_id;
    private String brand_name;

    public BrandModel(String brand_id, String brand_name) {
        this.brand_id = brand_id;
        this.brand_name = brand_name;
    }

    public String getBrand_id() {
        return this.brand_id;
    }

    public String getBrand_name() {
        return this.brand_name;
    }
}
