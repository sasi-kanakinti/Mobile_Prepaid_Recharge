package com.aits.mobileprepaid.controller;

import com.aits.mobileprepaid.entity.AccountUpdateRequest;
import com.aits.mobileprepaid.entity.DeleteAccountRequest;
import com.aits.mobileprepaid.repo.AccountUpdateRequestRepository;
import com.aits.mobileprepaid.repo.DeleteAccountRequestRepository;
import com.aits.mobileprepaid.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Endpoints for users to request update/delete of their account.
 */
@RestController
@RequestMapping("/user/requests")
public class UserRequestController {

    @Autowired
    private DeleteAccountRequestRepository deleteRepo;

    @Autowired
    private AccountUpdateRequestRepository updateRepo;

    @Autowired
    private EmailService emailService;

    // User requests account deletion
    @PostMapping("/delete")
    public ResponseEntity<?> requestDelete(@RequestBody DeleteAccountRequest req) {
        deleteRepo.save(req);

        // notify admin
        String adminEmail = System.getProperty("admin.email", "admin@mobileprepaid.com");
        String subject = "User Delete Account Request";
        String body = "User " + req.getEmail() + " requested account deletion.\nReason: " + req.getReason();
        emailService.sendMail(adminEmail, subject, body);

        return ResponseEntity.ok("Your delete request has been submitted to the admin.");
    }

    // User requests account update (email/mobile)
    @PostMapping("/update")
    public ResponseEntity<?> requestUpdate(@RequestBody AccountUpdateRequest req) {
        updateRepo.save(req);

        // notify admin
        String adminEmail = System.getProperty("admin.email", "admin@mobileprepaid.com");
        String subject = "User Account Update Request";
        String body = "UserID: " + req.getUserId() + " requested update.\nNew Email: " + req.getNewEmail() + "\nNew Mobile: " + req.getNewMobile();
        emailService.sendMail(adminEmail, subject, body);

        return ResponseEntity.ok("Your update request has been submitted to the admin.");
    }
}
