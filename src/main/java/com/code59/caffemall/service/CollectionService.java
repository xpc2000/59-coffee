package com.code59.caffemall.service;

import com.code59.caffemall.bean.Collection;

import java.util.List;

public interface CollectionService {
    public int add(Collection collection);
    public int delete(String id_guest,String id_food);
    public int update(Collection collection);
    public List<Collection> get(String id_guest,String id_food);
    public List<Collection> list();
}
