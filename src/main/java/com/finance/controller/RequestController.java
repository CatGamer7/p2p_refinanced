package com.finance.controller;

import com.finance.dto.RequestDTO;
import com.finance.model.request.Request;
import com.finance.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RequestController {

    @Autowired
    RequestService service;

    @GetMapping("/request")
    public ResponseEntity<List<Request>> getAll() {
        return new ResponseEntity<>(service.list(), HttpStatus.OK);
    }

    @GetMapping("/request/{id}")
    public ResponseEntity<Request> getOne(@PathVariable("id") Long id) {
        Optional<Request> possibleRequest = service.getOne(id);

        if (possibleRequest.isPresent()) {
            return new ResponseEntity<>(possibleRequest.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/request")
    public ResponseEntity<RequestDTO> create(@RequestBody RequestDTO requestDto) {
        Request newRequest = requestDto.toOffer();

        //If id was passed - try to retrieve
        if ((newRequest.getRequest_id() != null)) {
            Optional<Request> possibleRequest = service.getOne(newRequest.getRequest_id());

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
        requestDto.setId(newRequest.getRequest_id());

        return new ResponseEntity<>(requestDto, HttpStatus.OK);
    }
}
