package com.code59.caffemall.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.code59.caffemall.bean.Food;
import com.code59.caffemall.dao.FoodDao;
import com.code59.caffemall.service.MenuService;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
//只操作总菜单的信息
@Service
@DS("master")
public class MenuServiceImpl implements MenuService {
    @Autowired
    FoodDao foodDao;

    @Override
    public int add(Food food) {
        return foodDao.insert(food);
    }

    @Override
    public int delete(int id) {
        return foodDao.deleteById(id);
    }

    @Override
    public int update(Food food) {
        return foodDao.updateById(food);
    }

    @Override
    public Food get(int id) {
        return foodDao.selectById(id);
    }

    @Override
    public List<Food> list() {
        return foodDao.selectList(null);
    }

    @Override
    public int count() {
        return foodDao.selectCount(null);
    }

    @Override
    public List<Food> getFoodByType(String type) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("type",type);
        return foodDao.selectList(wrapper);
    }

    @Override
    public List<Food> getFoodByName(String name) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.like("name",name);
        return foodDao.selectList(wrapper);
    }

}
