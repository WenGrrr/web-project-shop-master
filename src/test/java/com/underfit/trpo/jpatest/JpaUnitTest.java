package com.underfit.trpo.jpatest;

import com.underfit.trpo.dao.*;
import com.underfit.trpo.entities.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class JpaUnitTest {
    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private RateRepository rateRepository;
    @Autowired
    private UserRepository userRepository;
    Rate rate;

    @BeforeEach
    public void setupData() {
        rateRepository.deleteAll();
        ordersRepository.deleteAll();
        buyerRepository.deleteAll();
        productRepository.deleteAll();
        sellerRepository.deleteAll();
        userRepository.deleteAll();

        Product product1 = new Product("noSql");
        Product product2 = new Product("Java");
        productRepository.save(product1);
        productRepository.save(product2);

        Seller seller1 = new Seller(
                "Анфиса", LocalDate.of(1989, 3, 20), "ж", "доцент",
                "bib", "4354543");
        Seller seller2 = new Seller(
                "Алексей", LocalDate.of(1978, 3, 20), "м", "доцент",
                "bub", "111111");
        sellerRepository.save(seller1);
        sellerRepository.save(seller2);

        Orders orders = new Orders("хлебный", 54, 1, product1, seller1);
        Orders orders1 = new Orders("молочный", 54, 2, product2, seller2);
        ordersRepository.save(orders);
        ordersRepository.save(orders1);

        Buyer buyer1 = new Buyer("Андрей", LocalDate.of(1999, 3, 20), "м", "4354543", "ffgfg@mail.ru");
        Buyer buyer2 = new Buyer("Алексей", LocalDate.of(1998, 12, 31), "м", "784654", "2222@mail.ru");
        buyerRepository.save(buyer1);
        buyerRepository.save(buyer2);

        Rate rate1 = new Rate("5", LocalDate.of(2019, 1, 14), buyer1, orders);
        Rate rate2 = new Rate("4", LocalDate.of(2019, 1, 14), buyer1, orders1);
        Rate rate3 = new Rate("3", LocalDate.of(2019, 1, 14), buyer2, orders);
        rate = new Rate("3", LocalDate.of(2019, 1, 14), buyer2, orders1);

        rateRepository.save(rate1);
        rateRepository.save(rate2);
        rateRepository.save(rate3);
        rateRepository.save(rate);

        User user1 = new User("19694283", String.valueOf("123".hashCode()), Role.ROLE_USER, "1332@gmail.com");
        User user2 = new User("1111", String.valueOf("111".hashCode()), Role.ROLE_ADMIN, "1313@gmail.com");

        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Test
    @Order(1)
    public void findData() {
        userRepository.findAll().forEach(System.out::println);

        assertThat(rateRepository.findAllRates().orElse(null)).hasSize(4);
        assertThat(productRepository.findAll()).hasSize(2);
        assertThat(sellerRepository.findAll()).hasSize(2);
        assertThat(ordersRepository.findAllOrders().orElse(null)).hasSize(2);
        assertThat(buyerRepository.findAll()).hasSize(2);
        assertThat(userRepository.findAll()).hasSize(2);
    }

    @Test
    @Order(2)
    public void updateData() {
        rate.setEvaluation("4");
        rate = rateRepository.save(rate);
        Assertions.assertEquals("4", rate.getEvaluation());
    }

    @Test
    @Order(3)
    public void deleteData() {
        Long id = rate.getId();
        rateRepository.deleteById(id);
        Assertions.assertFalse(rateRepository.findById(id).isPresent());
    }
}
