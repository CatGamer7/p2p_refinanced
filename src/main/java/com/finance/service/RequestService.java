package com.finance.service;

import com.finance.model.request.Request;
import com.finance.repository.RequestRepository;
import com.finance.service.interfaces.RequestServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService implements RequestServiceInterface {

    @Autowired
    private RequestRepository repository;

    @Override
    public List<Request> list() {
        return repository.findAll();
    }

    @Override
    public Optional<Request> getOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public Request save(Request request) {
        return repository.save(request);
    }
}
