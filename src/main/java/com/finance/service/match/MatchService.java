package com.finance.service.match;

import com.finance.dto.request.FilterDTO;
import com.finance.model.match.Match;
import com.finance.model.proposal.Proposal;
import com.finance.repository.MatchRepository;
import com.finance.service.interfaces.MatchServiceInterface;
import com.finance.service.proposal.ProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchService implements MatchServiceInterface {

    @Autowired
    private MatchRepository repository;

    @Autowired
    private MatchSpecification spec;

    @Autowired
    private ProposalService proposalService;

    @Override
    public Page<Match> list(List<FilterDTO> filters, Pageable pageable) {
        Specification<Match> specs = spec.applyFilters(filters);
        return repository.findAll(specs, pageable);
    }

    @Override
    public Optional<Match> getOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public Match save(Match match) {
        return repository.save(match);
    }

    @Override
    public void delete(Long id) {
        Optional<Match> optP = getOne(id);

        if (optP.isEmpty()) {
            return;
        }
        Proposal p = optP.get().getProposal();
        proposalService.delete(p.getProposalId());
    }
}
