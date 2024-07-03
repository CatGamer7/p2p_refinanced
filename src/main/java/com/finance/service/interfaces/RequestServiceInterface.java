package com.finance.service.interfaces;

import com.finance.model.offer.Offer;
import com.finance.model.request.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RequestServiceInterface {
    public List<Request> list();
    public Page<Request> list(Pageable pageable);
    public Optional<Request> getOne(Long id);
    public Request save(Request request);
    public void delete(Long id);
    public void setUser(Request request, Long userId);
}
