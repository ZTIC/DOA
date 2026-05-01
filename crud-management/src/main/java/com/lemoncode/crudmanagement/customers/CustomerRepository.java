package com.lemoncode.crudmanagement.customers;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findById(@NotNull Integer clientId);

    // Verify if STRING NAME is on firstname or lastname
    @Query("""
            SELECT c FROM Customer c WHERE c.firstname LIKE %:name%
            OR
            c.lastname LIKE %:name%
            """)
    public Customer findByName(String name);

    public Customer findByEmail(String email);

    public Customer findByPhone(String phone);

    public List<Customer> findByStatus(String status);

}
