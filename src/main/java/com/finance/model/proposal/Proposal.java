package com.finance.model.proposal;

import com.finance.model.match.Match;
import com.finance.model.request.Request;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "Loan_Proposal")
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_id")
    private Long proposalId;

    @ManyToOne
    @JoinColumn(name = "fk_request")
    private Request request;

    @Column(name = "status")
    private ProposalStatus status;

    @OneToMany(mappedBy = "proposal")
    @ToString.Exclude
    private List<Match> matches;
}
