package com.example.Bai1SpringBoot.Model.Entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {
    private int oid;
    private Users users;
    private Product product;
    private int quantity;
    private double totalPrice;
}
