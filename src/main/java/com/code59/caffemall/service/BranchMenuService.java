package com.code59.caffemall.service;

import com.code59.caffemall.bean.StockOnSell;

import java.util.List;

public interface BranchMenuService {
    public List<StockOnSell> getFoodByTypeNameShopid(String type, String name, String shopid);
}
