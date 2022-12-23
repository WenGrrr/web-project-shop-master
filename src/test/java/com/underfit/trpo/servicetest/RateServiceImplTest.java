package com.underfit.trpo.servicetest;

import com.underfit.trpo.dao.RateRepository;
import com.underfit.trpo.dto.RateDto;
import com.underfit.trpo.entities.Orders;
import com.underfit.trpo.entities.Rate;
import com.underfit.trpo.service.RateServiceImpl;
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
public class RateServiceImplTest {
    @MockBean
    private RateRepository rateRepository;

    @Autowired
    private RateServiceImpl rateService;

    private Rate rate;

    @BeforeEach
    public void setup() {
        rate = new Rate(1L, "4", LocalDate.of(2019, 1, 14), null, null);
    }

    @Test
    public void getByIdTest() {
        when(rateRepository.findById(1L)).thenReturn(Optional.of(rate));
        RateDto rateDto = rateService.getById(rate.getId());
        Assertions.assertEquals(rate.getId(), rateDto.toEntity().getId());
    }

    @Test
    public void getAllWhenNullTest() {
        when(rateRepository.findAllRates()).thenReturn(null);
        Assertions.assertThrows(NullPointerException.class, () -> rateService.getAll());
    }

    @Test
    public void getAllTest() {
        Rate rate1 = new Rate(1L, "4", LocalDate.of(2019, 1, 14), null, null);
        List<Rate> list = List.of(rate, rate1);
        when(rateRepository.findAllRates()).thenReturn(Optional.of(list));
        List<RateDto> ordersDto = rateService.getAll();
        assertThat(ordersDto).hasSize(2);
    }

    @Test
    public void getAllByIdTest() {
        Rate list1 = new Rate(1L, "4", LocalDate.of(2019, 1, 14), null, new Orders(1L, "Зачет", 24, 2,null, null));
        List<Rate> list = List.of(list1);
        when(rateRepository.findAllRatesByIdOrders(1L)).thenReturn(Optional.of(list));
        List<RateDto> ordersDto = rateService.getAllByIdOrders(1L);
        assertThat(ordersDto).hasSize(1);
        Assertions.assertEquals("4", ordersDto.get(0).getEvaluation());
    }

    @Test
    public void saveTest() {
        when(rateRepository.save(any(Rate.class))).thenReturn(rate);
        RateDto rateDto = RateDto.toDto(rate);
        RateDto saved = rateService.save(rateDto);
        Assertions.assertEquals(rate.getId(), saved.getId());
    }

    @Test
    public void deleteTest() {
        doNothing().when(rateRepository).deleteById(1L);
        rateService.delete(rate.getId());
        Assertions.assertFalse(rateRepository.findById(rate.getId()).isPresent());
    }
}
