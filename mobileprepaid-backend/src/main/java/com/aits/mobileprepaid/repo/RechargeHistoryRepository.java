package com.aits.mobileprepaid.repo;

import com.aits.mobileprepaid.entity.RechargeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RechargeHistoryRepository extends JpaRepository<RechargeHistory, Long> {
    List<RechargeHistory> findByUserId(Long userId);
}
