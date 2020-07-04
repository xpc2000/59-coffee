package com.code59.caffemall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.code59.caffemall.bean.Shop;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopDao extends BaseMapper<Shop> {
}
