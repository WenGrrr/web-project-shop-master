package com.underfit.trpo.dto;

import com.underfit.trpo.entities.Orders;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * A DTO for the {@link com.underfit.trpo.entities.Orders} entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDto implements Serializable {
    private Long id;
    private String passtype;
    private Integer price;
    private Integer countProduct;
    private Long productidfk;
    private Long selleridfk;

    public Orders toEntity() {
        Orders orders = new Orders();
        BeanUtils.copyProperties(this, orders);
        return orders;
    }

    public static OrdersDto toDto(Orders orders) {
        OrdersDto ordersDto = new OrdersDto();
        BeanUtils.copyProperties(orders, ordersDto);
        if (orders.getProductidfk() != null) {
            ordersDto.setProductidfk(orders.getProductidfk().getId());
        }
        if (orders.getSelleridfk() != null) {
            ordersDto.setSelleridfk(orders.getSelleridfk().getId());
        }
        return ordersDto;
    }
}