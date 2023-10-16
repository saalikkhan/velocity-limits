package com.vault.velocity.limits.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vault.velocity.limits.dto.req.LoadFundsReqDto;
import com.vault.velocity.limits.dto.resp.LoadFundsResDto;
import com.vault.velocity.limits.service.impl.LoadFundsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@WebMvcTest(LoadFundsController.class)
@AutoConfigureMockMvc
class LoadFundsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LoadFundsService loadFundsService;

    @Test
    void testLoadFundsEndpoint() throws Exception {
        LoadFundsReqDto request = new LoadFundsReqDto();
        request.setLoad_amount("$100.00");
        request.setTime(LocalDateTime.now());
        request.setId("12");
        request.setCustomer_id("12");

        LoadFundsResDto response = new LoadFundsResDto();

        Mockito.when(loadFundsService.loadFunds(request)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/funds/load")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> result.getResponse().getContentAsString())
                .andDo(print());
    }

}
