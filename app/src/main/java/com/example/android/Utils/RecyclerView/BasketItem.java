package com.example.android.Utils.RecyclerView;

import com.google.gson.annotations.SerializedName;

public class BasketItem {
    @SerializedName("categoryNo")   // 카테고리 PK
    private int categoryNo;
    @SerializedName("code")         // 바코드 번호
    private int code;
    @SerializedName("cost")         // 제품 원가
    private int cost;
    @SerializedName("imgUrl")       // 제품 이미지 url
    private String imgUrl;
    @SerializedName("location")     // 제품 진열 위치
    private String location;
    @SerializedName("name")         // 제품 이름
    private String name;
    @SerializedName("price")        // 판매금액
    private int price;
    @SerializedName("productNo")    // 제품 uid
    private int productNo;
    @SerializedName("stock")        // 재고
    private int stock;

    private int count;

    public int getCategoryNo() {
        return categoryNo;
    }

    public void setCategoryNo(int categoryNo) {
        this.categoryNo = categoryNo;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getProductNo() {
        return productNo;
    }

    public void setProductNo(int productNo) {
        this.productNo = productNo;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
