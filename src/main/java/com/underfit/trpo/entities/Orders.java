package com.underfit.trpo.entities;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ordersid", nullable = false)
    private Long id;

    @Column(name = "passtype", nullable = false, length = 50)
    private String passtype;

    @Column(name = "price")
    private Integer price;

    @Column(name = "count_product", nullable = false)
    private Integer countProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productidfk")
    private Product productidfk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "selleridfk")
    private Seller selleridfk;

    public Orders(String passtype, Integer totalhours, Integer countProduct, Product productidfk, Seller selleridfk) {
        this.passtype = passtype;
        this.price = price;
        this.countProduct = countProduct;
        this.productidfk = productidfk;
        this.productidfk = productidfk;
    }
}