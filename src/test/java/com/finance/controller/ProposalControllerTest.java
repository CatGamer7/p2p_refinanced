package com.finance.controller;

import com.finance.dto.request.FilterDTO;
import com.finance.dto.request.OfferDTO;
import com.finance.dto.request.ProposalCreateDTO;
import com.finance.dto.request.ProposalStatusDTO;
import com.finance.dto.response.*;
import com.finance.model.match.MatchStatus;
import com.finance.model.offer.Offer;
import com.finance.model.offer.OfferStatus;
import com.finance.model.proposal.Proposal;
import com.finance.model.proposal.ProposalStatus;
import com.finance.model.request.Request;
import com.finance.model.request.RequestStatus;
import com.finance.model.user.User;
import com.finance.security.WithStaffUser;
import com.finance.service.OfferService;
import com.finance.service.RequestService;
import com.finance.service.proposal.ProposalService;
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
@WebMvcTest(value = ProposalController.class)
@WithStaffUser
class ProposalControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProposalService proposalService;

    @MockBean
    private OfferService offerService;

    @MockBean
    private RequestService requestService;

    @Autowired
    private JacksonTester<ProposalFullDTO> jsonProposal;

    @Autowired
    private JacksonTester<ProposalStatusDTO> jsonProposalStatus;

    @Autowired
    private JacksonTester<Page<ProposalFullDTO>> jsonProposalPage;

    @Autowired
    private JacksonTester<List<FilterDTO>> jsonFilter;

    @Autowired
    private JacksonTester<ProposalCreateDTO> jsonCreate;

    @Test
    void filter() throws Exception {
        Page<Proposal> page = new PageImpl<>(
                Arrays.asList(new Proposal[] {new Proposal(0L, null, ProposalStatus.created,
                        null, null) } )
        );
        Page<ProposalFullDTO> pageDto = new PageImpl<>(
                Arrays.asList(new ProposalFullDTO[] {new ProposalFullDTO(0L, null,
                        ProposalStatus.created, null, null) } )
        );

        List<FilterDTO> filters = Arrays.asList(new FilterDTO("proposalId", "=", Arrays.asList( new String[] {"0"} )));

        String payload = jsonFilter.write(filters).getJson();

        given(proposalService.list(eq(filters), any()))
                .willReturn(page);

        // when
        MockHttpServletResponse response = mvc.perform(
                post("/api/proposal")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload)
                        .with(csrf())
        ).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonProposalPage.write(pageDto).getJson()
        );
    }

    @Test
    void deleteT() throws Exception {
        User u = new User(0L, "name", "email", "digest",
                true, true, null, null, null);

        Request r = new Request(0L, u, BigDecimal.valueOf(9000.00),
                "reason", RequestStatus.pending, null, null);

        given(proposalService.getOne(0L))
                .willReturn(Optional.of(new Proposal(0L, r, ProposalStatus.created,
                        null, null))
                );

        // when
        MockHttpServletResponse response = mvc.perform(
                delete("/api/proposal/0")
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

        Request r = new Request(0L, u, BigDecimal.valueOf(9000.00),
                "reason", RequestStatus.pending, null, null);

        given(proposalService.getOne(0L))
                .willReturn(Optional.of(new Proposal(0L, r, ProposalStatus.created,
                        null, null))
                );

        doAnswer(returnsFirstArg()).when(proposalService).save(any());

        ProposalStatusDTO statusDTO = new ProposalStatusDTO(ProposalStatus.accepted);

        String payload = jsonProposalStatus.write(statusDTO).getJson();

        //Expect
        UserDTO uDto = new UserDTO(0L, "name", "email",
                true, true, null);
        RequestFullDTO rDto = new RequestFullDTO(0L, uDto, BigDecimal.valueOf(9000.00),
                "reason", RequestStatus.pending, null);
        ProposalFullDTO updatedDTO = new ProposalFullDTO(0L, rDto, ProposalStatus.accepted,
                null, null);
        //

        // when
        MockHttpServletResponse response = mvc.perform(
                patch("/api/proposal/0")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload)
                        .with(csrf())
        ).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonProposal.write(updatedDTO).getJson()
        );
    }

    @Test
    void create() throws Exception {
        User u = new User(0L, "name", "email", "digest",
                true, true, null, null, null);

        Request r = new Request(0L, u, BigDecimal.valueOf(9000.00),
                "reason", RequestStatus.pending, null, null);

        given(requestService.getOne(0L))
                .willReturn(Optional.of(r)
                );

        doAnswer(returnsFirstArg()).when(proposalService).save(any());
        doAnswer(returnsFirstArg()).when(offerService).save(any());
        doAnswer(invocation  -> {
            Offer o = invocation.getArgument(0);
            o.setLender(u);
            return null;
        }).when(offerService).setUser(any(Offer.class), any(Long.class));

        ProposalCreateDTO createDTO = new ProposalCreateDTO(
                new OfferDTO(0L, 0L, BigDecimal.valueOf(9000.00),
                        BigDecimal.valueOf(5), OfferStatus.available, 91L),
                0L
        );

        String payload = jsonCreate.write(createDTO).getJson();

        //Expect
        UserDTO uDTO = new UserDTO(0L, "name", "email",
                true, true, null);
        RequestFullDTO rDto = new RequestFullDTO(0L, uDTO, BigDecimal.valueOf(9000.00),
                "reason", RequestStatus.pending, null);
        OfferFullDTO oDto = new OfferFullDTO(0L, uDTO, BigDecimal.valueOf(9000.00),
                BigDecimal.valueOf(5), OfferStatus.available, 91L, null);
        PrposalBriefDTO pbDto = new PrposalBriefDTO(null, ProposalStatus.created, rDto);
        MatchFullDTO mDto = new MatchFullDTO(null, oDto, BigDecimal.valueOf(9000.00),
                MatchStatus.created, null, pbDto);

        ProposalFullDTO pDto = new ProposalFullDTO(null, rDto, ProposalStatus.created, Arrays.asList(mDto), null);
        //

        // when
        MockHttpServletResponse response = mvc.perform(
                put("/api/proposal")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload)
                        .with(csrf())
        ).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonProposal.write(pDto).getJson()
        );
    }
}