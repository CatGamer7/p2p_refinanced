package com.finance.model.match;

import com.finance.model.offer.Offer;
import com.finance.model.proposal.Proposal;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "Loan_Matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_id")
    private Long matchId;

    @ManyToOne
    @JoinColumn(name = "fk_offer")
    private Offer offer;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "status")
    private MatchStatus status;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "fk_proposal")
    private Proposal proposal;

    @CreationTimestamp
    @Column(name="created_timestamp")
    private LocalDateTime createdTimestamp = null;
}
