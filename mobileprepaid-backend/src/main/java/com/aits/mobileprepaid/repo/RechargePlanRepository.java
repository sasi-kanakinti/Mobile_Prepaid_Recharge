package com.aits.mobileprepaid.repo;

import com.aits.mobileprepaid.entity.RechargePlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RechargePlanRepository extends JpaRepository<RechargePlan, Long> {
    List<RechargePlan> findByCategoryIgnoreCase(String category);
}
