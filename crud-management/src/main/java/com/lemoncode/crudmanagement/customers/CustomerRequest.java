package com.lemoncode.crudmanagement.customers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        @NotNull(message = "Name cannot be null.")
        @NotBlank(message = "Name is required.")    @NotNull(message = "Name cannot be null.")
        @NotBlank(message = "Name is required.")
        String firstname,
        @NotNull(message = "Name cannot be null.")
        @NotBlank(message = "Name is required.")
        String lastname,
        String nif,
        String email,
        String phone,
        String address,
        Status status,
        String comment
) {
}
