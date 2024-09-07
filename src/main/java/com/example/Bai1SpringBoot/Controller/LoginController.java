package com.example.Bai1SpringBoot.Controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Bai1SpringBoot.Model.Entity.Product;
import com.example.Bai1SpringBoot.Model.Entity.Users;
import com.example.Bai1SpringBoot.Model.Repo.LoginRepo;
import com.example.Bai1SpringBoot.Model.Repo.ProductRepo;


import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    ProductRepo productRepo;
    @Autowired
    LoginRepo loginRepo;
    @GetMapping("/")
    public String ShowIndex(Model model) throws Exception {
        ArrayList<Product> plist = productRepo.getAllProduct();
       
        model.addAttribute("ProductList", plist);
        return "Public/index";
    }

    @GetMapping("/login")
    public String ShowLogin() {
        return "Public/login";
    }
    @GetMapping("/logout")
    public String Logout(HttpSession httpSession) {
        httpSession.removeAttribute("UserAfterLogin");
        return "redirect:/";
    }
    @PostMapping("/loginToSystem")
    public String LoginToSystem(@RequestParam("username") String username, @RequestParam("password") String password,HttpSession httpSession) throws Exception {
        Users user = loginRepo.checkLogin(username, password);
        
        if (user == null) {
            return "Public/login";
        } else {
            httpSession.setAttribute("UserAfterLogin", user);
            return "redirect:/";
        }
        
      
    }
}
