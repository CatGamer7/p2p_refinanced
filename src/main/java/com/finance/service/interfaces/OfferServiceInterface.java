package com.finance.service.interfaces;

import com.finance.model.offer.Offer;

import java.util.List;
import java.util.Optional;

public interface OfferServiceInterface {
    public List<Offer> list();
    public Optional<Offer> getOne(Long id);
    public Offer save(Offer offer);
    public void delete(Long id);
    public void setUser(Offer offer, Long userId);
}
