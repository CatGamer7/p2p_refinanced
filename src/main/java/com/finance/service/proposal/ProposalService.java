package com.finance.service.proposal;

import com.finance.dto.request.FilterDTO;
import com.finance.model.offer.OfferStatus;
import com.finance.model.proposal.Proposal;
import com.finance.model.request.Request;
import com.finance.model.offer.Offer;
import com.finance.model.match.Match;
import com.finance.model.request.RequestStatus;
import com.finance.repository.MatchRepository;
import com.finance.repository.OfferRepository;
import com.finance.repository.ProposalRepository;
import com.finance.repository.RequestRepository;
import com.finance.service.interfaces.ProposalServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@EnableScheduling
public class ProposalService implements ProposalServiceInterface {

    @Autowired
    private ProposalRepository repository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private ProposalSpecification spec;

    @Override
    public Page<Proposal> list(List<FilterDTO> filters, Pageable pageable) {
        Specification<Proposal> specs = spec.applyFilters(filters);
        return repository.findAll(specs, pageable);
    }

    @Override
    public Optional<Proposal> getOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public Proposal save(Proposal proposal) {
        proposal = repository.save(proposal);

        Request r = proposal.getRequest();
        r.setStatus(RequestStatus.matched);
        requestRepository.save(r);

        List<Match> matches = proposal.getMatches();
        for (Match m : matches) {
            Offer o = m.getOffer();
            o.setStatus(OfferStatus.matched);
            o = offerRepository.save(o);

            m = matchRepository.save(m);
        }

        return proposal;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Proposal> optP = getOne(id);

        if (optP.isEmpty()) {
            return;
        }
        Proposal p = optP.get();

        Request r = p.getRequest();
        r.setStatus(RequestStatus.pending);
        requestRepository.save(r);

        List<Match> matches = p.getMatches();
        p.setMatches(null);
        p = repository.save(p);
        for (Match m : matches) {
            Offer o = m.getOffer();
            o.setStatus(OfferStatus.available);
            offerRepository.save(o);

            m.setProposal(null);
            m = matchRepository.save(m);
            matchRepository.deleteById(m.getMatchId());
        }

        repository.deleteById(p.getProposalId());
    }
}
