package com.finance.controller;

import com.finance.dto.request.FilterDTO;
import com.finance.dto.request.MatchStatusDTO;
import com.finance.dto.response.MatchFullDTO;
import com.finance.model.match.Match;
import com.finance.model.user.UserSecurityAdapter;
import com.finance.service.match.MatchService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
public class MatchController {

    @Autowired
    private MatchService service;

    @Autowired
    private ModelMapper modelMapper;

    private int pageSize = 100;

    @PostMapping("/match")
    public ResponseEntity<Page<MatchFullDTO>> filter(@RequestParam(value = "page", defaultValue = "0") int page,
                                                     @RequestBody List<FilterDTO> filters) {
        Pageable pageable = PageRequest.of(page, pageSize,
                Sort.by(new Sort.Order(Sort.Direction.DESC, "createdTimestamp")));

        return new ResponseEntity<>(
                service.list(filters, pageable).map(
                        match -> modelMapper.map(match, MatchFullDTO.class)
                ),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/match/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id,
                                       @AuthenticationPrincipal UserSecurityAdapter user) {
        Optional<Match> possibleMatch = service.getOne(id);

        if (possibleMatch.isPresent()) {

            //Only resource owner  can manipulate
            if (user.id != possibleMatch.get().getOffer().getLender().getUserId()) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            service.delete(id);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/match/{id}")
    public ResponseEntity<MatchFullDTO> update(@PathVariable("id") Long id,
                                               @RequestBody MatchStatusDTO matchStatusDTO,
                                               @AuthenticationPrincipal UserSecurityAdapter user) {
        Optional<Match> possibleMatch = service.getOne(id);

        if (possibleMatch.isPresent()) {

            //Only resource owner  can manipulate
            if (user.id != possibleMatch.get().getOffer().getLender().getUserId()) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            Match m = possibleMatch.get();
            m.setStatus(matchStatusDTO.getStatus());
            m = service.save(m);
            MatchFullDTO matchDto = modelMapper.map(m, MatchFullDTO.class);

            return new ResponseEntity<>(matchDto, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
