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
@Table(name = "seller")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sellerid", nullable = false)
    private Long id;

    @Column(name = "fio", nullable = false, length = 50)
    private String fio;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "gender", length = 2)
    private String gender;

    @Column(name = "title", length = 50)
    private String title;

    @Column(name = "sellercompany", length = 50)
    private String sellercompany;

    @Column(name = "phone", length = 15)
    private String phone;

    public Seller(String fio, LocalDate birthday, String gender, String title, String sellercompany, String phone) {
        this.fio = fio;
        this.birthday = birthday;
        this.gender = gender;
        this.title = title;
        this.sellercompany = sellercompany;
        this.phone = phone;
    }
}