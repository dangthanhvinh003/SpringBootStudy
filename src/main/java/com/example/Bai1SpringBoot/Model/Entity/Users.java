package com.example.Bai1SpringBoot.Model.Entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Users {
    private int uid;
    private String name;
    private int age;
    private String phone;
    private String email;
    private String cccd;
    private String address;
    private String username;
    private String password;
    private String role;
    public Users(String name, String username, String password, String role) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
}
