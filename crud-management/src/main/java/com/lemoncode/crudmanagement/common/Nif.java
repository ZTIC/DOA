package com.lemoncode.crudmanagement.common;

import jakarta.persistence.*;

@Entity
@Table(name = "nif_tb")
public class Nif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nif")
    private String nif;
    @Column(name = "comment")
    private String comment;

    public Nif() {
    }

    public Nif(Long id, String nif, String comment) {
        this.id = id;
        this.nif = nif;
        this.comment = comment;
    }
}
