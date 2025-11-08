package com.aits.mobileprepaid.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aits.mobileprepaid.entity.RechargeHistory;
import com.aits.mobileprepaid.entity.RechargePlan;
import com.aits.mobileprepaid.entity.User;
import com.aits.mobileprepaid.repo.RechargeHistoryRepository;
import com.aits.mobileprepaid.repo.RechargePlanRepository;
import com.aits.mobileprepaid.repo.UserRepository;

@Service
public class RechargeService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RechargePlanRepository planRepo;
    @Autowired
    private RechargeHistoryRepository historyRepo;
    @Autowired
    private EmailService emailService;

    @Transactional
    public String recharge(@NonNull Long userId, @NonNull Long planId, String paymentMethod) {
        Optional<User> userOpt = userRepo.findById(userId);
        Optional<RechargePlan> planOpt = planRepo.findById(planId);

        if (userOpt.isEmpty() || planOpt.isEmpty()) {
            return "User or Plan not found.";
        }

        RechargeHistory history = new RechargeHistory();
        history.setUser(userOpt.get());
        history.setPlan(planOpt.get());
        history.setPaymentMethod(paymentMethod);
        history.setRechargeDate(LocalDateTime.now());

        // dummy transaction id and payment status
        String txnId = "TXN" + System.currentTimeMillis();
        history.setTransactionId(txnId);
        history.setPaymentStatus("SUCCESS");

        // expiry = now + plan.validity days
        LocalDateTime expiry = LocalDateTime.now().plusDays(planOpt.get().getValidityInDays());
        history.setExpiryDate(expiry);

        historyRepo.save(history);

        // send confirmation using EmailService (HTML)
        emailService.sendRechargeConfirmation(
            userOpt.get().getEmail(),
            userOpt.get().getName(),
            planOpt.get().getName(),
            planOpt.get().getPrice(),
            planOpt.get().getValidityInDays()
        );

        // optionally, you can also send a short SMS via SmsService if implemented
        return "Recharge successful! Transaction ID: " + txnId;
    }

    public List<RechargeHistory> getUserHistory(Long userId) {
        return historyRepo.findByUserId(userId);
    }
}
