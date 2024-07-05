package com.finance.controller;

import com.finance.dto.request.FilterDTO;
import com.finance.dto.request.RequestDTO;
import com.finance.dto.response.RequestFullDTO;
import com.finance.matching.strategy.sorted.*;
import com.finance.model.request.Request;
import com.finance.service.RequestService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.finance.service.RequestService.oldestFirst;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RequestController {

    @Autowired
    private RequestService service;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    MatchStrategyMinOffers strat = new MatchStrategyMinOffers();

    private int pageSize = 100;

    @GetMapping("/request")
    public ResponseEntity<Page<RequestFullDTO>> getAll(@RequestParam(value = "page", defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, pageSize);

        return new ResponseEntity<>(
                service.list(pageable).map(
                        request -> modelMapper.map(request, RequestFullDTO.class)
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/request/{id}")
    public ResponseEntity<RequestFullDTO> getOne(@PathVariable("id") Long id) {
        Optional<Request> possibleRequest = service.getOne(id);

        if (possibleRequest.isPresent()) {
            return new ResponseEntity<>(
                    modelMapper.map(possibleRequest.get(), RequestFullDTO.class),
                    HttpStatus.OK
            );
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/request")
    public ResponseEntity<Page<RequestFullDTO>> filter(@RequestParam(value = "page", defaultValue = "0") int page,
                                                     @RequestBody List<FilterDTO> filters) {
        Pageable pageable = PageRequest.of(page, pageSize);

        return new ResponseEntity<>(
                service.list(filters, pageable).map(
                        offer -> modelMapper.map(offer, RequestFullDTO.class)
                ),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/request/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Optional<Request> possibleRequest = service.getOne(id);

        if (possibleRequest.isPresent()) {
            service.delete(possibleRequest.get());

            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/request")
    public ResponseEntity create(@RequestBody RequestDTO requestDto) {
        Request newRequest = modelMapper.map(requestDto, Request.class);

        try {
            service.setUser(newRequest, requestDto.getBorrowerId());
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
        }

        //If id was passed - try to retrieve
        if ((newRequest.getRequestId() != null)) {
            Optional<Request> possibleRequest = service.getOne(newRequest.getRequestId());

            //If object exists - update
            if (possibleRequest.isPresent()) {
                Request updated = possibleRequest.get();
                updated.setFields(newRequest);
                service.save(updated);

                return new ResponseEntity<>(requestDto, HttpStatus.OK);
            }
        }

        //Else - create
        newRequest = service.save(newRequest);
        requestDto = modelMapper.map(newRequest, RequestDTO.class);

        return new ResponseEntity<>(requestDto, HttpStatus.OK);
    }


    @PostMapping("/request/test-run")
    public ResponseEntity<Void> testRun() {
        List<Request> requests = service.list(service.specificationAvailable(), oldestFirst);

        for (Request r : requests) {
            strat.matchRequest(r);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
