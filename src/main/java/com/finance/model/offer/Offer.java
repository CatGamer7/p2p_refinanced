package com.finance.model.offer;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "LoanOffers")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long offer_id;

    @Column(name = "lender_id")
    private Long lender_id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "interest_rate")
    private BigDecimal interest_rate;

    @Column(name = "status")
    private OfferStatus status;

    @Column(name = "duration_days")
    private Long duration_days;

    public void setFields(Offer in) {
        lender_id = in.getOffer_id();
        amount = in.getAmount();
        interest_rate = in.getInterest_rate();
        status = in.getStatus();
        duration_days = in.getDuration_days();
    }
}
