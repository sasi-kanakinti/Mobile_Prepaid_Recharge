package com.aits.mobileprepaid.controller;

import com.aits.mobileprepaid.entity.User;
import com.aits.mobileprepaid.repo.UserRepository;
import com.aits.mobileprepaid.security.JwtUtil;
import com.aits.mobileprepaid.service.EmailService;
import com.aits.mobileprepaid.service.OtpService;
import com.aits.mobileprepaid.util.EmailValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private UserRepository userRepo;
    @Autowired private PasswordEncoder encoder;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private OtpService otpService;
    @Autowired private EmailService emailService;

    private static final String ADMIN_EMAIL = "admin@mobileprepaid.com"; // shown when user doesn't exist

    // ✅ LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        var userOpt = userRepo.findByEmail(email);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404)
                .body(Map.of("message", "You don't belong to this brand. Please contact " + ADMIN_EMAIL + " for registration."));
        }

        User user = userOpt.get();

        if (!encoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(Map.of(
            "token", token,
            "role", user.getRole().name(),
            "userId", user.getId(),
            "name", user.getName(),
            "email", user.getEmail(),
            "mobile", user.getMobile()
        ));
    }

    // ✅ FORGOT PASSWORD — send OTP
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> req) {
        String email = req.get("email");

        // ✅ Step 1: Validate email domain before anything else
        if (!EmailValidator.hasValidMXRecord(email)) {
            return ResponseEntity.status(400).body(Map.of("message", "Invalid or non-existent email domain"));
        }

        var userOpt = userRepo.findByEmail(email);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404)
                .body(Map.of("message", "You don't belong to this brand. Please contact admin@mobileprepaid.com for registration."));
        }

        // ✅ Step 2: Generate and send OTP as before
        String otp = otpService.generateAndStoreOtp(email);
        emailService.sendOtpEmail(email, otp);

        return ResponseEntity.ok(Map.of("message", "OTP sent to your registered email"));
    }


    // ✅ RESET PASSWORD — verify OTP and set new password
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> req) {
        String email = req.get("email");
        String otp = req.get("otp");
        String newPassword = req.get("newPassword");

        if (!otpService.validateOtp(email, otp)) {
            return ResponseEntity.status(400).body(Map.of("message", "Invalid or expired OTP"));
        }

        var userOpt = userRepo.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "User not found"));
        }

        User user = userOpt.get();
        user.setPassword(encoder.encode(newPassword));
        userRepo.save(user);
        otpService.deleteOtp(email);

        return ResponseEntity.ok(Map.of("message", "Password reset successful"));
    }
}
