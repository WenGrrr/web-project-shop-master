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
@Table(name = "buyer", indexes = {
        @Index(name = "buyer_email_key", columnList = "email", unique = true)
})
public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "buyerid", nullable = false)
    private Long id;

    @Column(name = "fio", nullable = false, length = 50)
    private String fio;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "gender", length = 2)
    private String gender;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "email", length = 50)
    private String email;

    public Buyer(String fio, LocalDate birthday, String gender, String phone, String email) {
        this.fio = fio;
        this.birthday = birthday;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
    }
}