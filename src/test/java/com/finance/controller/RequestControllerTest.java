package com.finance.controller;

import com.finance.dto.request.FilterDTO;
import com.finance.dto.request.RequestDTO;
import com.finance.dto.response.RequestFullDTO;
import com.finance.matching.strategy.sorted.MatchStrategyMinOffers;
import com.finance.model.request.Request;
import com.finance.model.request.RequestStatus;
import com.finance.service.RequestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@WebMvcTest(value = RequestController.class)
@WithMockUser
class RequestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RequestService requestService;

    @MockBean
    private MatchStrategyMinOffers strat;

    @Autowired
    private JacksonTester<RequestDTO> jsonRequest;

    @Autowired
    private JacksonTester<RequestFullDTO> jsonFullRequest;

    @Autowired
    private JacksonTester<Page<RequestFullDTO>> jsonRequestPage;

    @Autowired
    private JacksonTester<List<FilterDTO>> jsonFilter;

    @Test
    void getAll() throws Exception {
        Page<Request> page = new PageImpl<>(
                Arrays.asList(new Request[] {new Request(0L, null, BigDecimal.valueOf(9000.00),
                        "reason", RequestStatus.pending, null, null) } )
        );
        Page<RequestFullDTO> pageDto = new PageImpl<>(
                Arrays.asList(new RequestFullDTO[] {new RequestFullDTO(0L, null,
                        BigDecimal.valueOf(9000.00),"reason", RequestStatus.pending, null) } )
        );

        given(requestService.list(any()))
                .willReturn(page);

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/api/request")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonRequestPage.write(pageDto).getJson()
        );
    }

    @Test
    void getOne() throws Exception {
        given(requestService.getOne(0L))
                .willReturn(Optional.of(new Request(0L, null, BigDecimal.valueOf(9000.00),
                        "reason", RequestStatus.pending, null, null))
                );

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/api/request/0")
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonFullRequest.write(new RequestFullDTO(0L, null, BigDecimal.valueOf(9000.00),
                        "reason", RequestStatus.pending, null)).getJson()
        );
    }

    @Test
    void filter() throws Exception {
        Page<Request> page = new PageImpl<>(
                Arrays.asList(new Request[] {new Request(0L, null, BigDecimal.valueOf(9000.00),
                        "reason", RequestStatus.pending, null, null) } )
        );
        Page<RequestFullDTO> pageDto = new PageImpl<>(
                Arrays.asList(new RequestFullDTO[] {new RequestFullDTO(0L, null, BigDecimal.valueOf(9000.00),
                        "reason", RequestStatus.pending, null) } )
        );

        List<FilterDTO> filters = Arrays.asList(new FilterDTO("requestId", "=", Arrays.asList( new String[] {"0"} )));

        String payload = jsonFilter.write(filters).getJson();

        given(requestService.list(eq(filters), any()))
                .willReturn(page);

        // when
        MockHttpServletResponse response = mvc.perform(
                post("/api/request")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload)
                        .with(csrf())
        ).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonRequestPage.write(pageDto).getJson()
        );
    }

    @Test
    void deleteT() throws Exception {
        given(requestService.getOne(0L))
                .willReturn(Optional.of(new Request(0L, null, BigDecimal.valueOf(9000.00),
                        "reason", RequestStatus.pending, null, null))
                );

        // when
        MockHttpServletResponse response = mvc.perform(
                delete("/api/request/0")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        ).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void create() throws Exception {
        given(requestService.getOne(0L))
                .willReturn(Optional.of(new Request(0L, null, BigDecimal.valueOf(9000.00),
                        "reason", RequestStatus.pending, null, null))
                );

        RequestDTO request = new RequestDTO(0L, null, BigDecimal.valueOf(10000.00),
                "reason 2", RequestStatus.pending);

        String payload = jsonRequest.write(request).getJson();

        // when
        MockHttpServletResponse response = mvc.perform(
                put("/api/request")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload)
                        .with(csrf())
        ).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonRequest.write(request).getJson()
        );
    }
}