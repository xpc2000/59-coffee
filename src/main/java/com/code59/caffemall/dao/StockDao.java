package com.code59.caffemall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.code59.caffemall.bean.Stock;
import com.code59.caffemall.bean.StockOnSell;
import com.code59.caffemall.controller.Order.tempVar.NameAndId;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StockDao {
    public List<Stock> selectByFoodName(NameAndId nameAndId);
    public List<Stock> selectOnSellByFoodName(NameAndId nameAndId);
    public List<Stock> selectUnderSellByFoodName(NameAndId nameAndId);
}
