package com.lemoncode.crudmanagement.customers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private CustomerResponse response;
    private CustomerRequest request;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        //Customer customerCheck = customerRepository.findById(customer.);
        return null;
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return null;
    }

    @Override
    public List<Customer> findByStatus(String status, Pageable pageable) {
        return List.of();
    }

    @Override
    public Page<Customer> listCustomer(Integer userId, Pageable pageable) {
        return null;
    }

    @Override
    public Customer findByEmail(String email) {
        return null;
    }

    @Override
    public List<Customer> findAll() {
        return List.of();
    }

    @Override
    public void delete(Customer customer) {

    }

    @Override
    public Customer findById(String id) {
        return null;
    }

}
