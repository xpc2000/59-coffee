package com.code59.caffemall.dao;

import com.code59.caffemall.bean.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;

@Repository
public interface CustomerDao {
    public int add(Customer customer);
    public void delete(int id);
    public Customer get(int id);
    public List<Customer> list();
    public int count();
}
