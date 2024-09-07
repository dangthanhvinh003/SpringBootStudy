package com.example.Bai1SpringBoot.Model.Repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.example.Bai1SpringBoot.Model.Entity.Users;

@Repository
public class UserRepo {
      public ArrayList<Users> getAllUser() throws Exception {
        ArrayList<Users> UserList = new ArrayList<>();
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select * from users"); // Query la Xem du lieu, Update la Add,Delete,Update
        while (rs.next()) {
            int uid = rs.getInt("uid");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            String phone = rs.getString("phone");
            String email = rs.getString("email");
            String cccd = rs.getString("cccd");
            String address = rs.getString("address");
            String username = rs.getString("username");
            String password = rs.getString("password");
            String role = rs.getString("role");
            Users user = new Users(uid, name, age, phone, email, cccd, address, username, password, role);
            UserList.add(user);

        }
        return UserList;
    }

    public void addNewUser(Users user) throws Exception {
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        PreparedStatement ps = con.prepareStatement(
                "insert into users(name, age, phone, email, cccd, address, username, password, role) values(?,?,?,?,?,?,?,?,?)");
        ps.setString(1, user.getName());
        ps.setInt(2, user.getAge());
        ps.setString(3, user.getPhone());
        ps.setString(4, user.getEmail());
        ps.setString(5, user.getCccd());
        ps.setString(6, user.getAddress());
        ps.setString(7, user.getUsername());
        ps.setString(8, user.getPassword());
        ps.setString(9, user.getRole());
        ps.executeUpdate();
        ps.close();
    }

    public Users getUserById(int id) throws Exception {
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        PreparedStatement ps = con.prepareStatement("select * from users where uid = ?");
        ps.setInt(1, id);// 3
        ps.executeQuery();
        ResultSet rs = ps.getResultSet();
        rs.next();
        int uid = rs.getInt("uid");
        String name = rs.getString("name");
        int age = rs.getInt("age");
        String phone = rs.getString("phone");
        String email = rs.getString("email");
        String cccd = rs.getString("cccd");
        String address = rs.getString("address");
        String username = rs.getString("username");
        String password = rs.getString("password");
        String role = rs.getString("role");
        Users user = new Users(uid, name, age, phone, email, cccd, address, username, password, role);
        return user;
    }
    public ArrayList<Users> getAllUserByRole(String role) throws Exception {
        ArrayList<Users> UserList = new ArrayList<>();
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        PreparedStatement ps = con.prepareStatement("select * from users where role = ?");
        ps.setString(1, role);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int uid = rs.getInt("uid");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            String phone = rs.getString("phone");
            String email = rs.getString("email");
            String cccd = rs.getString("cccd");
            String address = rs.getString("address");
            String username = rs.getString("username");
            String password = rs.getString("password");
            String role1 = rs.getString("role");
            Users user = new Users(uid, name, age, phone, email, cccd, address, username, password, role1);
            UserList.add(user);
        }
        return UserList;
    }
    public void updateUserNameById(int id, String name)throws Exception{
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        PreparedStatement ps = con.prepareStatement(
                "update users set `name` = ? where uid = ?");
        ps.setString(1, name);
        ps.executeUpdate();
        ps.close();
    }
}
