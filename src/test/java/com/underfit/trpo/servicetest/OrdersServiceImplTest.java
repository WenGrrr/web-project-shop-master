package com.underfit.trpo.servicetest;

import com.underfit.trpo.dao.OrdersRepository;
import com.underfit.trpo.dto.OrdersDto;
import com.underfit.trpo.entities.Orders;
import com.underfit.trpo.service.OrdersServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class OrdersServiceImplTest {
    @MockBean
    private OrdersRepository ordersRepository;

    @Autowired
    private OrdersServiceImpl ordersService;

    private Orders orders;

    @BeforeEach
    public void setup() {
        orders = new Orders(1L, "экзамен", 54, 2, null, null);
    }

    @Test
    public void getByIdTest() {
        when(ordersRepository.findById(1L)).thenReturn(Optional.of(orders));
        OrdersDto ordersDto = ordersService.getById(orders.getId());
        Assertions.assertEquals(orders.getId(), ordersDto.toEntity().getId());
    }

    @Test
    public void getAllWhenNullTest() {
        when(ordersRepository.findAllOrders()).thenReturn(null);
        Assertions.assertThrows(NullPointerException.class, () -> ordersService.getAll());
    }

    @Test
    public void getAllTest() {
        Orders orders1 = new Orders(2L, "зачет", 36, 1, null, null);
        List<Orders> list = List.of(orders, orders1);
        when(ordersRepository.findAllOrders()).thenReturn(Optional.of(list));
        List<OrdersDto> ordersDtos = ordersService.getAll();
        assertThat(ordersDtos).hasSize(2);
    }

    @Test
    public void saveTest() {
        when(ordersRepository.save(any(Orders.class))).thenReturn(orders);
        OrdersDto ordersDto = OrdersDto.toDto(orders);
        OrdersDto saved = ordersService.save(ordersDto);
        Assertions.assertEquals(orders.getId(), saved.getId());
    }

    @Test
    public void deleteTest() {
        doNothing().when(ordersRepository).deleteById(1L);
        ordersService.delete(orders.getId());
        Assertions.assertFalse(ordersRepository.findById(orders.getId()).isPresent());
    }
}
