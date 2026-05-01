package com.lemoncode.crudmanagement.customers;

import com.lemoncode.crudmanagement.common.Email;
import com.lemoncode.crudmanagement.common.Nif;
import jakarta.persistence.*;

@Entity
@Table(name = "customer_tb")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int clientId;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "nif")
    @OneToMany(cascade = CascadeType.ALL)
    private Nif nif;
    @Column(name = "email", unique = true)
    private Email email;
    @Column(name = "phone", unique = true)
    private Phone phone;
    @Column(name = "address")
    private Address address;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String comment;

    public Customer() {
    }


}
