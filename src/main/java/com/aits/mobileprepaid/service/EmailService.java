package com.aits.mobileprepaid.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // =============== BASIC PLAIN MAIL ==================
    public void sendMail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
            System.out.println("📧 Sent plain email to " + to);
        } catch (Exception e) {
            System.err.println("❌ Failed to send plain email: " + e.getMessage());
        }
    }

    // =============== HTML MAIL ==================
    public void sendHtmlMail(@NonNull String to, @NonNull String subject, @NonNull String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            mailSender.send(message);
            System.out.println("📩 Sent HTML email to " + to);
        } catch (MessagingException e) {
            System.err.println("❌ Failed to send HTML email: " + e.getMessage());
        }
    }

    // =============== SPECIFIC MAIL TYPES ==================

    /** Password Reset OTP */
    @SuppressWarnings("null")
	public void sendOtpEmail(@NonNull String to, String otp) {
        String subject = "Password Reset OTP - Mobile Prepaid";
        String htmlBody = """
            <div style='font-family:Arial,sans-serif;max-width:600px;margin:auto;border:1px solid #ddd;border-radius:10px;padding:20px;background:#f9f9f9;'>
              <h2 style='color:#2563EB;'>Password Reset Request</h2>
              <p>Dear user,</p>
              <p>Your OTP for resetting your password is:</p>
              <h3 style='text-align:center;background:#2563EB;color:#fff;padding:10px;border-radius:6px;'>%s</h3>
              <p>This OTP will expire in <b>5 minutes</b>.</p>
              <p>If you didn’t request this, you can safely ignore this email.</p>
              <hr>
              <p style='font-size:12px;color:#555;text-align:center;'>© Mobile Prepaid 2025</p>
            </div>
            """.formatted(otp);
        sendHtmlMail(to, subject, htmlBody);
    }

    /** Welcome Email (Admin adds new user) */
    @SuppressWarnings("null")
	public void sendWelcomeEmail(@NonNull String to, String name) {
        String subject = "Welcome to Mobile Prepaid!";
        String htmlBody = """
            <div style='font-family:Arial,sans-serif;max-width:600px;margin:auto;border:1px solid #ddd;border-radius:10px;padding:20px;background:#fefefe;'>
              <h2 style='color:#2563EB;'>Welcome, %s 👋</h2>
              <p>We’re excited to have you on board! Your Mobile Prepaid account has been successfully created by our admin.</p>
              <p>You can now log in to your account using your registered email address.</p>
              <p>We hope you enjoy your experience!</p>
              <br>
              <p>Warm regards,<br><b>Mobile Prepaid Team</b></p>
              <hr>
              <p style='font-size:12px;color:#555;text-align:center;'>© Mobile Prepaid 2025</p>
            </div>
            """.formatted(name);
        sendHtmlMail(to, subject, htmlBody);
    }

    /** Recharge Confirmation Email */
    @SuppressWarnings("null")
	public void sendRechargeConfirmation(@NonNull String to, String name, String planName, double price, int validityDays) {
        String subject = "Recharge Successful - Mobile Prepaid";
        String htmlBody = """
            <div style='font-family:Arial,sans-serif;max-width:600px;margin:auto;border:1px solid #ccc;border-radius:10px;padding:20px;background:#ffffff;'>
              <h2 style='color:#16a34a;'>Recharge Successful 🎉</h2>
              <p>Hi <b>%s</b>,</p>
              <p>We’re pleased to inform you that your recharge has been processed successfully!</p>
              <table style='width:100%%;border-collapse:collapse;margin:15px 0;'>
                <tr><td style='padding:8px;border-bottom:1px solid #eee;'>💡 Plan Name:</td><td>%s</td></tr>
                <tr><td style='padding:8px;border-bottom:1px solid #eee;'>💰 Amount:</td><td>₹%.2f</td></tr>
                <tr><td style='padding:8px;border-bottom:1px solid #eee;'>📆 Validity:</td><td>%d days</td></tr>
              </table>
              <p>Thank you for using <b>Mobile Prepaid</b>! We appreciate your trust in us.</p>
              <p>– The Mobile Prepaid Team</p>
              <hr>
              <p style='font-size:12px;color:#555;text-align:center;'>© Mobile Prepaid 2025</p>
            </div>
            """.formatted(name, planName, price, validityDays);
        sendHtmlMail(to, subject, htmlBody);
    }
}
