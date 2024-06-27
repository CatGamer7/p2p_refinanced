package com.finance.request;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
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
}
