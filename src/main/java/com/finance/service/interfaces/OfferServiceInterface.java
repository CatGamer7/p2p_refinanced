package com.finance.service.interfaces;

import com.finance.dto.request.FilterDTO;
import com.finance.model.offer.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface OfferServiceInterface {
    public List<Offer> list();
    public Page<Offer> list(Pageable pageable);
    public Page<Offer> list(List<FilterDTO> filters, Pageable pageable);
    public List<Offer> list(Specification<Offer> specification, List<Sort.Order> orders);
    public Optional<Offer> getOne(Long id);
    public Offer save(Offer offer);
    public void delete(Long id);
    public void delete(Offer offer);
    public void setUser(Offer offer, Long userId);
}
