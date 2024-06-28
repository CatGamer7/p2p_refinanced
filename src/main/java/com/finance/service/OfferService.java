package com.finance.service;

import com.finance.model.offer.Offer;
import com.finance.repository.OfferRepository;
import com.finance.service.interfaces.OfferServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferService implements OfferServiceInterface {

    @Autowired
    private OfferRepository repository;

    public List<Offer> list() {
        return repository.findAll();
    }

    public Optional<Offer> getOne(Long id) {
        return repository.findById(id);
    }

    public Offer save(Offer offer) {
        return repository.save(offer);
    }

}
