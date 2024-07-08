package com.finance.model.request;

import com.finance.model.proposal.Proposal;
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
@Table(name = "Loan_Requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_id")
    private Long requestId;

    @ManyToOne
    @JoinColumn(name = "fk_borrower_id")
    private User borrower;

    @Column(name = "requested_amount")
    private BigDecimal requestedAmount;

    @Column(name = "reason")
    private String reason;

    @Column(name = "status")
    private RequestStatus status;

    @CreationTimestamp
    @Column(name="created_timestamp")
    private LocalDateTime createdTimestamp = null;

    @OneToMany(mappedBy = "request")
    private List<Proposal> proposals = null;

    public void setFields(Request in) {
        borrower = in.getBorrower();
        requestedAmount = in.getRequestedAmount();
        reason = in.getReason();
        status = in.getStatus();
    }
}
