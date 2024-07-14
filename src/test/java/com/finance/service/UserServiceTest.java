package com.finance.service;

import com.finance.dto.request.FilterDTO;
import com.finance.model.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService service;

    @Test
    void filter() {
        User u1 = service.save(
                new User(null, "name", "email1@filter.net", "digest",
                        true, true,null, null, null)
        );
        User u2 = service.save(
                new User(null, "name 2", "email2@filter.net", "digest 2",
                        false, false,null, null, null)
        );

        Page<User> page = service.list(
                Arrays.asList(
                        new FilterDTO("name", "=", Arrays.asList("name")),
                        new FilterDTO("staff", "=", Arrays.asList("true"))
                ),
                PageRequest.of(0, 100)
        );

        assertEquals(u1.getName(), page.getContent().getFirst().getName());
        assertEquals(u1.getEmail(), page.getContent().getFirst().getEmail());
        assertEquals(u1.getUserId(), page.getContent().getFirst().getUserId());

        service.delete(u1.getUserId());
        service.delete(u2.getUserId());
    }
}