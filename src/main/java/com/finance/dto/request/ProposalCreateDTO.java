package com.finance.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProposalCreateDTO {
    private OfferDTO offer;
    private Long requestId;
}
