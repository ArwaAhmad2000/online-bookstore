package com.example.onlinebookstore.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class TransactionBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private double price;

    private Integer quantity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private TransactionHistory transaction;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Book book;

}
