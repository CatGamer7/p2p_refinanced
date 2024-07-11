package com.finance.controller;

import com.finance.dto.request.FilterDTO;
import com.finance.dto.request.ProposalCreateDTO;
import com.finance.dto.request.ProposalStatusDTO;
import com.finance.dto.response.ProposalFullDTO;
import com.finance.model.match.Match;
import com.finance.model.match.MatchStatus;
import com.finance.model.offer.Offer;
import com.finance.model.proposal.Proposal;
import com.finance.model.proposal.ProposalStatus;
import com.finance.model.request.Request;
import com.finance.service.OfferService;
import com.finance.service.RequestService;
import com.finance.service.proposal.ProposalService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ProposalController {

    @Autowired
    private ProposalService service;

    @Autowired
    private OfferService offerService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private ModelMapper modelMapper;

    private int pageSize = 100;

    @PostMapping("/proposal")
    public ResponseEntity<Page<ProposalFullDTO>> filter(@RequestParam(value = "page", defaultValue = "0") int page,
                                                        @RequestBody List<FilterDTO> filters) {
        Pageable pageable = PageRequest.of(page, pageSize,
                Sort.by(new Sort.Order(Sort.Direction.DESC, "createdTimestamp")));

        return new ResponseEntity<>(
                service.list(filters, pageable).map(
                        proposal -> modelMapper.map(proposal, ProposalFullDTO.class)
                ),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/proposal/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Optional<Proposal> possibleProposal = service.getOne(id);

        if (possibleProposal.isPresent()) {
            try {
                service.delete(id);
            }
            catch (Exception e) {
                int a = 1;
            }

            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/proposal/{id}")
    public ResponseEntity<ProposalFullDTO> update(@PathVariable("id") Long id,
                                               @RequestBody ProposalStatusDTO proposalStatusDTO) {
        Optional<Proposal> possibleProposal = service.getOne(id);

        if (possibleProposal.isPresent()) {
            Proposal p = possibleProposal.get();
            p.setStatus(proposalStatusDTO.getStatus());
            p = service.save(p);
            ProposalFullDTO proposalDto = modelMapper.map(p, ProposalFullDTO.class);

            return new ResponseEntity<>(proposalDto, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/proposal")
    public ResponseEntity<ProposalFullDTO> create(@RequestBody ProposalCreateDTO proposalCreateDTO) {
        Optional<Request> rOptional = requestService.getOne(proposalCreateDTO.getRequestId());

        if (rOptional.isPresent()) {
            Offer o = modelMapper.map(proposalCreateDTO.getOffer(), Offer.class);
            o = offerService.save(o);

            Proposal p = null;
            Match m = new Match(null, o, o.getAmount(), MatchStatus.created, p, null);
            p = new Proposal(null, rOptional.get(), ProposalStatus.created, Arrays.asList(m), null);

            p = service.save(p);
            ProposalFullDTO pDto = modelMapper.map(p, ProposalFullDTO.class);

            return new ResponseEntity<>(pDto, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
