package com.finance.model.match;

import com.finance.model.offer.Offer;
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
@Table(name = "LoanMatches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long match_id;

    @ManyToOne
    private Request request;

    @ManyToOne
    private Offer offer;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "status")
    private MatchStatus status;
}
