package com.lemoncode.crudmanagement.customers;

public record CustomerResponse(
        String firstName,
        String lastName,
        String nif,
        String email,
        String phone,
        String address,
        Status status,
        String comment
) {
}
