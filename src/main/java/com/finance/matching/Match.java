package com.finance.matching;

import java.math.BigDecimal;

public class Match {

    public Match(Long match_id, Long request_id, Long offer_id, BigDecimal amount, int status) {
        this.match_id = match_id;
        this.request_id = request_id;
        this.offer_id = offer_id;
        this.amount = amount;
        this.status = status;
    }

    private enum matchStatus {
        created, accepted
    }

    public Long match_id;
    public Long request_id;
    public Long offer_id;
    public BigDecimal amount;
    public int status;
}
