package com.underfit.trpo.controller;

import com.underfit.trpo.dto.RateDto;
import com.underfit.trpo.service.RateServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rates")
public class RateController {
    private final RateServiceImpl rateService;

    @GetMapping
    public List<RateDto> getRates() {
        return rateService.getAll();
    }

    @GetMapping("/{id}/orders")
    public List<RateDto> getRatesByIdOrders(@PathVariable Long id) {
        return rateService.getAllByIdOrders(id);
    }

    @GetMapping("/{id}")
    public RateDto getRate(@PathVariable Long id) {
        return rateService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public RateDto create(@RequestBody RateDto rateDto) {
        if (rateDto.getId() != null) {
            rateDto.setId(null);
        }
        return rateService.save(rateDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RateDto update(@PathVariable Long id, @RequestBody RateDto rateDto) {
        rateDto.setId(id);
        return rateService.save(rateDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        rateService.delete(id);
    }
}
