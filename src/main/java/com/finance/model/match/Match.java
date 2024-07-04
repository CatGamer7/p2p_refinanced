package com.finance.model.match;

import com.finance.model.offer.Offer;
import com.finance.model.proposal.Proposal;
import com.finance.model.request.Request;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
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

    @ManyToOne
    @JoinColumn(name = "fk_proposal")
    private Proposal proposal;
}
