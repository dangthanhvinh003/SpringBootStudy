package com.example.Bai1SpringBoot.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Bai1SpringBoot.Model.Entity.Order;
import com.example.Bai1SpringBoot.Model.Entity.Product;
import com.example.Bai1SpringBoot.Model.Entity.Users;
import com.example.Bai1SpringBoot.Model.Repo.OrderRepo;
import com.example.Bai1SpringBoot.Model.Repo.ProductRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {
    @Autowired
    OrderRepo orderRepo;
    @Autowired
    ProductRepo productRepo;

    ArrayList<Product> cartList = new ArrayList<>();

    @GetMapping("/ShowOrder/{id}")
    public String showOrder(@PathVariable("id") int id, Model model) throws Exception {
        Product product = productRepo.getProductBypid(id);
        model.addAttribute("ProductOrder", product);
        return "Order/showOrder";
    }

    @PostMapping("/OrderProduct")
    public String orderProduct(@RequestParam("pid") int id, @RequestParam("Quantity") int quantity,
            HttpSession httpSession) throws Exception {
        Users user = (Users) httpSession.getAttribute("UserAfterLogin");
        Product product = productRepo.getProductBypid(id);
        double totalprice = product.getPrice() * quantity;
        int newQuantity = product.getQuantity() - quantity;
        Order order = new Order(0, user, product, quantity, totalprice);
        productRepo.UpdateQuantity(id, newQuantity);
        orderRepo.addNewOrder(order);
        return "redirect:/";
    }

    @GetMapping("/ShowOrderByUserId")
    public String showOrderByUserId(HttpSession httpSession, Model model) throws Exception {
        Users user = (Users) httpSession.getAttribute("UserAfterLogin");
        ArrayList<Order> ordList = orderRepo.getOrderByUserId(user.getUid());
        model.addAttribute("OrderList", ordList);
        return "Order/showOrderByUserId";
    }

    @GetMapping("/AddToCart/{id}")
    public String addToCart(@PathVariable("id") int id, HttpSession httpSession) throws Exception {
        Product product = productRepo.getProductBypid(id);
        for (Product p : cartList) {
            if (p.getPid() == product.getPid()) {
                p.setQuantity(p.getQuantity() + 1);
                return "redirect:/";
            }
        }
        product.setQuantity(1);
        cartList.add(product);
        httpSession.setAttribute("CartList", cartList);
        return "redirect:/";
    }

    @GetMapping("/ShowCart")
    public String showCart(HttpSession httpSession, Model model) throws Exception {
        ArrayList<Product> proList = (ArrayList<Product>) httpSession.getAttribute("CartList");
        model.addAttribute("CartListModel", proList);
        double totalpriceAllProduct = 0;
        for (Product product : proList) {
            totalpriceAllProduct = totalpriceAllProduct + (product.getPrice() * product.getQuantity());
        }
        httpSession.setAttribute("totalpriceAllProduct", totalpriceAllProduct);
        return "Order/showCart";
    }

    @GetMapping("/reduce/{id}")
    public String reduce(@PathVariable("id") int id, HttpSession httpSession) throws Exception {
        ArrayList<Product> proList = (ArrayList<Product>) httpSession.getAttribute("CartList");
        for (Product product : proList) {
            if (product.getPid() == id) {
                if (product.getQuantity() == 1) {
                    proList.remove(product);
                    return "redirect:/ShowCart";
                } else {
                    product.setQuantity(product.getQuantity() - 1);
                    return "redirect:/ShowCart";
                }
            }
        }
        return "redirect:/ShowCart";
    }

    @GetMapping("/increase/{id}")
    public String increase(@PathVariable("id") int id, HttpSession httpSession) throws Exception {
        ArrayList<Product> proList = (ArrayList<Product>) httpSession.getAttribute("CartList");
        for (Product product : proList) {
            if (product.getPid() == id) {
                product.setQuantity(product.getQuantity() + 1);
                return "redirect:/ShowCart";
            }
        }
        return "redirect:/ShowCart";
    }

    @GetMapping("/BuyProDuctInCart")
    public String buyProDuctInCart(HttpSession httpSession) throws Exception {
        ArrayList<Product> proList = (ArrayList<Product>) httpSession.getAttribute("CartList");
        Users user = (Users) httpSession.getAttribute("UserAfterLogin");

        for (Product product : proList) {

            Product oldProduct = productRepo.getProductBypid(product.getPid());
            double totalprice = product.getPrice() * product.getQuantity();

            int newQuantity = oldProduct.getQuantity() - product.getQuantity();
            Order order = new Order(0, user, product, product.getQuantity(), totalprice);
            productRepo.UpdateQuantity(oldProduct.getPid(), newQuantity);
            orderRepo.addNewOrder(order);
        }

        proList.clear();
        return "redirect:/";
    }
}
