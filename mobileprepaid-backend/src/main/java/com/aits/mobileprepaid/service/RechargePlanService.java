package com.aits.mobileprepaid.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aits.mobileprepaid.entity.RechargePlan;
import com.aits.mobileprepaid.repo.RechargePlanRepository;

@Service
public class RechargePlanService {

    @Autowired
    private RechargePlanRepository planRepo;

    public List<RechargePlan> getAllPlans() {
        return planRepo.findAll();
    }

    public List<RechargePlan> getPlansByCategory(String category) {
        return planRepo.findByCategoryIgnoreCase(category);
    }

    public RechargePlan insert(RechargePlan plan) {
        return planRepo.save(plan);
    }

    public RechargePlan updatePlan(Long id, RechargePlan updated) {
        Optional<RechargePlan> opt = planRepo.findById(id);
        if (opt.isEmpty()) return null;
        RechargePlan plan = opt.get();
        if (updated.getName() != null) plan.setName(updated.getName());
        if (updated.getCategory() != null) plan.setCategory(updated.getCategory());
        // keep primitive types safe
        plan.setPrice(updated.getPrice());
        plan.setValidityInDays(updated.getValidityInDays());
        plan.setDescription(updated.getDescription());
        return planRepo.save(plan);
    }

    public void deletePlan(Long id) {
        planRepo.deleteById(id);
    }
}
