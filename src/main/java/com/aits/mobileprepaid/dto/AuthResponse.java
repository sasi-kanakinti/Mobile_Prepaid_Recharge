package com.aits.mobileprepaid.dto;

import com.aits.mobileprepaid.entity.Role;

public class AuthResponse {
    private String token;
    private Role role;
    private Long userId;
    private String name;
    private String email;
    private String mobile;

    public AuthResponse() {}

    public AuthResponse(String token, Role role, Long userId, String name, String email, String mobile) {
        this.token = token;
        this.role = role;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    // getters & setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }
}
