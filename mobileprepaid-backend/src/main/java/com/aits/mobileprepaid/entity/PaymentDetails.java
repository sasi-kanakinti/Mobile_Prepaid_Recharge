package com.aits.mobileprepaid.entity;

import lombok.Data;

@Data
public class PaymentDetails {
    @SuppressWarnings("unused")
	private String method;
    @SuppressWarnings("unused")
	private String upiId;
    @SuppressWarnings("unused")
	private String cardNumber;
    @SuppressWarnings("unused")
	private String bankAccount;
}