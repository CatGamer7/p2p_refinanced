package com.finance.repository;

import com.finance.model.request.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository  extends JpaRepository<Request, Long> {

}
