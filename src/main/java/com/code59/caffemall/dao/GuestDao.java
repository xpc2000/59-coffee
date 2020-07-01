package com.code59.caffemall.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.code59.caffemall.bean.Guest;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestDao extends BaseMapper<Guest> {
}
