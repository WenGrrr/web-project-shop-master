package com.underfit.trpo.controller;


import com.underfit.trpo.dto.BuyerDto;
import com.underfit.trpo.service.BuyerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/buyers")
public class BuyerController {
    private final BuyerServiceImpl buyerService;

    @GetMapping
    public List<BuyerDto> getBuyers() {
        return buyerService.getAll();
    }

    @GetMapping("/{id}")
    public BuyerDto getBuyer(@PathVariable Long id) {
        return buyerService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public BuyerDto create(@RequestBody BuyerDto buyerDto) {
        if (buyerDto.getId() != null) {
            buyerDto.setId(null);
        }
        return buyerService.save(buyerDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public BuyerDto update(@PathVariable Long id, @RequestBody BuyerDto buyerDto) {
        buyerDto.setId(id);
        return buyerService.save(buyerDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        buyerService.delete(id);
    }
}
