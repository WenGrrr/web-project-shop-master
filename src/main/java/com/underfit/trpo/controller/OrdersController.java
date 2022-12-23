package com.underfit.trpo.controller;

import com.underfit.trpo.dto.OrdersDto;
import com.underfit.trpo.service.OrdersServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final OrdersServiceImpl ordersService;

    @GetMapping
    public List<OrdersDto> getOrders() {
        return ordersService.getAll();
    }

    @GetMapping(path = "/{id}")
    public OrdersDto getOrder(@PathVariable Long id) {
        return ordersService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public OrdersDto create(@RequestBody OrdersDto dto) {
        if (dto.getId() != null) {
            dto.setId(null);
        }
        return ordersService.save(dto);
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public OrdersDto update(@PathVariable Long id, @RequestBody OrdersDto dto) {
        dto.setId(id);
        return ordersService.save(dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        ordersService.delete(id);
    }
}
