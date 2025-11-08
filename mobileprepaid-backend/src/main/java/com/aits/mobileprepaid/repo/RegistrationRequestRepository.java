package com.aits.mobileprepaid.repo;

import com.aits.mobileprepaid.entity.RegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest, Long> {

    // ✅ Add this line — Spring Data JPA automatically implements it
    List<RegistrationRequest> findByApprovedFalse();

    // Optional: for admin dashboard stats
    long countByApprovedFalse();
}
