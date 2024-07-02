package com.finance.controller;

import com.finance.model.offer.Offer;
import com.finance.dto.OfferDTO;
import com.finance.service.OfferService;
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
public class OfferController {

    @Autowired
    private OfferService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/offer")
    public ResponseEntity<List<OfferDTO>> getAll() {
        return new ResponseEntity<>(
                service.list()
                        .stream()
                        .map(offer -> modelMapper.map(offer, OfferDTO.class))
                        .collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    @GetMapping("/offer/{id}")
    public ResponseEntity<OfferDTO> getOne(@PathVariable("id") Long id) {
        Optional<Offer> possibleOffer = service.getOne(id);

        if (possibleOffer.isPresent()) {
            return new ResponseEntity<>(
                    modelMapper.map(possibleOffer.get(), OfferDTO.class),
                    HttpStatus.OK
            );
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/offer/{id}")
    public ResponseEntity<OfferDTO> delete(@PathVariable("id") Long id) {
        Optional<Offer> possibleOffer = service.getOne(id);

        if (possibleOffer.isPresent()) {
            service.delete(id);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/offer")
    public ResponseEntity<OfferDTO> create(@RequestBody OfferDTO offerDto) {
        Offer newOffer = modelMapper.map(offerDto, Offer.class);

        //If id was passed - try to retrieve
        if ((newOffer.getOfferId() != null)) {
            Optional<Offer> possibleOffer = service.getOne(newOffer.getOfferId());

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
        offerDto = modelMapper.map(newOffer, OfferDTO.class);

        return new ResponseEntity<>(offerDto, HttpStatus.OK);
    }

}
