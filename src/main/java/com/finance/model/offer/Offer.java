package com.finance.model.offer;

import com.finance.model.match.Match;
import com.finance.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
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
    @JoinColumn(name = "fk_lender_id", nullable = false)
    private User lender;

    @Column(name = "amount", nullable = false)
    @DecimalMin(value = "0.01", message = "Requested amount must be positive")
    private BigDecimal amount;

    @Column(name = "interest_rate", nullable = false)
    @DecimalMin(value = "0.01", message = "Interest rate must be positive")
    private BigDecimal interestRate;

    @Column(name = "status", nullable = false)
    private OfferStatus status;

    @Column(name = "duration_days")
    @Min(value = 1, message = "Duration must be greater than or equal 1 day")
    private Long durationDays;

    @CreationTimestamp
    @Column(name="created_timestamp")
    private LocalDateTime createdTimestamp = null;

    @OneToMany(mappedBy = "offer")
    private List<Match> matches = null;

    public void setFields(Offer in) {
        amount = in.getAmount();
        interestRate = in.getInterestRate();
        status = in.getStatus();
        durationDays = in.getDurationDays();
    }
}
