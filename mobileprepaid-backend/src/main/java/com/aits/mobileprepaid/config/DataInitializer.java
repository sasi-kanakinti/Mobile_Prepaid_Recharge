package com.aits.mobileprepaid.config;

import com.aits.mobileprepaid.entity.Role;
import com.aits.mobileprepaid.entity.User;
import com.aits.mobileprepaid.repo.UserRepository;
import com.aits.mobileprepaid.util.EmailValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${admin.email:9908135565ks@gmail.com}")
    private String adminEmail;

    @Value("${admin.password:Admin@123}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        // ✅ Step 1: validate admin email domain before seeding
        if (!EmailValidator.hasValidMXRecord(adminEmail)) {
            System.err.println("❌ Invalid admin email domain: " + adminEmail);
            System.err.println("Please check that the domain is real (e.g., gmail.com).");
            return; // abort seeding admin user
        }

        // ✅ Step 2: create admin if not found
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            User admin = new User();
            admin.setName("System Admin");
            admin.setEmail(adminEmail);
            admin.setMobile("9999999999");
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
            System.out.println("✅ Default admin created");
            System.out.println("   Email: " + adminEmail);
            System.out.println("   Password: " + adminPassword);
        } else {
            System.out.println("✅ Admin already exists: " + adminEmail);
        }
    }
}
