package com.code59.caffemall.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.code59.caffemall.bean.StockOnSell;
import com.code59.caffemall.dao.StockOnSellDao;
import com.code59.caffemall.service.BranchMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DS("coffee_shop")
public class BranchMenuServiceImpl implements BranchMenuService {

    @Autowired
    StockOnSellDao stockOnSellDao;
    @Override
    public List<StockOnSell> getFoodByTypeNameShopid(String type, String name, String shopid) {
        QueryWrapper<StockOnSell> wrapper=new QueryWrapper<>();
        if(shopid.equals("null")||shopid.equals(null))
        {
            if(name.equals("null")||name.equals(null))
            {
                wrapper.like("type",type);
            }
            else {
                wrapper.eq("name",name)
                        .like("type",type);
            }
        }
        else
        {
            if(name.equals("null")||name.equals(null))
            {
                wrapper.like("type",type)
                        .eq("id_shop",shopid);
            }
            else{
                wrapper.eq("name",name)
                        .like("type",type)
                        .eq("id_shop",shopid);
            }
        }
        return stockOnSellDao.selectList(wrapper);
    }
}
