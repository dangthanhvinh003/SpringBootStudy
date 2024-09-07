package com.example.Bai1SpringBoot.Model.Repo;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;


import com.example.Bai1SpringBoot.Model.Entity.TypeProduct;

@Repository
public class TypeProductRepo {
    public ArrayList<TypeProduct> getAllType() throws Exception{
         ArrayList<TypeProduct> TypeList = new ArrayList<>();
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select * from TypeProduct"); // Query la Xem du lieu, Update la Add,Delete,Update
        while (rs.next()) {
            int pid = rs.getInt("tid");
            String pName = rs.getString("tName");
            TypeProduct typeProduct = new TypeProduct(pid, pName);
            TypeList.add(typeProduct);
        }
        return TypeList;
    }
    public TypeProduct getTypeProductbyID(int id) throws Exception{
        Class.forName(Baseconnection.nameClass);
        Connection con = DriverManager.getConnection(Baseconnection.url, Baseconnection.username,
                Baseconnection.password);
        PreparedStatement ps = con.prepareStatement("select * from TypeProduct where tid = ?");
        // Query la Xem du lieu, Update la Add,Delete,Update
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery(); 
        rs.next();
        int pid = rs.getInt("tid");
        String pName = rs.getString("tName");
        TypeProduct typeProduct = new TypeProduct(pid, pName);
        return typeProduct;
    }
}
