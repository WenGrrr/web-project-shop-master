package com.underfit.trpo.entities;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productid", nullable = false)
    private Long id;

    @Column(name = "productname", nullable = false, length = 50)
    private String productname;

    public Product(String productname) {
        this.productname = productname;
    }
}