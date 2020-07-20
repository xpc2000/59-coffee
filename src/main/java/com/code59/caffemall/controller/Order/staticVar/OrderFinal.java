package com.code59.caffemall.controller.Order.staticVar;

import com.code59.caffemall.bean.Cart;

import java.util.List;
//某个订单
public class OrderFinal {
    private String shopId;
    private String guestId;
    private List<Cart> cartsAll;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public List<Cart> getCartsAll() {
        return cartsAll;
    }

    public void setCartsAll(List<Cart> cartsAll) {
        this.cartsAll = cartsAll;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }
}