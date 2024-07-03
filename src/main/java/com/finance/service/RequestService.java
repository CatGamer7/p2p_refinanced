package com.finance.service;

import com.finance.model.request.Request;
import com.finance.model.user.User;
import com.finance.repository.RequestRepository;
import com.finance.service.interfaces.RequestServiceInterface;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService implements RequestServiceInterface {

    @Autowired
    private RequestRepository repository;

    @Autowired
    private UserService userService;

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

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
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
