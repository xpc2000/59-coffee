package com.code59.caffemall.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.code59.caffemall.bean.Collection;
import com.code59.caffemall.dao.CollectionDao;
import com.code59.caffemall.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@DS("coffee_guest")
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    CollectionDao collectiondao;

    @Override
    public int add(Collection collection){
        return collectiondao.insert(collection);
    }

    @Override
    public int delete(String id_guest,String id_food){
        Map<String, Object> columnMap = new HashMap<String, Object>();
        columnMap.put("id_guest",id_guest);
        columnMap.put("id_food",id_food);
        return collectiondao.deleteByMap(columnMap);
    }

    @Override
    public int update(Collection collection){
        return collectiondao.updateById((collection));
    }

    @Override
    public List<Collection> get(String id_guest, String id_food){
        Map<String,Object>columMap=new HashMap<String,Object>();
        columMap.put("id_guest",id_guest);
        columMap.put("id_food",id_food);
        return collectiondao.selectByMap(columMap);
    }

    @Override
    public List<Collection> list(){
        return collectiondao.selectList(null);
    }
}
