package com.aits.mobileprepaid.dto;

import jakarta.validation.constraints.NotNull;

public class RechargeRequest {
    @NotNull
    private Long userId;

    @NotNull
    private Long planId;

    private String paymentMethod;

    // getters & setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getPlanId() { return planId; }
    public void setPlanId(@NotNull Long planId) { this.planId = planId; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
}
