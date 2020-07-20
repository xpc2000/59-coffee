package com.code59.caffemall.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.code59.caffemall.bean.ShopStock;
import com.code59.caffemall.bean.Stock;
import com.code59.caffemall.bean.StockOnSell;
import com.code59.caffemall.dao.ShopStockDao;
import com.code59.caffemall.dao.StockDao;
import com.code59.caffemall.dao.StockOnSellDao;
import com.code59.caffemall.service.StockMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@DS("coffee_shop")
public class StockMenuServiceImpl implements StockMenuService {
    @Autowired
    ShopStockDao shopStockDao;
    @Autowired
    StockOnSellDao stockOnSellDao;
    @Autowired
    StockDao stockDao;

    @Override
    public int add(StockOnSell stockOnSell) { return stockOnSellDao.insert(stockOnSell);
    }

    @Override
    public int delete(int id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id_food",id);
        return stockOnSellDao.delete(wrapper);
    }

    @Override
    public int update(StockOnSell stockOnSell) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id_food",stockOnSell.getIdfood());
        return stockOnSellDao.update(stockOnSell,wrapper);
    }
    @Override
    public StockOnSell get(int id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id_food",id);
        return stockOnSellDao.selectOne(wrapper);
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

    @Override
    public List<Stock> drawlist(String type,String name){
        List<StockOnSell> list=new ArrayList<>();
        if(type!=null)
            list=listByType(type);
        else if(name!=null)
            list=listByName(name);
        else
            list=list();

        Stock show=new Stock();
        ShopStock temp =new ShopStock();
        List<Stock> result=null;

        for(int i=0;i<list.size();i++)
        {
           show.setIdFood(list.get(i).getIdfood());
           show.setDiscount(list.get(i).getDiscount());
           show.setName(list.get(i).getName());
           show.setPrice(list.get(i).getPrice());
           show.setSellOut(list.get(i).isSellOut());
           show.setUrl(list.get(i).getUrl());
           show.setType(list.get(i).getType());
           temp=shopStockDao.selectById(show.getIdFood());
           show.setNum(temp.getNum());
           result.add(show);
        }
        return result;
    }

    @Override
    public List<Stock> showStock(String foodName) {
        List<Stock> stocks= stockDao.selectByFoodName(foodName);
        stocks.forEach(stock -> System.out.println(stock));
        return stocks;
    }

    @Override
    public List<Stock> showOnSellStock(String foodName) {
        List<Stock> stocksOnSell=stockDao.selectOnSellByFoodName(foodName);
        return stocksOnSell;
    }

    @Override
    public List<Stock> showUnderSellStock(String foodName) {
        return stockDao.selectUnderSellByFoodName(foodName);
    }

    @Override
    public boolean undercarrage(String foodid) {
        QueryWrapper<StockOnSell>wrapper=new QueryWrapper<>();
        wrapper.eq("id_food",foodid);
        StockOnSell temp=stockOnSellDao.selectOne(wrapper);
        if(temp==null)return false;
        temp.setSellOut("u");
        int num=stockOnSellDao.update(temp,wrapper);
        if(num==1)return true;
        return false;
    }
}
