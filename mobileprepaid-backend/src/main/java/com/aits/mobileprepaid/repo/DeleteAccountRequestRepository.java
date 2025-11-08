package com.aits.mobileprepaid.repo;

import com.aits.mobileprepaid.entity.DeleteAccountRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeleteAccountRequestRepository extends JpaRepository<DeleteAccountRequest, Long> {
    List<DeleteAccountRequest> findByProcessedFalse();
}
