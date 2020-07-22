package com.code59.caffemall.service;

import com.code59.caffemall.bean.ShopStock;
import com.code59.caffemall.bean.Stock;
import com.code59.caffemall.bean.StockOnSell;
import com.code59.caffemall.controller.Order.tempVar.IdAndId;
import com.code59.caffemall.controller.Order.tempVar.NameAndId;

import java.util.List;

public interface StockMenuService {
    public int add(StockOnSell stockOnSell);
    public int add(ShopStock shopStock);
    public int delete(int id);
    public int update(StockOnSell stockOnSell);
    public int update(ShopStock shopStock);
    public StockOnSell get(int id);
    public List<StockOnSell> list();
    public List<StockOnSell> list(String idshop);
    public List<StockOnSell> listByType(String type);
    public List<StockOnSell> listByName(String name);
    public int count();
    public int addStockShopInfo(ShopStock shopStock);
    public int deleteStockShopInfo(int id);
    public int updateStockShopInfo(ShopStock shopStock);
    public ShopStock getStockShopInfo(int id);
    public List<Stock> drawlist(String type,String name);
    public List<Stock> showStock(NameAndId nameAndId);
    public List<Stock> showOnSellStock(NameAndId nameAndId);
    public List<Stock> showUnderSellStock(NameAndId nameAndId);
    public boolean undercarrage(IdAndId idAndId);
    public boolean upcarrage(IdAndId idAndId);
    public List<Stock> convertFromStockOnsellToStock(List<StockOnSell>sos);
}
