package com.underfit.trpo.service;

import com.underfit.trpo.dao.BuyerRepository;
import com.underfit.trpo.dao.OrdersRepository;
import com.underfit.trpo.dao.RateRepository;
import com.underfit.trpo.dto.RateDto;
import com.underfit.trpo.entities.Rate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RateServiceImpl implements ShopService<RateDto> {
    private final RateRepository rateRepository;
    private final OrdersRepository ordersRepository;
    private final BuyerRepository buyerRepository;

    @Override
    public List<RateDto> getAll() {
        return rateRepository
                .findAllRates().orElseThrow()
                .stream()
                .map(RateDto::toDto)
                .collect(Collectors.toList());
    }

    public List<RateDto> getAllByIdOrders(Long id) {
        return rateRepository
                .findAllRatesByIdOrders(id).orElseThrow()
                .stream()
                .map(RateDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public RateDto getById(Long id) {
        Rate rate = rateRepository.findById(id).orElse(null);
        return rate != null ? RateDto.toDto(rate) : null;
    }

    @Override
    public RateDto save(RateDto dto) {
        Rate rate = dto.toEntity();
        if (dto.getOrdersidfk() != null)
            rate.setOrdersidfk(ordersRepository.findById(dto.getOrdersidfk()).orElseThrow());
        if (dto.getBuyeridfk() != null)
            rate.setBuyeridfk(buyerRepository.findById(dto.getBuyeridfk()).orElseThrow());
        return RateDto.toDto(rateRepository.save(rate));
    }

    @Override
    public void delete(Long id) {
        rateRepository.deleteById(id);
    }
}
