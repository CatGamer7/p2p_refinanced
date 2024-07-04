package com.finance.service;

import com.finance.dto.request.FilterDTO;
import com.finance.model.offer.Offer;
import com.finance.model.user.User;
import com.finance.repository.OfferRepository;
import com.finance.service.interfaces.OfferServiceInterface;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferService implements OfferServiceInterface {

    @Autowired
    private OfferRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private SpecificationHelper<Offer> spec;

    public List<Offer> list() {
        return repository.findAll();
    }

    @Override
    public Page<Offer> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Offer> list(List<FilterDTO> filters, Pageable pageable) {
        Specification<Offer> specs = spec.applyFilters(filters);
        return repository.findAll(specs, pageable);
    }

    public Optional<Offer> getOne(Long id) {
        return repository.findById(id);
    }

    public Offer save(Offer offer) {
        return repository.save(offer);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void setUser(Offer offer, Long userId) {
        Optional<User> lender = userService.getOne(userId);

        if (lender.isPresent()) {
            offer.setLender(lender.get());
        }
        else {
            throw new EntityNotFoundException("No user found by specified id");
        }
    }




}
