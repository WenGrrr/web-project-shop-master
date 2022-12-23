package com.underfit.trpo.controller;

import com.underfit.trpo.dto.SellerDto;
import com.underfit.trpo.service.SellerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/sellers")
@RequiredArgsConstructor
public class SellerController {
    private final SellerServiceImpl sellerService;

    @GetMapping
    public List<SellerDto> getSellers() {
        return sellerService.getAll();
    }

    @GetMapping("/{id}")
    public SellerDto getSeller(@PathVariable Long id) {
        return sellerService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public SellerDto create(@RequestBody SellerDto sellerDto) {
        if (sellerDto.getId() != null) {
            sellerDto.setId(null);
        }
        return sellerService.save(sellerDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public SellerDto update(@PathVariable Long id, @RequestBody SellerDto sellerDto) {
        sellerDto.setId(id);
        return sellerService.save(sellerDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        sellerService.delete(id);
    }
}
