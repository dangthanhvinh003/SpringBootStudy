package com.example.Bai1SpringBoot.Controller;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Bai1SpringBoot.Model.Entity.Product;
import com.example.Bai1SpringBoot.Model.Entity.TypeProduct;
import com.example.Bai1SpringBoot.Model.Repo.ProductRepo;
import com.example.Bai1SpringBoot.Model.Repo.TypeProductRepo;

@Controller
public class ProductController {
    @Autowired
    ProductRepo productRepo;
    @Autowired 
    TypeProductRepo typeProductRepo;
    @GetMapping("/ViewDetail/{id}")
    public String ViewDetail(@PathVariable("id") int id, Model model) throws Exception {
        Product product = productRepo.getProductBypid(id);
        model.addAttribute("ProductDetail", product);
        return "Public/viewDetail";
    }

    @GetMapping("/showAddProduct")
    public String showAddProduct(Model model) throws Exception {
        ArrayList<TypeProduct> typeList = typeProductRepo.getAllType();
        model.addAttribute("TypeList", typeList);
        return "Product/addProduct";
    }

    @PostMapping("/addProducts")
    public String addProduct(@RequestParam("Name") String pName, @RequestParam("Type") int tid,
            @RequestParam("Price") double price, @RequestParam("Quantity") int quantity,
            @RequestParam("Img") String img)
            throws Exception {
            TypeProduct typeProduct = typeProductRepo.getTypeProductbyID(tid);
            Product product = new Product(0,pName, typeProduct, price, quantity, img);
            productRepo.AddProduct(product);
        return "redirect:/";
    }
    @GetMapping("/ShowEdit/{id}")
    public String showEdit(Model model, @PathVariable("id") int pid) throws Exception{
        ArrayList<TypeProduct> typeList = typeProductRepo.getAllType();
        model.addAttribute("TypeList", typeList );
        Product product = productRepo.getProductBypid(pid);
        model.addAttribute("Product", product);
        return "Product/editProduct";
    }
    @PostMapping("/editProduct")
    public String editProduct(@RequestParam("pid") int pid, @RequestParam("Name") String pName, @RequestParam("Type") int tid,
            @RequestParam("Price") double price, @RequestParam("Quantity") int quantity,
            @RequestParam("Img") String img)
            throws Exception {
            TypeProduct typeProduct = typeProductRepo.getTypeProductbyID(tid);
            Product product = new Product(pid,pName,typeProduct , price, quantity, img);
            productRepo.updatepProductById(product);
        return "redirect:/";
    }
    @GetMapping("/searchProduct")
    public String searchProduct(@RequestParam("search") String search, Model model) throws Exception {
       ArrayList<Product> plist = productRepo.getAllProduct();
       ArrayList<Product> findProduct = new ArrayList<>();
       for (Product product : plist) {
            if (product.getPName().contains(search)) {
                findProduct.add(product);
            }
       }
        model.addAttribute("ProductList", findProduct);
        return "Public/index";
    }


}
