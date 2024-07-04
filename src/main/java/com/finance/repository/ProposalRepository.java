package com.finance.repository;

import com.finance.model.proposal.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProposalRepository extends JpaRepository<Proposal, Long>, JpaSpecificationExecutor<Proposal> {
}
