package com.finance.service.interfaces;

import com.finance.dto.request.FilterDTO;
import com.finance.model.proposal.Proposal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProposalServiceInterface {
    public Page<Proposal> list(List<FilterDTO> filters, Pageable pageable);
    public Optional<Proposal> getOne(Long id);
    public Proposal save(Proposal proposal);
    public void delete(Long id);
}
