package com.underfit.trpo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.underfit.trpo.entities.Buyer;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.underfit.trpo.entities.Buyer} entity
 */
@Data
public class BuyerDto implements Serializable {
    private Long id;
    private String fio;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthday;
    private String gender;
    private String phone;
    private String email;

    public Buyer toEntity() {
        Buyer buyer = new Buyer();
        BeanUtils.copyProperties(this, buyer);
        return buyer;
    }

    public static BuyerDto toDto(Buyer buyer) {
        BuyerDto buyerDto = new BuyerDto();
        BeanUtils.copyProperties(buyer, buyerDto);
        return buyerDto;
    }
}