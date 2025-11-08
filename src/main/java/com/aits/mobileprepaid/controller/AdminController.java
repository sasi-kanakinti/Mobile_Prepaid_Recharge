package com.aits.mobileprepaid.controller;

import com.aits.mobileprepaid.entity.AccountUpdateRequest;
import com.aits.mobileprepaid.entity.DeleteAccountRequest;
import com.aits.mobileprepaid.entity.RegistrationRequest;
import com.aits.mobileprepaid.entity.User;
import com.aits.mobileprepaid.repo.AccountUpdateRequestRepository;
import com.aits.mobileprepaid.repo.DeleteAccountRequestRepository;
import com.aits.mobileprepaid.repo.RegistrationRequestRepository;
import com.aits.mobileprepaid.repo.UserRepository;
import com.aits.mobileprepaid.repo.RechargeHistoryRepository;
import com.aits.mobileprepaid.repo.RechargePlanRepository;
import com.aits.mobileprepaid.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired private UserRepository userRepo;
    @Autowired private PasswordEncoder encoder;
    @Autowired private EmailService emailService;
    @Autowired private RegistrationRequestRepository registrationRepo;
    @Autowired private DeleteAccountRequestRepository deleteRepo;
    @Autowired private AccountUpdateRequestRepository updateRepo;
    @Autowired private RechargeHistoryRepository historyRepo;
    @Autowired private RechargePlanRepository planRepo;

    // --- Users management ---

    // Get all users (existing)
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepo.findAll());
    }

    // Update user credentials/details (admin)
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updated) {
        Optional<User> opt = userRepo.findById(id);
        if (opt.isEmpty()) return ResponseEntity.badRequest().body("User not found");

        User user = opt.get();
        if (updated.getName() != null) user.setName(updated.getName());
        if (updated.getEmail() != null) user.setEmail(updated.getEmail());
        if (updated.getMobile() != null) user.setMobile(updated.getMobile());
        if (updated.getPassword() != null && !updated.getPassword().isBlank()) {
            user.setPassword(encoder.encode(updated.getPassword()));
        }
        userRepo.save(user);
        return ResponseEntity.ok("User updated successfully");
    }

    // Delete user (admin)
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!userRepo.existsById(id)) return ResponseEntity.badRequest().body("User not found");
        userRepo.deleteById(id);
        return ResponseEntity.ok("User deleted");
    }

    // --- Delete requests view & approve ---
    @GetMapping("/delete-requests")
    public ResponseEntity<List<DeleteAccountRequest>> getDeleteRequests() {
        return ResponseEntity.ok(deleteRepo.findByProcessedFalse());
    }

    @PostMapping("/delete-requests/{id}/approve")
    public ResponseEntity<?> approveDeleteRequest(@PathVariable Long id) {
        Optional<DeleteAccountRequest> opt = deleteRepo.findById(id);
        if (opt.isEmpty()) return ResponseEntity.badRequest().body("Request not found");

        DeleteAccountRequest req = opt.get();
        if (!userRepo.existsById(req.getUserId())) {
            req.setProcessed(true);
            deleteRepo.save(req);
            return ResponseEntity.badRequest().body("User not found — request marked processed");
        }

        userRepo.deleteById(req.getUserId());
        req.setProcessed(true);
        deleteRepo.save(req);

        emailService.sendMail(req.getEmail(),
                "Account Deleted - Mobile Prepaid",
                "Your account has been deleted as per your request.");

        return ResponseEntity.ok("User account deleted successfully");
    }

    @PostMapping("/delete-requests/{id}/reject")
    public ResponseEntity<?> rejectDeleteRequest(@PathVariable Long id) {
        Optional<DeleteAccountRequest> opt = deleteRepo.findById(id);
        if (opt.isEmpty()) return ResponseEntity.badRequest().body("Request not found");
        DeleteAccountRequest req = opt.get();
        req.setProcessed(true);
        deleteRepo.save(req);
        return ResponseEntity.ok("Delete request rejected");
    }

    // --- Account update requests view & approve ---
    @GetMapping("/update-requests")
    public ResponseEntity<List<AccountUpdateRequest>> getUpdateRequests() {
        return ResponseEntity.ok(updateRepo.findByProcessedFalse());
    }

    @PostMapping("/update-requests/{id}/approve")
    public ResponseEntity<?> approveUpdateRequest(@PathVariable Long id) {
        Optional<AccountUpdateRequest> opt = updateRepo.findById(id);
        if (opt.isEmpty()) return ResponseEntity.badRequest().body("Request not found");

        AccountUpdateRequest req = opt.get();
        Optional<User> userOpt = userRepo.findById(req.getUserId());
        if (userOpt.isEmpty()) {
            req.setProcessed(true);
            updateRepo.save(req);
            return ResponseEntity.badRequest().body("User not found — request marked processed");
        }

        User user = userOpt.get();
        if (req.getNewEmail() != null && !req.getNewEmail().isBlank()) user.setEmail(req.getNewEmail());
        if (req.getNewMobile() != null && !req.getNewMobile().isBlank()) user.setMobile(req.getNewMobile());
        userRepo.save(user);

        req.setProcessed(true);
        updateRepo.save(req);

        emailService.sendMail(user.getEmail(),
                "Account Update Approved - Mobile Prepaid",
                "Your account information has been updated by admin.");

        return ResponseEntity.ok("Update applied and user notified");
    }

    @PostMapping("/update-requests/{id}/reject")
    public ResponseEntity<?> rejectUpdateRequest(@PathVariable Long id) {
        Optional<AccountUpdateRequest> opt = updateRepo.findById(id);
        if (opt.isEmpty()) return ResponseEntity.badRequest().body("Request not found");
        AccountUpdateRequest req = opt.get();
        req.setProcessed(true);
        updateRepo.save(req);
        return ResponseEntity.ok("Update request rejected");
    }

    // --- Admin stats for dashboard ---
    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userRepo.count());
        stats.put("pendingRegistrations", registrationRepo.countByApprovedFalse());
        stats.put("pendingDeletes", deleteRepo.findByProcessedFalse().size());
        stats.put("pendingUpdateRequests", updateRepo.findByProcessedFalse().size());
        stats.put("totalPlans", planRepo.count());
        stats.put("totalRecharges", historyRepo.count());
        return ResponseEntity.ok(stats);
    }
}
