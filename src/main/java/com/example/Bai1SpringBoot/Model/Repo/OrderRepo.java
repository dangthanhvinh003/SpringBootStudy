package com.example.Bai1SpringBoot.Model.Repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.Bai1SpringBoot.Model.Entity.Order;
import com.example.Bai1SpringBoot.Model.Entity.Product;
import com.example.Bai1SpringBoot.Model.Entity.Users;


@Repository
public class OrderRepo {
    @Autowired
    UserRepo userRepo = new UserRepo();
    @Autowired
    ProductRepo productRepo = new ProductRepo();

   

    public ArrayList<Order> getAllOrder() throws Exception {
        ArrayList<Order> OrderList = new ArrayList<>();
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select * from `order`"); // Query la Xem du lieu, Update la Add,Delete,Update
        while (rs.next()) {
            int oid = rs.getInt("oid");
            int idUser = rs.getInt("uid");
            int idProduct = rs.getInt("pid");
            Users user = userRepo.getUserById(idUser);
            Product product = productRepo.getProductBypid(idProduct);
            int quantity = rs.getInt("quantity");
            double totalprice = rs.getDouble("totalprice");
            Order order = new Order(oid, user, product, quantity, totalprice);
            OrderList.add(order);
        }
        return OrderList;
    }
    public ArrayList<Order> getOrderByUserId( int id) throws Exception {
        ArrayList<Order> OrderList = new ArrayList<>();
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        PreparedStatement ps = con.prepareStatement("select * from `order` where uid = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int oid = rs.getInt("oid");
            int idUser = rs.getInt("uid");
            int idProduct = rs.getInt("pid");
            Users user = userRepo.getUserById(idUser);
            Product product = productRepo.getProductBypid(idProduct);
            int quantity = rs.getInt("quantity");
            double totalprice = rs.getDouble("totalprice");
            Order order = new Order(oid, user, product, quantity, totalprice);
            OrderList.add(order);
        }
        return OrderList;
    }
    public void addNewOrder(Order oder) throws Exception {
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO `order` (uid, pid, quantity, totalprice) VALUES (?,?,?,?)");
        ps.setInt(1, oder.getUsers().getUid());
        ps.setInt(2, oder.getProduct().getPid());
        ps.setInt(3, oder.getQuantity());
        ps.setDouble(4, oder.getTotalPrice());
        ps.executeUpdate();
    }
}
