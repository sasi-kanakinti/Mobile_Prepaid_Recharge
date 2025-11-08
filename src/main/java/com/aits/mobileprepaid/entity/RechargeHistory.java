package com.aits.mobileprepaid.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class RechargeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private RechargePlan plan;

    private String paymentMethod;
    private LocalDateTime rechargeDate;
    private LocalDateTime expiryDate; // new
    private String transactionId;     // new
    private String paymentStatus;     // new (e.g., SUCCESS, FAILED)

    public RechargeHistory() {}
    public RechargeHistory(Long id, User user, RechargePlan plan, String paymentMethod, LocalDateTime rechargeDate, LocalDateTime expiryDate,String transactionId, String paymentStatus) {
		super();
		this.id = id;
		this.user = user;
		this.plan = plan;
		this.paymentMethod = paymentMethod;
		this.rechargeDate = rechargeDate;
		this.expiryDate=expiryDate;
		this.transactionId=transactionId;
		this.paymentStatus=paymentStatus;
	}

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public RechargePlan getPlan() { return plan; }
    public void setPlan(RechargePlan plan) { this.plan = plan; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public LocalDateTime getRechargeDate() { return rechargeDate; }
    public void setRechargeDate(LocalDateTime rechargeDate) { this.rechargeDate = rechargeDate; }

    public LocalDateTime getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDateTime expiryDate) { this.expiryDate = expiryDate; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
}
