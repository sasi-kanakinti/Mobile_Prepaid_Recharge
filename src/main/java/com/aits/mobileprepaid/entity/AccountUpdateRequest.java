package com.aits.mobileprepaid.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AccountUpdateRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String newEmail;
    private String newMobile;
    private String reason;
    private boolean processed = false;
    private LocalDateTime requestDate = LocalDateTime.now();

    public AccountUpdateRequest() {}

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getNewEmail() { return newEmail; }
    public void setNewEmail(String newEmail) { this.newEmail = newEmail; }

    public String getNewMobile() { return newMobile; }
    public void setNewMobile(String newMobile) { this.newMobile = newMobile; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public boolean isProcessed() { return processed; }
    public void setProcessed(boolean processed) { this.processed = processed; }

    public LocalDateTime getRequestDate() { return requestDate; }
    public void setRequestDate(LocalDateTime requestDate) { this.requestDate = requestDate; }
}
