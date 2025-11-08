package com.aits.mobileprepaid.service;

import com.aits.mobileprepaid.entity.RegistrationRequest;
import com.aits.mobileprepaid.entity.Role;
import com.aits.mobileprepaid.entity.User;
import com.aits.mobileprepaid.repo.RegistrationRequestRepository;
import com.aits.mobileprepaid.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRequestRepository requestRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private EmailService emailService;

    // Admin email from properties (configurable)
    @Value("${admin.email:admin@mobileprepaid.com}")
    private String adminEmail;

    // Frontend admin URL for convenience (configurable)
    @Value("${frontend.admin.requests-url:http://localhost:5173/admin/requests}")
    private String adminRequestsUrl;

    // Save new request and notify admin by email
    public RegistrationRequest saveRequest(RegistrationRequest req) {
        RegistrationRequest saved = requestRepo.save(req);

        // Build HTML body for admin notification
        String subject = "New Registration Request — Mobile Prepaid";
        String htmlBody = """
                <html>
                  <body style='font-family: Arial, sans-serif; color:#333;'>
                    <h2 style='color:#2563EB;'>New User Registration Request</h2>
                    <p>A new user has requested registration. Details below:</p>
                    <table style='border-collapse: collapse;'>
                      <tr><td style='padding:6px;'><b>Name:</b></td><td style='padding:6px;'>%s</td></tr>
                      <tr><td style='padding:6px;'><b>Email:</b></td><td style='padding:6px;'>%s</td></tr>
                      <tr><td style='padding:6px;'><b>Mobile:</b></td><td style='padding:6px;'>%s</td></tr>
                      <tr><td style='padding:6px;'><b>Message:</b></td><td style='padding:6px;'>%s</td></tr>
                      <tr><td style='padding:6px;'><b>Requested at:</b></td><td style='padding:6px;'>%s</td></tr>
                    </table>
                    <p style='margin-top:12px;'>Review and take action: <a href='%s'>Open pending requests</a></p>
                    <hr>
                    <p style='font-size:0.9em; color:#666;'>This is an automated notification.</p>
                  </body>
                </html>
                """.formatted(
                    escapeHtml(saved.getName()),
                    escapeHtml(saved.getEmail()),
                    escapeHtml(saved.getMobile()),
                    escapeHtml(saved.getMessage() == null ? "(none)" : saved.getMessage()),
                    saved.getRequestDate().toString(),
                    adminRequestsUrl
                );

        // send HTML mail to admin (best-effort; exceptions logged inside EmailService)
        emailService.sendHtmlMail(adminEmail, subject, htmlBody);

        return saved;
    }

    // --- rest of service (getPendingRequests, approveRequest, rejectRequest) remains unchanged ---
    public List<RegistrationRequest> getPendingRequests() {
        return requestRepo.findByApprovedFalse();
    }

    public boolean approveRequest(Long id) {
        Optional<RegistrationRequest> opt = requestRepo.findById(id);
        if (opt.isEmpty()) return false;

        RegistrationRequest req = opt.get();
        if (req.isApproved()) return false;

        String tempPassword = "User@" + (int)(Math.random() * 9000 + 1000);

        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setMobile(req.getMobile());
        user.setPassword(encoder.encode(tempPassword));
        user.setRole(Role.USER);

        userRepo.save(user);

        req.setApproved(true);
        requestRepo.save(req);

        emailService.sendWelcomeEmail(req.getEmail(), req.getName());
        emailService.sendMail(
            req.getEmail(),
            "Mobile Prepaid Account Created",
            "Hi " + req.getName() + ",\n\n" +
            "Your registration request has been approved!\n\n" +
            "Login Credentials:\n" +
            "Email: " + req.getEmail() + "\n" +
            "Password: " + tempPassword + "\n\n" +
            "Please change your password after first login.\n\n" +
            "Regards,\nMobile Prepaid Team"
        );

        return true;
    }

    public boolean rejectRequest(Long id) {
        Optional<RegistrationRequest> opt = requestRepo.findById(id);
        if (opt.isEmpty()) return false;

        requestRepo.deleteById(id);
        return true;
    }

    // Simple helper to escape a few HTML characters to avoid broken HTML content
    private static String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
}
