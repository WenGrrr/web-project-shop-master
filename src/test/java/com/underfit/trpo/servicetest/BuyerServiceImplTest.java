package com.underfit.trpo.servicetest;

import com.underfit.trpo.dao.BuyerRepository;
import com.underfit.trpo.dto.BuyerDto;
import com.underfit.trpo.entities.Buyer;
import com.underfit.trpo.service.BuyerServiceImpl;
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
public class BuyerServiceImplTest {
    @MockBean
    private BuyerRepository buyerRepository;

    @Autowired
    private BuyerServiceImpl buyerService;

    private Buyer buyer;

    @BeforeEach
    public void setup() {
        buyer = new Buyer(1L,"Андрей", LocalDate.of(1999, 3, 20), "м", "4354543", "ffgfg@mail.ru");
    }

    @Test
    public void getByIdTest() {
        when(buyerRepository.findById(1L)).thenReturn(Optional.of(buyer));
        BuyerDto buyerDto = buyerService.getById(buyer.getId());
        Assertions.assertEquals(buyer.getId(), buyerDto.toEntity().getId());
    }

    @Test
    public void getAllWhenNullTest() {
        when(buyerRepository.findAll()).thenReturn(null);
        Assertions.assertThrows(NullPointerException.class, () -> buyerService.getAll());
    }

    @Test
    public void getAllTest() {
        Buyer buyer1 = new Buyer(1L,"Юра", LocalDate.of(1999, 6, 21), "м", "4354543", "ffgfg@mail.ru");
        List<Buyer> list = List.of(buyer, buyer1);
        when(buyerRepository.findAll()).thenReturn(list);
        List<BuyerDto> buyerDtos = buyerService.getAll();
        assertThat(buyerDtos).hasSize(2);
    }

    @Test
    public void saveTest() {
        when(buyerRepository.save(any(Buyer.class))).thenReturn(buyer);
        BuyerDto buyerDto = BuyerDto.toDto(buyer);
        BuyerDto saved = buyerService.save(buyerDto);
        Assertions.assertEquals(buyer.getId(), saved.getId());
    }

    @Test
    public void deleteTest() {
        doNothing().when(buyerRepository).deleteById(1L);
        buyerService.delete(buyer.getId());
        Assertions.assertFalse(buyerRepository.findById(buyer.getId()).isPresent());
    }
}
