package com.lemoncode.crudmanagement.customers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    Customer findByEmail(String email);
    Customer createCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
    List<Customer> findAll();
//    Customer save(Customer customer);
//    Customer update(Customer customer);
    void delete(Customer customer);
    Customer findById(String id);
    List<Customer> findByStatus(String status,  Pageable pageable);
    Page<Customer> listCustomer(Integer userId, Pageable pageable);
}
