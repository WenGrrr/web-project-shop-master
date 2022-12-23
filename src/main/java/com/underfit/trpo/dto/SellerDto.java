package com.underfit.trpo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.underfit.trpo.entities.Seller;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link Seller} entity
 */
@Data
public class SellerDto implements Serializable {
    private Long id;
    private String fio;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthday;
    private String gender;
    private String title;
    private String companyName;
    private String phone;

    public Seller toEntity() {
        Seller seller = new Seller();
        BeanUtils.copyProperties(this, seller);
        return seller;
    }

    public static SellerDto toDto(Seller seller) {
        SellerDto sellerDto = new SellerDto();
        BeanUtils.copyProperties(seller, sellerDto);
        return sellerDto;
    }
}