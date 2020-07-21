package com.code59.caffemall.controller.Order.tempVar;

import com.code59.caffemall.bean.Cart;


//订单中的某一项餐品
public class OrderSingle {
    private String shopId;
    private String guestId;
    private Cart cart;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }
}
