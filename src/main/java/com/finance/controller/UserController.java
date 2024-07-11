package com.finance.controller;

import com.finance.dto.request.FilterDTO;
import com.finance.dto.response.UserDTO;
import com.finance.dto.request.UserFullDTO;
import com.finance.model.offer.Offer;
import com.finance.model.request.Request;
import com.finance.model.user.User;
import com.finance.service.OfferService;
import com.finance.service.RequestService;
import com.finance.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private OfferService offerService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private ModelMapper modelMapper;

    private int pageSize = 100;

    @GetMapping("/user")
    public ResponseEntity<Page<UserDTO>> getAll(@RequestParam(value = "page", defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, pageSize,
                Sort.by(new Sort.Order(Sort.Direction.DESC, "createdTimestamp")));

        return new ResponseEntity<>(
                service.list(pageable).map(
                        user -> modelMapper.map(user, UserDTO.class)
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getOne(@PathVariable("id") Long id) {
        Optional<User> possibleUser = service.getOne(id);

        if (possibleUser.isPresent()) {
            return new ResponseEntity<>(
                    modelMapper.map(possibleUser.get(), UserDTO.class),
                    HttpStatus.OK
            );
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Optional<User> possibleUser = service.getOne(id);

        if (possibleUser.isPresent()) {
            List<Offer> userOffers = possibleUser.get().getOffers();
            List<Request> userRequest = possibleUser.get().getRequests();

            for (Request r : userRequest) {
                requestService.delete(r);
            }
            for (Offer o : userOffers) {
                offerService.delete(o);
            }

            service.delete(id);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/user")
    public ResponseEntity<Page<UserDTO>> filter(@RequestParam(value = "page", defaultValue = "0") int page,
                                                       @RequestBody List<FilterDTO> filters) {
        Pageable pageable = PageRequest.of(page, pageSize,
                Sort.by(new Sort.Order(Sort.Direction.DESC, "createdTimestamp")));

        return new ResponseEntity<>(
                service.list(filters, pageable).map(
                        offer -> modelMapper.map(offer, UserDTO.class)
                ),
                HttpStatus.OK
        );
    }

    @PutMapping("/user")
    public ResponseEntity<UserDTO> create(@RequestBody UserFullDTO userDTO) {
        User newUser = modelMapper.map(userDTO, User.class);

        //If id was passed - try to retrieve
        if ((newUser.getUserId() != null)) {
            Optional<User> possibleUser = service.getOne(newUser.getUserId());

            //If object exists - update
            if (possibleUser.isPresent()) {
                User updated = possibleUser.get();
                updated.setFields(newUser);
                service.save(updated);
                UserDTO response = modelMapper.map(updated, UserDTO.class);

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }

        //Else - create
        newUser = service.save(newUser);
        UserDTO response = modelMapper.map(newUser, UserDTO.class);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
