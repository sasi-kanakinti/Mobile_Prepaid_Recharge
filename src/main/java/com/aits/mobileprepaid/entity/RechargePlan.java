package com.aits.mobileprepaid.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@SuppressWarnings("unused")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RechargePlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
	private String name;
    private String category;
    private double price;
    private int validityInDays;
    private String description;

    // Optional convenience constructor (if needed elsewhere)
    public RechargePlan(Long id, String name, String category, double price, int validityInDays, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.validityInDays = validityInDays;
        this.description = description;
    }
    
    public RechargePlan() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getValidityInDays() {
		return validityInDays;
	}

	public void setValidityInDays(int validityInDays) {
		this.validityInDays = validityInDays;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
