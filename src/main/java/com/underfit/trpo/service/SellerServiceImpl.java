package com.underfit.trpo.service;

import com.underfit.trpo.dao.SellerRepository;
import com.underfit.trpo.dto.SellerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements ShopService<SellerDto> {
    private final SellerRepository sellerRepository;
    @Override
    public List<SellerDto> getAll() {
        return sellerRepository
                .findAll()
                .stream()
                .map(SellerDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SellerDto getById(Long id) {
        return SellerDto.toDto(sellerRepository.findById(id).orElse(null));
    }

    @Override
    public SellerDto save(SellerDto dto) {
        return SellerDto.toDto(sellerRepository.save(dto.toEntity()));
    }

    @Override
    public void delete(Long id) {
        sellerRepository.deleteById(id);
    }
}
