package com.finance.matching;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LoanProposal")
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long proposal_id;

    @Column(name = "matches")
    @OneToMany
    private List<Match> matches;
}
