package com.finance.service.interfaces;

import com.finance.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {
    public List<User> list();
    public Optional<User> getOne(Long id);
    public User save(User user);
    public void delete(Long id);
}
