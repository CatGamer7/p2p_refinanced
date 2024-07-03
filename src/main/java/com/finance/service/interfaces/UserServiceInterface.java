package com.finance.service.interfaces;

import com.finance.model.request.Request;
import com.finance.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {
    public List<User> list();
    public Page<User> list(Pageable pageable);
    public Optional<User> getOne(Long id);
    public User save(User user);
    public void delete(Long id);
}
