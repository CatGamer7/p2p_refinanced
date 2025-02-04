package com.finance.controller;

import com.finance.dto.request.FilterDTO;
import com.finance.dto.response.OfferFullDTO;
import com.finance.model.offer.Offer;
import com.finance.dto.request.OfferDTO;
import com.finance.model.user.UserSecurityAdapter;
import com.finance.service.OfferService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.finance.model.user.UserAuthority.USER;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class OfferController {

    @Autowired
    private OfferService service;

    @Autowired
    private ModelMapper modelMapper;

    private int pageSize = 100;

    @GetMapping("/offer")
    public ResponseEntity<Page<OfferFullDTO>> getAll(@RequestParam(value = "page", defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, pageSize,
                Sort.by(new Sort.Order(Sort.Direction.DESC, "createdTimestamp")));

        return new ResponseEntity<>(
                service.list(pageable).map(
                        offer -> modelMapper.map(offer, OfferFullDTO.class)
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/offer/{id}")
    public ResponseEntity<OfferFullDTO> getOne(@PathVariable("id") Long id) {
        Optional<Offer> possibleOffer = service.getOne(id);

        if (possibleOffer.isPresent()) {
            return new ResponseEntity<>(
                    modelMapper.map(possibleOffer.get(), OfferFullDTO.class),
                    HttpStatus.OK
            );
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/offer")
    public ResponseEntity<Page<OfferFullDTO>> filter(@RequestParam(value = "page", defaultValue = "0") int page,
                                                     @RequestBody List<FilterDTO> filters) {
        Pageable pageable = PageRequest.of(page, pageSize,
                Sort.by(new Sort.Order(Sort.Direction.DESC, "createdTimestamp")));

        return new ResponseEntity<>(
                service.list(filters, pageable).map(
                        offer -> modelMapper.map(offer, OfferFullDTO.class)
                ),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/offer/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id,
                                       @AuthenticationPrincipal UserSecurityAdapter user) {
        Optional<Offer> possibleOffer = service.getOne(id);

        if (possibleOffer.isPresent()) {

            //Only resource owner and staff can manipulate
            if ((user.id != possibleOffer.get().getLender().getUserId())
                    && (user.getAuthorities().contains(USER))) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            service.delete(possibleOffer.get());

            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/offer")
    public ResponseEntity create(@RequestBody OfferDTO offerDto,
                                 @AuthenticationPrincipal UserSecurityAdapter user) {
        Offer newOffer = modelMapper.map(offerDto, Offer.class);

        try {
            service.setUser(newOffer, offerDto.getLenderId());
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
        }

        //If id was passed - try to retrieve
        if ((newOffer.getOfferId() != null)) {
            Optional<Offer> possibleOffer = service.getOne(newOffer.getOfferId());

            //If object exists - update
            if (possibleOffer.isPresent()) {

                //Only resource owner can manipulate
                if (user.id != possibleOffer.get().getLender().getUserId()) {
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }

                Offer updated = possibleOffer.get();
                updated.setFields(newOffer);
                service.save(updated);

                return new ResponseEntity<>(offerDto, HttpStatus.OK);
            }
        }

        //Cannot create as someone else
        if ((user.id != newOffer.getLender().getUserId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        //Else - create
        newOffer = service.save(newOffer);
        offerDto = modelMapper.map(newOffer, OfferDTO.class);

        return new ResponseEntity<>(offerDto, HttpStatus.OK);
    }

}
