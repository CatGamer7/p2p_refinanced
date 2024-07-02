package com.finance.controller;

import com.finance.dto.RequestDTO;
import com.finance.model.request.Request;
import com.finance.service.RequestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/request")
    public ResponseEntity<List<RequestDTO>> getAll() {
        return new ResponseEntity<>(
                service.list()
                        .stream()
                        .map(request -> modelMapper.map(request, RequestDTO.class))
                        .collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    @GetMapping("/request/{id}")
    public ResponseEntity<RequestDTO> getOne(@PathVariable("id") Long id) {
        Optional<Request> possibleRequest = service.getOne(id);

        if (possibleRequest.isPresent()) {
            return new ResponseEntity<>(
                    modelMapper.map(possibleRequest.get(), RequestDTO.class),
                    HttpStatus.OK
            );
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/request/{id}")
    public ResponseEntity<RequestDTO> delete(@PathVariable("id") Long id) {
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
