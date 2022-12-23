package com.underfit.trpo.service;

import com.underfit.trpo.dao.OrdersRepository;
import com.underfit.trpo.dao.ProductRepository;
import com.underfit.trpo.dao.SellerRepository;
import com.underfit.trpo.dto.OrdersDto;
import com.underfit.trpo.entities.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements ShopService<OrdersDto> {
    private final OrdersRepository ordersRepository;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;

    @Override
    public List<OrdersDto> getAll() {
        return ordersRepository.findAllOrders().orElseThrow()
                .stream()
                .map(OrdersDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrdersDto getById(Long id) {
        Orders orders = ordersRepository.findById(id).orElse(null);
        return OrdersDto.toDto(orders);
    }

    @Override
    public OrdersDto save(OrdersDto dto) {
        Orders orders = dto.toEntity();
        if (dto.getSelleridfk() != null)
            orders.setSelleridfk(sellerRepository.findById(dto.getSelleridfk()).orElseThrow());
        if (dto.getProductidfk() != null)
            orders.setProductidfk(productRepository.findById(dto.getProductidfk()).orElseThrow());
        return OrdersDto.toDto(ordersRepository.save(orders));
    }

    @Override
    public void delete(Long id) {
        ordersRepository.deleteById(id);
    }
}
