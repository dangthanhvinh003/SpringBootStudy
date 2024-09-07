package com.example.Bai1SpringBoot.Model.Repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.Bai1SpringBoot.Model.Entity.Product;
import com.example.Bai1SpringBoot.Model.Entity.TypeProduct;

@Repository
public class ProductRepo {
    @Autowired
    TypeProductRepo typeProductRepo;

    public ArrayList<Product> getAllProduct() throws Exception {
        ArrayList<Product> pList = new ArrayList<>();
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select * from product"); // Query la Xem du lieu, Update la Add,Delete,Update
        while (rs.next()) {
            int pid = rs.getInt("pid");
            String pName = rs.getString("pName");
            int pType = rs.getInt("tid");
            TypeProduct tProduct = typeProductRepo.getTypeProductbyID(pType);
            double price = rs.getDouble("price");
            int quantity = rs.getInt("quantity");
            String img = rs.getString("img");
            Product product = new Product(pid, pName, tProduct, price, quantity,img);
            pList.add(product);
        }
        return pList;
    }

    public void AddProduct(Product product) throws Exception {
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        PreparedStatement ps = con
                .prepareStatement("INSERT INTO product (pName, tid, price, quantity,img) VALUES (?,?,?,?,?)");
        ps.setString(1, product.getPName());
        ps.setInt(2, product.getTypeProduct().getTid());
        ps.setDouble(3, product.getPrice());
        ps.setInt(4, product.getQuantity());
        ps.setString(5, product.getImg());
        ps.executeUpdate();
        ps.close();
    }

    public Product getProductBypid(int pid) throws Exception {
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        PreparedStatement ps = con.prepareStatement("select * from product where pid = ?");
        ps.setInt(1, pid);
        ps.executeQuery();
        ResultSet rs = ps.getResultSet();
        rs.next();
        int pid1 = rs.getInt("pid");
        String pName = rs.getString("pName");
        int pType = rs.getInt("tid");
        TypeProduct tProduct = typeProductRepo.getTypeProductbyID(pType);
        double price = rs.getDouble("price");
        int quantity = rs.getInt("quantity");
        String img = rs.getString("img");
        Product product = new Product(pid1, pName, tProduct, price, quantity,img);
        return product;
    }

    public void updatepProductById(Product product) throws Exception {
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        PreparedStatement ps = con.prepareStatement(
                "update product set pName = ?, tid=?, price= ?, quantity =?, img = ? where pid = ?");
        ps.setString(1, product.getPName());
        ps.setInt(2, product.getTypeProduct().getTid());
        ps.setDouble(3, product.getPrice());
        ps.setInt(4, product.getQuantity());
        ps.setString(5, product.getImg());
        ps.setInt(6, product.getPid());
        ps.executeUpdate();
        ps.close();
    }

    public void UpdateQuantity(int pid, int newQuantity) throws Exception {
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        PreparedStatement ps = con.prepareStatement("update product set quantity = ? where pid =?");
        ps.setInt(1, newQuantity);
        ps.setInt(2, pid);
        ps.executeUpdate();
        ps.close();
    }
}
