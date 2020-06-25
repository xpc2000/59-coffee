package com.code59.caffemall.service.impl;

import com.code59.caffemall.bean.Customer;
import com.code59.caffemall.dao.CustomerDao;
import com.code59.caffemall.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerDao customerDao;
    @Override
    public Customer get(int id) {
        return customerDao.get(id);
    }

    @Override
    public void add(Customer customer) {
        customerDao.add(customer);
    }
}
