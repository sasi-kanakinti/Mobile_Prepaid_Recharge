package com.aits.mobileprepaid.dto;

public class RechargePlanResponse {
    private Long id;
    private String name;
    private String category;
    private Double price;
    private Integer validityInDays;
    private String description;

    public RechargePlanResponse() {}

    public RechargePlanResponse(Long id, String name, String category, Double price, Integer validityInDays, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.validityInDays = validityInDays;
        this.description = description;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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
