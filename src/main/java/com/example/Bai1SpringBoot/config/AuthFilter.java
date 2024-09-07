package com.example.Bai1SpringBoot.config;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.example.Bai1SpringBoot.Model.Entity.Users;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String uri = httpServletRequest.getRequestURI();
        HttpSession session = httpServletRequest.getSession();
        Users user = (Users) session.getAttribute("UserAfterLogin");
        //Ap dung cho tat ca cac role
        if((uri.equals("/login") || uri.equals("/") || uri.equals("/loginToSystem") || uri.startsWith("/ViewDetail/") || uri.startsWith("/ShowOrder/") || uri.equals("/logout"))){
            chain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        
        //Ap dung cho role Admin
        if(user.getRole().equals("A")){
            chain.doFilter(httpServletRequest, httpServletResponse);
        }else if(user.getRole().equals("S")&& uri.equals("/showAddProduct")|| uri.equals("/addProducts") || uri.startsWith("/ShowEdit") || uri.equals("/editProduct") || uri.equals("/searchProduct") ){    //Ap dung cho role Seller
            chain.doFilter(httpServletRequest, httpServletResponse);
        }else if(user.getRole().equals("U") && uri.equals("/OrderProduct") || uri.startsWith("/ShowOrder/") ||uri.equals("/ShowOrderByUserId") || uri.equals("/ShowCart")|| uri.startsWith("/AddToCart") || uri.equals("/searchProduct") || uri.startsWith("/reduce/") || uri.startsWith("/increase") || uri.equals("/BuyProDuctInCart")){    //Ap dung cho role User
            chain.doFilter(httpServletRequest, httpServletResponse);
        }
        else{
            httpServletResponse.sendRedirect("/");
        }
    }
    
}
