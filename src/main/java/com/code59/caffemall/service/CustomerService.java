package com.code59.caffemall.service;

import com.code59.caffemall.bean.Customer;

public interface CustomerService {
    public Customer get(int id);
    public void add(Customer customer);
}
