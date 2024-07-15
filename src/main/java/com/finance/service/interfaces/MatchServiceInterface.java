package com.finance.service.interfaces;

import com.finance.dto.request.FilterDTO;
import com.finance.model.match.Match;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MatchServiceInterface {
    public Page<Match> list(List<FilterDTO> filters, Pageable pageable);
    public Optional<Match> getOne(Long id);
    public Match save(Match match);
    public void delete(Long id);
}
