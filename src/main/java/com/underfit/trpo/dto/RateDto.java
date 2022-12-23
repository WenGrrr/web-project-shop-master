package com.underfit.trpo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.underfit.trpo.entities.Rate;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.underfit.trpo.entities.Rate} entity
 */
@Data
public class RateDto implements Serializable {
    private Long id;
    private String evaluation;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate buydate;
    private Long buyeridfk;
    private Long ordersidfk;

    public Rate toEntity() {
        Rate rate = new Rate();
        BeanUtils.copyProperties(this, rate);
        return rate;
    }

    public static RateDto toDto(Rate rate) {
        RateDto rateDto = new RateDto();
        BeanUtils.copyProperties(rate, rateDto);
        if (rate.getOrdersidfk() != null) {
            rateDto.setOrdersidfk(rate.getOrdersidfk().getId());
        }
        if (rate.getBuyeridfk() != null) {
            rateDto.setBuyeridfk(rate.getBuyeridfk().getId());
        }
        return rateDto;
    }
}