package com.finance.service;

import com.finance.dto.request.FilterDTO;
import com.finance.model.offer.Offer;
import com.finance.model.offer.OfferStatus;
import com.finance.model.request.Request;
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
class OfferServiceTest {
    @Autowired
    private OfferService service;

    @MockBean
    private UserService userService;

    @Test
    void filter() {
        Offer o1 = service.save(
                new Offer(null, null, BigDecimal.valueOf(9000.00), BigDecimal.valueOf(5),
                        OfferStatus.available, 30L, null, null)
        );

        Offer o2 = service.save(
                new Offer(null, null, BigDecimal.valueOf(8000.00), BigDecimal.valueOf(4),
                        OfferStatus.matched, 60L, null, null)
        );

        Page<Offer> page = service.list(
                Arrays.asList(
                        new FilterDTO("amount", "<=", Arrays.asList("8500.00")),
                        new FilterDTO("durationDays", ">=", Arrays.asList("30")),
                        new FilterDTO("status", "=", Arrays.asList("2"))
                ),
                PageRequest.of(0, 100)
        );

        assertEquals(o2.getAmount().doubleValue(), page.getContent().getFirst().getAmount().doubleValue());
        assertEquals(o2.getDurationDays(), page.getContent().getFirst().getDurationDays());
        assertEquals(o2.getStatus(), page.getContent().getFirst().getStatus());
        assertEquals(o2.getOfferId(), page.getContent().getFirst().getOfferId());

        service.delete(o1.getOfferId());
        service.delete(o2.getOfferId());
    }

    @Test
    @Transactional
    void delete() {
        Offer o1 = new Offer(null, null, BigDecimal.valueOf(9000.00), BigDecimal.valueOf(5),
                OfferStatus.available, 30L, null, Arrays.asList());

        o1 = service.save(o1);
        Offer o2 = service.getOne(o1.getOfferId()).get();

        assertEquals(o1.getMatches().size(), o2.getMatches().size());

        service.delete(o1);
    }

    @Test
    void setUser() {
        Offer o1 = new Offer(null, null, BigDecimal.valueOf(9000.00), BigDecimal.valueOf(5),
                OfferStatus.available, 30L, null, Arrays.asList());

        User u = new User(0L, "name", "email", "password_digest",
                true, true, null, new ArrayList<Request>(), new ArrayList<Offer>());

        given(userService.getOne(0L))
                .willReturn(Optional.of(u)
                );

        service.setUser(o1, 0L);

        assertEquals(o1.getLender().getUserId(), u.getUserId());
        assertEquals(o1.getLender().getName(), u.getName());
        assertEquals(o1.getLender().getEmail(), u.getEmail());
        assertEquals(o1.getLender().isStaff(), u.isStaff());
    }
}