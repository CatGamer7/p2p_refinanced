package com.finance.service;

import com.finance.dto.request.FilterDTO;
import com.finance.model.user.User;
import com.finance.model.user.UserAuthority;
import com.finance.repository.UserRepository;
import com.finance.service.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceInterface, UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private SpecificationHelper<User> spec;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public User register(User user) {
        user.setPasswordDigest(passwordEncoder.encode(user.getPasswordDigest()));
        return repository.save(user);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = repository.findByEmail(username);
        return new org.springframework.security.core.userdetails.User(
                u.getEmail(),
                u.getPasswordDigest(),
                Arrays.asList(new UserAuthority(u.isStaff()))
        );
    }
}
