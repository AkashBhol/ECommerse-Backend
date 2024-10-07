package com.example.Controller;

import com.example.DTO.PageableDTO;
import com.example.DTO.Result;
import com.example.Service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);


    @PostMapping("/product/create")
    public Result createProduct(
            @RequestParam("productName") String productName,
            @RequestParam("productDescription") String productDescription,
            @RequestParam("price") String price,
            @RequestParam("quantity") String quantity,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("categoryName") String categoryName
    ) {
        Result product = productService.createProduct(productName, productDescription, price, quantity, file,categoryName);
        return product;
    }

    @GetMapping("/product/get")
    public Result getAllProduct(PageableDTO pageableDTO) {
        Result allProduct = productService.getAllProduct(pageableDTO);
        return allProduct;
    }


    @GetMapping("/product/getsingle")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Result getSingleProduct(@RequestParam("id") int id) {
        Result singleProduct = productService.getSingleProduct(id);
        return singleProduct;
    }

    @PutMapping("/product/update")
    public Result updateProduct(
            @RequestParam("id") int id,
            @RequestParam("productName") String productName,
            @RequestParam("productDescription") String productDescription,
            @RequestParam("price") String price,
            @RequestParam("quantity") String quantity,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        Result result = productService.updateProduct(id, productName, productDescription, price, quantity, file);
        return result;
    }

    @DeleteMapping("/product/delete")
    public Result deleteProduct(@RequestParam("id") int id) {
        Result result = productService.deleteProduct(id);
        return result;
    }
}
