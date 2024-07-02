package com.finance.controller;

import com.finance.dto.UserDTO;
import com.finance.dto.UserFullDTO;
import com.finance.model.user.User;
import com.finance.service.UserService;
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
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/user")
    public ResponseEntity<List<UserDTO>> getAll() {
        return new ResponseEntity<>(
                service.list()
                        .stream()
                        .map(user -> modelMapper.map(user, UserDTO.class))
                        .collect(Collectors.toList()),
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
    public ResponseEntity<UserDTO> delete(@PathVariable("id") Long id) {
        Optional<User> possibleUser = service.getOne(id);

        if (possibleUser.isPresent()) {
            service.delete(id);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
