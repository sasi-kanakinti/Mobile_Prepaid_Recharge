package com.aits.mobileprepaid.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aits.mobileprepaid.dto.RechargeRequest;
import com.aits.mobileprepaid.entity.RechargeHistory;
import com.aits.mobileprepaid.service.RechargeService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/recharge")
public class RechargeController {

    @Autowired
    private RechargeService service;

    // POST: perform recharge
    @SuppressWarnings("null")
	@PostMapping
    public ResponseEntity<String> recharge(@Valid @RequestBody RechargeRequest request) {
        String message = service.recharge(request.getUserId(), request.getPlanId(), request.getPaymentMethod());
        return ResponseEntity.ok(message);
    }

    // GET: fetch recharge history for user
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<RechargeHistory>> getHistory(@PathVariable Long userId) {
        List<RechargeHistory> history = service.getUserHistory(userId);
        return ResponseEntity.ok(history);
    }
}
