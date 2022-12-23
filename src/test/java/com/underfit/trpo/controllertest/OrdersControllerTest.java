package com.underfit.trpo.controllertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.underfit.trpo.dto.OrdersDto;
import com.underfit.trpo.service.OrdersServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrdersControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean//создаем объект класса, причем он может быть интерфейсом
    // (Генерирует заглушку, перекрывает все публичные методы класса, будет вызываться метод объекта заглушки,
    // делать ничего не будет)
    private OrdersServiceImpl service;
    @Autowired
    private ObjectMapper objectMapper;

    @WithMockUser
    @Test
    public void getOrdersTest() throws Exception {
        List<OrdersDto> ordersDtoList = List.of(new OrdersDto(1L, "экзамен", 54, 1, 1L, 1L),
                        new OrdersDto(2L, "мололчный", 48, 2, 1L, 1L),
                        new OrdersDto(3L, "хлебный", 36, 2, 1L, 1L));

        when(service.getAll()).thenReturn(ordersDtoList);
        mockMvc.perform(get("/api/v1/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(ordersDtoList.size()))
                .andDo(print());
    }

    @WithMockUser
    @Test
    public void getOrderTest() throws Exception {
        long id = 1L;
        OrdersDto ordersDto = new OrdersDto(id, "экзамен", 54, 1, 1L, 1L);

        when(service.getById(id)).thenReturn(ordersDto);
        mockMvc.perform(get("/api/v1/orders/{id}", id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.passtype").value(ordersDto.getPasstype()))
                .andExpect(jsonPath("$.totalhours").value(ordersDto.getTotalhours()))
                .andExpect(jsonPath("$.semester").value(ordersDto.getSemester()))
                .andDo(print());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void updateOrderTest() throws Exception {
        Long id = 1L;
        OrdersDto ordersDto = new OrdersDto(id, "экзамен", 54, 1, 1L, 1L);
        service.save(ordersDto);
        OrdersDto updateOrdersDto = new OrdersDto(id, "зачет", 36, 2, 1L, 1L);
        when(service.save(any(OrdersDto.class))).thenReturn(updateOrdersDto);
        mockMvc.perform(put("/api/v1/orders/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateOrdersDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.passtype").value(updateOrdersDto.getPasstype()))
                .andExpect(jsonPath("$.totalhours").value(updateOrdersDto.getTotalhours()))
                .andExpect(jsonPath("$.semester").value(updateOrdersDto.getSemester()))
                .andDo(print());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void createOrdersTest() throws Exception {
        OrdersDto ordersDtoResult = new OrdersDto(1L, "экзамен", 54, 2, 1L, 1L);
        //если кто то пропрост сохранить объекта класса OrdersDto.class, то вернем в качестве ответа объект который мы создали(OrdersDtoResult)
        when(service.save(any(OrdersDto.class))).thenReturn(ordersDtoResult);

        OrdersDto ordersDto = new OrdersDto();
        ordersDto.setPasstype("молочный");
        ordersDto.setTotalhours(54);
        ordersDto.setSemester(2);
        ordersDto.setProductidfk(1L);
        ordersDto.setSelleridfk(1L);

        mockMvc.perform(post("/api/v1/orders").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ordersDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ordersDtoResult.getId()))
                .andDo(print());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void deleteTest() throws Exception {
        long id = 1L;
        doNothing().when(service).delete(id);
        mockMvc.perform(delete("/api/v1/orders/{id}", id))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
