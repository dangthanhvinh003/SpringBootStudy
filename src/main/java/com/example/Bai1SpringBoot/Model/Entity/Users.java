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
}
