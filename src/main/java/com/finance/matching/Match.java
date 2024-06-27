package com.finance.matching;

import com.finance.offer.Offer;
import com.finance.request.Request;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LoanMatches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long match_id;

    @Column(name = "request_id")
    @ManyToOne
    private Request request;

    @Column(name = "offer_id")
    @ManyToOne
    private Offer offer;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "status")
    private MatchStatus status;
}
