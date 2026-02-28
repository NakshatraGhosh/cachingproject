package com.caching.demo;


import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;   
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
 


@Entity
@Table(name = "books")
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  private Double price;

  public Book() {}

  public Book(String name, Double price) {
    this.name = name;
    this.price = price;
  }

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public Double getPrice() { return price; }
  public void setPrice(Double price) { this.price = price; }
}