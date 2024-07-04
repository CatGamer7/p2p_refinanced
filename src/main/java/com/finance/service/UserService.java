package com.finance.service;

import com.finance.dto.request.FilterDTO;
import com.finance.model.user.User;
import com.finance.repository.UserRepository;
import com.finance.service.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    private UserRepository repository;

    @Autowired
    private SpecificationHelper<User> spec;

    @Override
    public List<User> list() {
        return repository.findAll();
    }

    @Override
    public Page<User> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<User> list(List<FilterDTO> filters, Pageable pageable) {
        Specification<User> specs = spec.applyFilters(filters);
        return repository.findAll(specs, pageable);
    }

    @Override
    public Optional<User> getOne(Long id) {
        return repository.findById(id);
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
