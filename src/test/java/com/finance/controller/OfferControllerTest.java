package com.finance.controller;

import com.finance.dto.request.FilterDTO;
import com.finance.dto.request.OfferDTO;
import com.finance.dto.response.OfferFullDTO;
import com.finance.model.offer.Offer;
import com.finance.model.offer.OfferStatus;
import com.finance.service.OfferService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureJsonTesters
@WebMvcTest(value = OfferController.class)
@WithMockUser
class OfferControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OfferService offerService;

    @Autowired
    private JacksonTester<OfferDTO> jsonOffer;

    @Autowired
    private JacksonTester<OfferFullDTO> jsonFullOffer;

    @Autowired
    private JacksonTester<Page<OfferFullDTO>> jsonOfferPage;

    @Autowired
    private JacksonTester<List<FilterDTO>> jsonFilter;

    @Test
    void getAll() throws Exception {
        Page<Offer> page = new PageImpl<>(
                Arrays.asList(new Offer[] {new Offer(0L, null, BigDecimal.valueOf(90000.00),
                        BigDecimal.valueOf(5), OfferStatus.available, 91L,
                        null, null) } )
        );
        Page<OfferFullDTO> pageDto = new PageImpl<>(
                Arrays.asList(new OfferFullDTO[] {new OfferFullDTO(0L, null, BigDecimal.valueOf(90000.00),
                        BigDecimal.valueOf(5), OfferStatus.available, 91L, null) } )
        );

        given(offerService.list(any()))
                .willReturn(page);

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/api/offer")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonOfferPage.write(pageDto).getJson()
        );
    }

    @Test
    void getOne() throws Exception {
        given(offerService.getOne(0L))
                .willReturn(Optional.of(new Offer(0L, null, BigDecimal.valueOf(90000.00),
                        BigDecimal.valueOf(5), OfferStatus.available, 91L,null, null))
                );

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/api/offer/0")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonFullOffer.write(new OfferFullDTO(0L, null, BigDecimal.valueOf(90000.00),
                        BigDecimal.valueOf(5), OfferStatus.available, 91L,null)).getJson()
        );
    }

    @Test
    void filter() throws Exception {
        Page<Offer> page = new PageImpl<>(
                Arrays.asList(new Offer[] {new Offer(0L, null, BigDecimal.valueOf(90000.00),
                        BigDecimal.valueOf(5), OfferStatus.available, 91L,
                        null, null) } )
        );
        Page<OfferFullDTO> pageDto = new PageImpl<>(
                Arrays.asList(new OfferFullDTO[] {new OfferFullDTO(0L, null, BigDecimal.valueOf(90000.00),
                        BigDecimal.valueOf(5), OfferStatus.available, 91L,null) } )
        );

        List<FilterDTO> filters = Arrays.asList(new FilterDTO("offerId", "=", Arrays.asList( new String[] {"0"} )));

        String payload = jsonFilter.write(filters).getJson();

        given(offerService.list(eq(filters), any()))
                .willReturn(page);

        // when
        MockHttpServletResponse response = mvc.perform(
                post("/api/offer")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload)
                        .with(csrf())
        ).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonOfferPage.write(pageDto).getJson()
        );
    }

    @Test
    void deleteT() throws Exception {
        given(offerService.getOne(0L))
                .willReturn(Optional.of(new Offer(0L, null, BigDecimal.valueOf(90000.00),
                        BigDecimal.valueOf(5), OfferStatus.available, 91L,null, null))
                );

        // when
        MockHttpServletResponse response = mvc.perform(
                delete("/api/offer/0")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        ).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void create() throws Exception {
        given(offerService.getOne(0L))
                .willReturn(Optional.of(new Offer(0L, null, BigDecimal.valueOf(90000.00),
                        BigDecimal.valueOf(5), OfferStatus.available, 91L,null, null))
                );

        OfferDTO offer = new OfferDTO(0L, null, BigDecimal.valueOf(90000.00),
                BigDecimal.valueOf(5), OfferStatus.available, 91L);

        String payload = jsonOffer.write(offer).getJson();

        // when
        MockHttpServletResponse response = mvc.perform(
                put("/api/offer")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload)
                        .with(csrf())
        ).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonOffer.write(offer).getJson()
        );
    }
}