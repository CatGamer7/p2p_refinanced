package com.finance.model.proposal;

import com.finance.model.match.Match;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "LoanProposal")
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long proposal_id;

    @OneToMany
    @ToString.Exclude
    private List<Match> matches;
}
