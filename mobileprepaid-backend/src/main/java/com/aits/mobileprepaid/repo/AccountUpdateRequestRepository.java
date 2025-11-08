package com.aits.mobileprepaid.repo;

import com.aits.mobileprepaid.entity.AccountUpdateRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountUpdateRequestRepository extends JpaRepository<AccountUpdateRequest, Long> {
    List<AccountUpdateRequest> findByProcessedFalse();
}
