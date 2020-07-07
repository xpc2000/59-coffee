package com.code59.caffemall.service;

import com.code59.caffemall.bean.Guest;
import com.code59.caffemall.bean.Shop;
import com.code59.caffemall.dao.GuestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    public int add(Guest guest);
    public int delete(String id);
    public int update(Guest guest);
    public Guest get(String id);
    public List<Guest> list();
    public List<Guest> getUserByMessage(String id,String password);
    public List<Shop> getAdminByMessage(String id,String password);
}
