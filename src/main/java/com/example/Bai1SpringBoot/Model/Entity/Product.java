package com.example.Bai1SpringBoot.Model.Entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    private int pid;
    private String pName;
    private TypeProduct TypeProduct;
    private double price;
    private int quantity;
    private String img;
}
