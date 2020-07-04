package com.code59.caffemall.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.code59.caffemall.bean.Guest;
import com.code59.caffemall.bean.Shop;
import com.code59.caffemall.dao.GuestDao;
import com.code59.caffemall.dao.ShopDao;
import com.code59.caffemall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@DS("master")
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    GuestDao guestDao;
    @Autowired
    ShopDao shopDao;
    @Override
    public int add(Guest guest) {
        return guestDao.insert(guest);
    }

    @Override
    public int delete(String id) {
        return guestDao.deleteById(id);
    }

    @Override
    public int update(Guest guest) {
        return guestDao.updateById(guest);
    }

    @Override
    public Guest get(String id) {
        return guestDao.selectById(id);
    }

    @Override
    public List<Guest> list() {
        return guestDao.selectList(null);
    }

    @Override
    public List<Guest> getUserByMessage(String username, String password) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("name",username);
        wrapper.eq("password",password);
        return guestDao.selectList(wrapper);
    }

    @Override
    public List<Shop> getAdminByMessage(String id, String password)
    {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id",id);
        wrapper.eq("password",password);
        return shopDao.selectList(wrapper);
    }
}
