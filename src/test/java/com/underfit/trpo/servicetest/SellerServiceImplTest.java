package com.underfit.trpo.servicetest;

import com.underfit.trpo.dao.SellerRepository;
import com.underfit.trpo.dto.SellerDto;
import com.underfit.trpo.entities.Seller;
import com.underfit.trpo.service.SellerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class SellerServiceImplTest {
    @MockBean
    private SellerRepository sellerRepository;

    @Autowired
    private SellerServiceImpl sellerService;

    private Seller seller;

    @BeforeEach
    public void setup() {
        seller = new Seller(1L,
                "Алексей", LocalDate.of(1978, 3, 20), "м", "доцент",
                "кандидат наук", "111111");
    }

    @Test
    public void getByIdTest() {
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));
        SellerDto sellerDto = sellerService.getById(seller.getId());
        Assertions.assertEquals(seller.getId(), sellerDto.toEntity().getId());
    }

    @Test
    public void getAllWhenNullTest() {
        when(sellerRepository.findAll()).thenReturn(null);
        Assertions.assertThrows(NullPointerException.class, () -> sellerService.getAll());
    }

    @Test
    public void getAllTest() {
        Seller seller1 = new Seller(1L,
                "Петр", LocalDate.of(1960, 1, 20), "м", "доцент",
                "кандидат наук", "111112");
        List<Seller> list = List.of(seller, seller1);
        when(sellerRepository.findAll()).thenReturn(list);
        List<SellerDto> sellerDtos = sellerService.getAll();
        assertThat(sellerDtos).hasSize(2);
    }

    @Test
    public void saveTest() {
        when(sellerRepository.save(any(Seller.class))).thenReturn(seller);
        SellerDto sellerDto = SellerDto.toDto(seller);
        SellerDto saved = sellerService.save(sellerDto);
        Assertions.assertEquals(seller.getId(), saved.getId());
    }

    @Test
    public void deleteTest() {
        doNothing().when(sellerRepository).deleteById(1L);
        sellerService.delete(seller.getId());
        Assertions.assertFalse(sellerRepository.findById(seller.getId()).isPresent());
    }
}
