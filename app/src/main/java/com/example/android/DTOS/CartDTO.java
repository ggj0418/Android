package com.example.android.DTOS;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartDTO {
    @SerializedName("cartNo")
    private int cartNo;
    @SerializedName("totalPrice")
    private int totalPrice;
    @SerializedName("cartItems")
    private List<CartItemDTO> cartItems;

    public int getCartNo() {
        return cartNo;
    }

    public void setCartNo(int cartNo) {
        this.cartNo = cartNo;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartItemDTO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDTO> cartItems) {
        this.cartItems = cartItems;
    }
}
