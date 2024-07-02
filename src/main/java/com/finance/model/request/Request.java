package com.finance.model.request;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "Loan_Requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_id")
    private Long requestId;

    @Column(name = "borrower_id")
    private Long borrowerId;

    @Column(name = "requested_amount")
    private BigDecimal requestedAmount;

    @Column(name = "reason")
    private String reason;

    @Column(name = "status")
    private RequestStatus status;

    public void setFields(Request in) {
        borrowerId = in.getBorrowerId();
        requestedAmount = in.getRequestedAmount();
        reason = in.getReason();
        status = in.getStatus();
    }
}
