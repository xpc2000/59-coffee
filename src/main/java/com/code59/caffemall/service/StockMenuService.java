package com.code59.caffemall.service;

import com.code59.caffemall.bean.ShopStock;
import com.code59.caffemall.bean.Stock;
import com.code59.caffemall.bean.StockOnSell;

import java.util.List;

public interface StockMenuService {
    public int add(StockOnSell stockOnSell);
    public int delete(int id);
    public int update(StockOnSell stockOnSell);
    public StockOnSell get(int id);
    public List<StockOnSell> list();
    public List<StockOnSell> listByType(String type);
    public List<StockOnSell> listByName(String name);
    public int count();
    public int addStockShopInfo(ShopStock shopStock);
    public int deleteStockShopInfo(int id);
    public int updateStockShopInfo(ShopStock shopStock);
    public ShopStock getStockShopInfo(int id);
    public List<Stock> drawlist(String type,String name);
    public List<Stock> showStock(String foodName);
    public List<Stock> showOnSellStock(String foodName);
    public List<Stock> showUnderSellStock(String foodName);
    public boolean undercarrage(String foodid);
}
