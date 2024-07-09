package com.finance.service.proposal;

import com.finance.dto.request.FilterDTO;
import com.finance.model.match.Match;
import com.finance.model.match.MatchStatus;
import com.finance.model.offer.Offer;
import com.finance.model.offer.OfferStatus;
import com.finance.model.proposal.Proposal;
import com.finance.model.proposal.ProposalStatus;
import com.finance.model.request.Request;
import com.finance.model.request.RequestStatus;
import com.finance.service.OfferService;
import com.finance.service.RequestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProposalServiceTest {

    @Autowired
    private ProposalService service;

    @Autowired
    private RequestService requestService;

    @Autowired
    private OfferService offerService;

    @Test
    @Transactional
    void list() {
        Request r1 = new Request(null, null, BigDecimal.valueOf(9000.00), "reason",
                RequestStatus.pending, null, null);

        r1 = requestService.save(r1);

        Offer o1 = new Offer(null, null, BigDecimal.valueOf(9000.00), BigDecimal.valueOf(5),
                OfferStatus.available, 30L, null, null);

        o1 = offerService.save(o1);

        Match m1 = new Match(null, o1, BigDecimal.valueOf(9000.00),
                MatchStatus.created, null, null);
        Match m2 = new Match(null, o1, BigDecimal.valueOf(9000.00),
                MatchStatus.created, null, null);

        Proposal p1 = service.save(
                new Proposal(null, r1, ProposalStatus.accepted, Arrays.asList(m1), null)
        );
        Proposal p2 = service.save(
                new Proposal(null, r1, ProposalStatus.created, Arrays.asList(m2), null)
        );

        //Save test
        o1 = offerService.getOne(o1.getOfferId()).get();
        r1 = requestService.getOne(r1.getRequestId()).get();

        assertEquals(o1.getStatus(), OfferStatus.matched);
        assertEquals(r1.getStatus(), RequestStatus.matched);
        //

        m1.setProposal(p1);
        m2.setProposal(p2);

        Page<Proposal> page = service.list(
                Arrays.asList(
                        new FilterDTO("requestId", "=", Arrays.asList(r1.getRequestId().toString()))
                ),
                PageRequest.of(0, 100)
        );

        assertEquals(
                p2.getRequest().getRequestId(),
                page.getContent().getFirst().getRequest().getRequestId()
        );
        assertEquals(
                p2.getMatches().getFirst().getOffer().getOfferId(),
                page.getContent().getFirst().getMatches().getFirst().getOffer().getOfferId()
        );

        //Delete test
        service.delete(p1.getProposalId());
        service.delete(p2.getProposalId());

        assertEquals(o1.getStatus(), OfferStatus.available);
        assertEquals(r1.getStatus(), RequestStatus.pending);
        //
    }
}