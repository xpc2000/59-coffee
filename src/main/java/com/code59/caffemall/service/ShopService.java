package com.code59.caffemall.service;

import com.code59.caffemall.bean.Shop;

import java.util.List;

public interface ShopService {
    public int add(Shop shop);
    public int delete(String id);
    public int update(Shop shop);
    public Shop get(String id);
    public List<Shop> list();
}
