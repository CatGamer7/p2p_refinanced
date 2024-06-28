package com.finance.controller;

import com.finance.model.offer.Offer;
import com.finance.dto.OfferDTO;
import com.finance.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class OfferController {

    @Autowired
    OfferService service;

    @GetMapping("/offer")
    public ResponseEntity<List<Offer>> getAll() {
        return new ResponseEntity<>(service.list(), HttpStatus.OK);
    }

    @GetMapping("/offer/{id}")
    public ResponseEntity<Offer> getOne(@PathVariable("id") Long id) {
        Optional<Offer> possibleOffer = service.getOne(id);

        if (possibleOffer.isPresent()) {
            return new ResponseEntity<>(possibleOffer.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/offer")
    public ResponseEntity<OfferDTO> create(@RequestBody OfferDTO offerDto) {
        Offer newOffer = offerDto.toOffer();

        //If id was passed - try to retrieve
        if ((newOffer.getOffer_id() != null)) {
            Optional<Offer> possibleOffer = service.getOne(newOffer.getOffer_id());

            //If object exists - update
            if (possibleOffer.isPresent()) {
                Offer updated = possibleOffer.get();
                updated.setFields(newOffer);
                service.save(updated);

                return new ResponseEntity<>(offerDto, HttpStatus.OK);
            }
        }

        //Else - create
        newOffer = service.save(newOffer);
        offerDto.setId(newOffer.getOffer_id());

        return new ResponseEntity<>(offerDto, HttpStatus.OK);
    }

}
