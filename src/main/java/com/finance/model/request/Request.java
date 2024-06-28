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
@Table(name = "LoanRequests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long request_id;

    @Column(name = "borrower_id")
    private Long borrower_id;

    @Column(name = "requested_amount")
    private BigDecimal requested_amount;

    @Column(name = "reason")
    private String reason;

    @Column(name = "status")
    private RequestStatus status;

    public void setFields(Request in) {
        borrower_id = in.getBorrower_id();
        requested_amount = in.getRequested_amount();
        reason = in.getReason();
        status = in.getStatus();
    }
}
