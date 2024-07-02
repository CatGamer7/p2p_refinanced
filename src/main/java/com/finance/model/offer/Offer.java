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
@Table(name = "Loan_Offers")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_id")
    private Long offerId;

    @Column(name = "lender_id")
    private Long lenderId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @Column(name = "status")
    private OfferStatus status;

    @Column(name = "duration_days")
    private Long durationDays;

    public void setFields(Offer in) {
        lenderId = in.getLenderId();
        amount = in.getAmount();
        interestRate = in.getInterestRate();
        status = in.getStatus();
        durationDays = in.getDurationDays();
    }
}
