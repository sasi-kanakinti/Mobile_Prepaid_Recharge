package com.aits.mobileprepaid.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RechargePlanRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String category;

    @NotNull
    @Min(1)
    private Double price;

    @NotNull
    @Min(1)
    private Integer validityInDays;

    private String description;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getValidityInDays() { return validityInDays; }
    public void setValidityInDays(Integer validityInDays) { this.validityInDays = validityInDays; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
