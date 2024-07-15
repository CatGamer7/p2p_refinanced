package com.finance.repository;

import com.finance.model.request.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RequestRepository  extends JpaRepository<Request, Long>, JpaSpecificationExecutor<Request> {

}
