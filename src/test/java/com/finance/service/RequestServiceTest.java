package com.finance.service;

import com.finance.dto.request.FilterDTO;
import com.finance.model.offer.Offer;
import com.finance.model.request.Request;
import com.finance.model.request.RequestStatus;
import com.finance.model.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class RequestServiceTest {
    @Autowired
    private RequestService service;

    @Autowired
    private UserService userService;

    @Test
    void filter() {
        User u = userService.save(new User(null, "name", "email", "digest", false,
                true, null, null, null));

        Request r1 = service.save(
                new Request(null, u, BigDecimal.valueOf(9000.00), "reason",
                        RequestStatus.pending, null, null)
        );

        Request r2 = service.save(
                new Request(null, u, BigDecimal.valueOf(8000.00), "reason 2",
                        RequestStatus.matched, null, null)
        );

        Page<Request> page = service.list(
                Arrays.asList(
                        new FilterDTO("requestedAmount", "<=", Arrays.asList("8500.00")),
                        new FilterDTO("reason", "=", Arrays.asList("reason 2")),
                        new FilterDTO("status", "=", Arrays.asList("2"))
                ),
                PageRequest.of(0, 100)
        );

        assertEquals(r2.getRequestedAmount().doubleValue(), page.getContent().getFirst().getRequestedAmount().doubleValue());
        assertEquals(r2.getReason(), page.getContent().getFirst().getReason());
        assertEquals(r2.getStatus(), page.getContent().getFirst().getStatus());
        assertEquals(r2.getRequestId(), page.getContent().getFirst().getRequestId());

        service.delete(r1.getRequestId());
        service.delete(r2.getRequestId());

        userService.delete(u.getUserId());
    }

    @Test
    @Transactional
    void delete() {
        User u = userService.save(new User(null, "name", "setuser@email", "digest", false,
                true, null, null, null));

        Request r1 = service.save(
                new Request(null, u, BigDecimal.valueOf(9000.00), "reason",
                        RequestStatus.pending, null, Arrays.asList())
        );

        r1 = service.save(r1);
        Request r2 = service.getOne(r1.getRequestId()).get();

        assertEquals(r1.getProposals().size(), r2.getProposals().size());

        service.delete(r1);
        userService.delete(u.getUserId());
    }

    @Test
    void setUser() {
        User u = userService.save(new User(null, "name", "setuser@email", "digest", false,
                true, null, null, null));

        Request r1 = service.save(
                new Request(null, u, BigDecimal.valueOf(9000.00), "reason",
                        RequestStatus.pending, null, Arrays.asList())
        );

        service.setUser(r1, u.getUserId());

        assertEquals(r1.getBorrower().getUserId(), u.getUserId());
        assertEquals(r1.getBorrower().getName(), u.getName());
        assertEquals(r1.getBorrower().getEmail(), u.getEmail());
        assertEquals(r1.getBorrower().isStaff(), u.isStaff());

        service.delete(r1.getRequestId());
        userService.delete(u.getUserId());
    }
}