package com.aits.mobileprepaid.controller;
import java.util.Map;
import com.aits.mobileprepaid.entity.RegistrationRequest;
import com.aits.mobileprepaid.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    // ✅ Step 1: User submits registration request
    @PostMapping("/request")
    public ResponseEntity<?> requestRegistration(@RequestBody RegistrationRequest req) {
        RegistrationRequest saved = registrationService.saveRequest(req);
        return ResponseEntity.ok(Map.of(
            "message", "Your registration request has been submitted for admin approval.",
            "notifiedAdmin", true
        ));
    }


    // ✅ Step 2: Admin views all pending registration requests
    @GetMapping("/admin/pending")
    public ResponseEntity<List<RegistrationRequest>> getPendingRequests() {
        List<RegistrationRequest> requests = registrationService.getPendingRequests();
        return ResponseEntity.ok(requests);
    }

    // ✅ Step 3: Admin approves a specific request
    @PostMapping("/admin/approve/{id}")
    public ResponseEntity<?> approveRequest(@PathVariable Long id) {
        boolean approved = registrationService.approveRequest(id);
        if (approved) {
            return ResponseEntity.ok("✅ User has been approved and added to the system.");
        } else {
            return ResponseEntity.badRequest().body("❌ Request not found or already approved.");
        }
    }

    // ✅ Step 4: Admin rejects a request
    @DeleteMapping("/admin/reject/{id}")
    public ResponseEntity<?> rejectRequest(@PathVariable Long id) {
        boolean rejected = registrationService.rejectRequest(id);
        if (rejected) {
            return ResponseEntity.ok("🚫 Request has been rejected and removed.");
        } else {
            return ResponseEntity.badRequest().body("❌ Request not found.");
        }
    }
}
	