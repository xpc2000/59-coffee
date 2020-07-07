package com.code59.caffemall.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.code59.caffemall.bean.Shop;
import com.code59.caffemall.dao.ShopDao;
import com.code59.caffemall.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@DS("master")
public class ShopServiceImpl implements ShopService {

    @Autowired
    ShopDao shopDao;
    @Override
    public int add(Shop shop) {
        return shopDao.insert(shop);
    }

    @Override
    public int delete(String id) {
        return shopDao.deleteById(id);
    }

    @Override
    public int update(Shop shop) {
        return shopDao.updateById(shop);
    }

    @Override
    public Shop get(String id) {
        return shopDao.selectById(id);
    }

    @Override
    public List<Shop> list() {
        return shopDao.selectList(null);
    }
}
