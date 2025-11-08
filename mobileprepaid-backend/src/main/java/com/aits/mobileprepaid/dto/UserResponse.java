package com.aits.mobileprepaid.dto;

import com.aits.mobileprepaid.entity.Role;

public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String mobile;
    private Role role;

    public UserResponse() {}

    public UserResponse(Long id, String name, String email, String mobile, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.role = role;
    }

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}
