package com.example.Service;

import com.example.DTO.PageableDTO;
import com.example.DTO.ProductDTO;
import com.example.DTO.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    public Result createProduct(String productName,
                                String productDescription,
                                String price,
                                String quantity,
                                MultipartFile file,
                                String categoryName
    );

    public Result getAllProduct(PageableDTO pageableDTO );

    public Result getSingleProduct(int id);

    public Result updateProduct(int id, String productName, String productDescription, String price, String quantity, MultipartFile file);

    public Result deleteProduct(int id);
}
