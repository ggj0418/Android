package com.example.android.DTOS;

import com.google.gson.annotations.SerializedName;

public class CartItemDTO {
    @SerializedName("productNo")
    private int productNo;
    @SerializedName("categoryName")
    private String categoryName;
    @SerializedName("productName")
    private String productName;
    @SerializedName("count")
    private int count;
    @SerializedName("productCode")
    private int productCode;
    @SerializedName("productPrice")
    private int productPrice;

    public int getProductNo() {
        return productNo;
    }

    public void setProductNo(int productNo) {
        this.productNo = productNo;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }
}
