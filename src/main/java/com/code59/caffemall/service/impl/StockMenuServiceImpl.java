package com.code59.caffemall.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.code59.caffemall.bean.Guest;
import com.code59.caffemall.bean.ShopStock;
import com.code59.caffemall.bean.StockOnSell;
import com.code59.caffemall.dao.ShopStockDao;
import com.code59.caffemall.dao.StockOnSellDao;
import com.code59.caffemall.service.StockMenuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DS("coffee_shop")
public class StockMenuServiceImpl implements StockMenuService {
    @Autowired
    ShopStockDao shopStockDao;
    @Autowired
    StockOnSellDao stockOnSellDao;

    @Override
    public int add(StockOnSell stockOnSell) {
        return stockOnSellDao.insert(stockOnSell);
    }

    @Override
    public int delete(int id) {
        return stockOnSellDao.deleteById(id);
    }

    @Override
    public int update(StockOnSell stockOnSell) {
        return stockOnSellDao.updateById(stockOnSell);
    }
    @Override
    public StockOnSell get(int id) {
        return stockOnSellDao.selectById(id);
    }

    @Override
    public List<StockOnSell> list() {
        return stockOnSellDao.selectList(null);
    }

    @Override
    public List<StockOnSell> listByType(String type) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("type",type);
        return stockOnSellDao.selectList(wrapper);
    }

    @Override
    public List<StockOnSell> listByName(String name) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.like("name",name);
        return stockOnSellDao.selectList(wrapper);
    }

    @Override
    public int count() {
        return stockOnSellDao.selectCount(null);
    }

    @Override
    public int addStockShopInfo(ShopStock shopStock) {
        return shopStockDao.insert(shopStock);
    }

    @Override
    public int deleteStockShopInfo(int id) {
        return shopStockDao.deleteById(id);
    }

    @Override
    public int updateStockShopInfo(ShopStock shopStock) {
        return shopStockDao.updateById(shopStock);
    }

    @Override
    public ShopStock getStockShopInfo(int id) {
        return shopStockDao.selectById(id);
    }
}
