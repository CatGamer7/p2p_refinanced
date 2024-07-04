package com.finance.service;

import com.finance.model.match.Match;
import com.finance.model.offer.Offer;
import com.finance.model.offer.OfferStatus;
import com.finance.model.proposal.Proposal;
import com.finance.model.request.Request;
import com.finance.model.request.RequestStatus;
import com.finance.repository.OfferRepository;
import com.finance.repository.ProposalRepository;
import com.finance.repository.RequestRepository;
import com.finance.service.interfaces.ProposalServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProposalService implements ProposalServiceInterface {

    @Autowired
    private ProposalRepository repository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Override
    public Proposal save(Proposal proposal) {

        Request r = proposal.getRequest();
        r.setStatus(RequestStatus.matched);
        requestRepository.save(r);

        List<Match> matches = proposal.getMatches();
        for (Match m : matches) {
            Offer o = m.getOffer();
            o.setStatus(OfferStatus.matched);
            offerRepository.save(o);
        }

        return repository.save(proposal);
    }
}
