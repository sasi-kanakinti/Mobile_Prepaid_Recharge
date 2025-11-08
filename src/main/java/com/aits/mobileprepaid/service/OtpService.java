package com.aits.mobileprepaid.service;

import com.aits.mobileprepaid.entity.PasswordResetToken;
import com.aits.mobileprepaid.repo.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private PasswordResetTokenRepository otpRepo;

    // Generate 6-digit OTP
    public String generateAndStoreOtp(String email) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(5);

        otpRepo.deleteByEmail(email); // ensure old OTPs are cleared
        PasswordResetToken token = new PasswordResetToken(email, otp, expiry);
        otpRepo.save(token);

        return otp;
    }

    public boolean validateOtp(String email, String otp) {
        return otpRepo.findByEmail(email)
                .filter(t -> t.getOtp().equals(otp) && t.getExpiryTime().isAfter(LocalDateTime.now()))
                .isPresent();
    }

    public void deleteOtp(String email) {
        otpRepo.deleteByEmail(email);
    }
}
