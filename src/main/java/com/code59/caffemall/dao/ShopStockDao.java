package com.code59.caffemall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.code59.caffemall.bean.Guest;
import com.code59.caffemall.bean.ShopStock;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ShopStockDao extends BaseMapper<ShopStock> {

}
