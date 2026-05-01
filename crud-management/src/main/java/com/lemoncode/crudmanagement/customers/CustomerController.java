package com.lemoncode.crudmanagement.customers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/customers")
public class CustomerController {
    private CustomerServiceImpl service;

    @GetMapping
    public ResponseEntity<List<Customer>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/{email}")
    public ResponseEntity<Customer> findByEmail(@RequestParam("email") @Validated String email) {
        service.findByEmail(email);
        return ResponseEntity.ok().build();
    }

}
