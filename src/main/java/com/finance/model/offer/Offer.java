package com.finance.model.offer;

import com.finance.model.match.Match;
import com.finance.model.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "Loan_Offers")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_id")
    private Long offerId;

    @ManyToOne
    @JoinColumn(name = "fk_lender_id")
    private User lender;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @Column(name = "status")
    private OfferStatus status;

    @Column(name = "duration_days")
    private Long durationDays;

    @CreationTimestamp
    @Column(name="created_timestamp")
    private LocalDateTime createdTimestamp;

    @OneToMany(mappedBy = "offer")
    private List<Match> matches;

    public void setFields(Offer in) {
        lender = in.getLender();
        amount = in.getAmount();
        interestRate = in.getInterestRate();
        status = in.getStatus();
        durationDays = in.getDurationDays();
    }
}
