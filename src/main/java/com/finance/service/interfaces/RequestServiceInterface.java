package com.finance.service.interfaces;

import com.finance.dto.request.FilterDTO;
import com.finance.model.request.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface RequestServiceInterface {
    public List<Request> list();
    public Page<Request> list(Pageable pageable);
    public Page<Request> list(List<FilterDTO> filters, Pageable pageable);
    public List<Request> list(Specification<Request> spec, List<Sort.Order> orders);
    public Optional<Request> getOne(Long id);
    public Request save(Request request);
    public void delete(Long id);
    public void delete(Request request);
    public void setUser(Request request, Long userId);
}
