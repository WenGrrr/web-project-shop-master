package com.underfit.trpo.service;

import com.underfit.trpo.dao.BuyerRepository;
import com.underfit.trpo.dto.BuyerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuyerServiceImpl implements ShopService<BuyerDto> {
    private final BuyerRepository buyerRepository;
    @Override
    public List<BuyerDto> getAll() {
        return buyerRepository
                .findAll()
                .stream()
                .map(BuyerDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BuyerDto getById(Long id) {
        return BuyerDto.toDto(buyerRepository.findById(id).orElse(null));
    }

    @Override
    public BuyerDto save(BuyerDto dto) {
        return BuyerDto.toDto(buyerRepository.save(dto.toEntity()));
    }

    @Override
    public void delete(Long id) {
        buyerRepository.deleteById(id);
    }
}
