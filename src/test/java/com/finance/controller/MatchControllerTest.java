package com.finance.controller;

import com.finance.dto.request.FilterDTO;
import com.finance.dto.request.MatchStatusDTO;
import com.finance.dto.response.*;
import com.finance.model.match.Match;
import com.finance.model.match.MatchStatus;
import com.finance.model.offer.Offer;
import com.finance.model.offer.OfferStatus;
import com.finance.model.proposal.ProposalStatus;
import com.finance.model.request.RequestStatus;
import com.finance.model.user.User;
import com.finance.security.WithStaffUser;
import com.finance.service.match.MatchService;
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
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureJsonTesters
@WebMvcTest(value = MatchController.class)
@WithStaffUser
class MatchControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MatchService matchService;

    @Autowired
    private JacksonTester<MatchFullDTO> jsonMatch;

    @Autowired
    private JacksonTester<MatchStatusDTO> jsonMatchStatus;

    @Autowired
    private JacksonTester<Page<MatchFullDTO>> jsonMatchPage;

    @Autowired
    private JacksonTester<List<FilterDTO>> jsonFilter;

    @Test
    void filter() throws Exception {
        Page<Match> page = new PageImpl<>(
                Arrays.asList(new Match[] {new Match(0L, null, BigDecimal.valueOf(9000.00),
                        MatchStatus.created, null, null) } )
        );
        Page<MatchFullDTO> pageDto = new PageImpl<>(
                Arrays.asList(new MatchFullDTO[] {new MatchFullDTO(0L, null, BigDecimal.valueOf(9000.00),
                        MatchStatus.created, null, null) } )
        );

        List<FilterDTO> filters = Arrays.asList(new FilterDTO("matchId", "=", Arrays.asList( new String[] {"0"} )));

        String payload = jsonFilter.write(filters).getJson();

        given(matchService.list(eq(filters), any()))
                .willReturn(page);

        // when
        MockHttpServletResponse response = mvc.perform(
                post("/api/match")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload)
                        .with(csrf())
        ).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonMatchPage.write(pageDto).getJson()
        );
    }

    @Test
    void deleteT() throws Exception {
        User u = new User(0L, "name", "email", "digest",
                true, true, null, null, null);

        Offer o = new Offer(0L, u, BigDecimal.valueOf(90000.00),
                BigDecimal.valueOf(5), OfferStatus.available, 91L,null, null);

        given(matchService.getOne(0L))
                .willReturn(Optional.of(new Match(0L, o, BigDecimal.valueOf(9000.00),
                        MatchStatus.created, null, null))
                );

        // when
        MockHttpServletResponse response = mvc.perform(
                delete("/api/match/0")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        ).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void update() throws Exception {
        User u = new User(0L, "name", "email", "digest",
                true, true, null, null, null);

        Offer o = new Offer(0L, u, BigDecimal.valueOf(90000.00),
                BigDecimal.valueOf(5), OfferStatus.available, 91L,null, null);

        given(matchService.getOne(0L))
                .willReturn(Optional.of(new Match(0L, o, BigDecimal.valueOf(9000.00),
                        MatchStatus.created, null, null))
                );

        doAnswer(returnsFirstArg()).when(matchService).save(any());

        MatchStatusDTO statusDTO = new MatchStatusDTO(MatchStatus.accepted);

        String payload = jsonMatchStatus.write(statusDTO).getJson();


        //Expect
        UserDTO uDto = new UserDTO(0L, "name", "email",
                true, true, null);
        OfferFullDTO oDto = new OfferFullDTO(0L, uDto, BigDecimal.valueOf(90000.00),
                BigDecimal.valueOf(5), OfferStatus.available, 91L,null);
        MatchFullDTO updatedDTO = new MatchFullDTO(0L, oDto, BigDecimal.valueOf(9000.00),
                MatchStatus.accepted, null, null);
        //

        // when
        MockHttpServletResponse response = mvc.perform(
                patch("/api/match/0")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload)
                        .with(csrf())
        ).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonMatch.write(updatedDTO).getJson()
        );
    }

}