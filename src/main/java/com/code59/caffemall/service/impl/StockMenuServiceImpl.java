package com.code59.caffemall.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.code59.caffemall.bean.ShopStock;
import com.code59.caffemall.bean.Stock;
import com.code59.caffemall.bean.StockOnSell;
import com.code59.caffemall.dao.ShopStockDao;
import com.code59.caffemall.dao.StockOnSellDao;
import com.code59.caffemall.service.StockMenuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@DS("coffee_shop")
public class StockMenuServiceImpl implements StockMenuService {
    @Autowired
    ShopStockDao shopStockDao;
    @Autowired
    StockOnSellDao stockOnSellDao;

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
           show.setIdfood(list.get(i).getIdfood());
           show.setDiscount(list.get(i).getDiscount());
           show.setName(list.get(i).getName());
           show.setPrice(list.get(i).getPrice());
           show.setSellOut(list.get(i).isSellOut());
           show.setUrl(list.get(i).getUrl());
           show.setType(list.get(i).getType());
           temp=shopStockDao.selectById(show.getIdfood());
           show.setNum(temp.getNum());
           result.add(show);
        }
        return result;
    }
}
