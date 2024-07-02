package com.finance.service.interfaces;

import com.finance.model.request.Request;

import java.util.List;
import java.util.Optional;

public interface RequestServiceInterface {
    public List<Request> list();
    public Optional<Request> getOne(Long id);
    public Request save(Request request);
    public void delete(Long id);
}
