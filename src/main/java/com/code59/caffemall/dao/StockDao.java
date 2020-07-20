package com.code59.caffemall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.code59.caffemall.bean.Stock;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StockDao {
    public List<Stock> selectByFoodName(String name);
    public List<Stock> selectOnSellByFoodName(String name);
    public List<Stock> selectUnderSellByFoodName(String name);
}
