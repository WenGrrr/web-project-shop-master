package com.underfit.trpo.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "rate")
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rateid", nullable = false)
    private Long id;

    @Column(name = "evaluation", length = 10)
    private String evaluation;
    @Column(name = "buydate", nullable = false)
    private LocalDate buydate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyeridfk")
    @ToString.Exclude
    private Buyer buyeridfk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordersidfk")
    @ToString.Exclude
    private Orders ordersidfk;

    public Rate(String evaluation, LocalDate buydate, Buyer buyeridfk, Orders ordersidfk) {
        this.evaluation = evaluation;
        this.buydate = buydate;
        this.buyeridfk = buyeridfk;
        this.ordersidfk = ordersidfk;
    }
}