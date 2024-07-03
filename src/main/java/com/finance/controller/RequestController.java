package com.finance.controller;

import com.finance.dto.request.RequestDTO;
import com.finance.dto.response.OfferFullDTO;
import com.finance.dto.response.RequestFullDTO;
import com.finance.model.request.Request;
import com.finance.service.RequestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class RequestController {

    @Autowired
    private RequestService service;

    @Autowired
    private ModelMapper modelMapper;

    private int pageSize = 2;

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

    @DeleteMapping("/request/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Optional<Request> possibleRequest = service.getOne(id);

        if (possibleRequest.isPresent()) {
            service.delete(id);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/request")
    public ResponseEntity<RequestDTO> create(@RequestBody RequestDTO requestDto) {
        Request newRequest = modelMapper.map(requestDto, Request.class);
        service.setUser(newRequest, requestDto.getBorrowerId());

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
}
