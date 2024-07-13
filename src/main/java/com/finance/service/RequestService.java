package com.finance.service;

import com.finance.dto.request.FilterDTO;
import com.finance.model.proposal.Proposal;
import com.finance.model.request.Request;
import com.finance.model.request.RequestStatus;
import com.finance.model.user.User;
import com.finance.repository.RequestRepository;
import com.finance.service.interfaces.RequestServiceInterface;
import com.finance.service.proposal.ProposalService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class RequestService implements RequestServiceInterface {

    @Autowired
    private RequestRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProposalService proposalService;

    @Autowired
    private SpecificationHelper<Request> spec;

    public static List<Sort.Order> oldestFirst = Arrays.asList(new Sort.Order[] {
            new Sort.Order(Sort.Direction.ASC, "createdTimestamp")
    });

    public static Specification<Request> specificationAvailable() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("status"),
                        RequestStatus.pending
                );
    }

    @Override
    public List<Request> list() {
        return repository.findAll();
    }

    @Override
    public Page<Request> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Request> list(List<FilterDTO> filters, Pageable pageable) {
        Specification<Request> specs = spec.applyFilters(filters);
        return repository.findAll(specs, pageable);
    }

    @Override
    public List<Request> list(Specification<Request> spec, List<Sort.Order> orders) {
        return repository.findAll(spec, Sort.by(orders));
    }

    @Override
    public Optional<Request> getOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public Request save(Request request) {
        return repository.save(request);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(Request request) {
        for (Proposal p : request.getProposals()) {
            proposalService.delete(p.getProposalId());
        }

        repository.delete(request);
    }

    @Override
    public void setUser(Request request, Long userId) {
        Optional<User> borrower = userService.getOne(userId);

        if (borrower.isPresent()) {
            request.setBorrower(borrower.get());
        }
        else {
            throw new EntityNotFoundException("No user found by specified id");
        }
    }
}
