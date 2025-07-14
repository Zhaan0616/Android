package com.example.gouwu.entity;

import java.io.Serializable;

public class ProductInfo implements Serializable {
    private int product_id;
    private int product_img;
    private String product_title;
    private String product_details;
    private int product_price;

    public ProductInfo(int product_id, int product_img, String product_title, String product_details, int product_price) {
        this.product_id = product_id;
        this.product_img = product_img;
        this.product_title = product_title;
        this.product_details = product_details;
        this.product_price = product_price;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getProduct_img() {
        return product_img;
    }

    public void setProduct_img(int product_img) {
        this.product_img = product_img;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public String getProduct_details() {
        return product_details;
    }

    public void setProduct_details(String product_details) {
        this.product_details = product_details;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }
}
