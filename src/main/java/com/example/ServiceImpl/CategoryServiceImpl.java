package com.example.ServiceImpl;

import com.example.Config.BsicUtil;
import com.example.DTO.CategoryDTO;
import com.example.DTO.PageableDTO;
import com.example.DTO.PageableResponseVO;
import com.example.DTO.Result;
import com.example.Model.Category;
import com.example.Model.Product;
import com.example.Repository.CategoryRepository;
import com.example.Repository.ProductRepository;
import com.example.Service.CategoryService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Service
public class CategoryServiceImpl extends BsicUtil implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Override

    public Result createCategory(CategoryDTO categoryDTO) {
        Category category=  new Category();
        if(isNullOrEmpty(categoryDTO.getEmail())){
            return prepareResponseObject("034","Email Can nOt Be Null",null);
        }
        if(categoryRepository.existsByCategoryTitle(categoryDTO.getCategoryTitle())){
            return prepareResponseObject("040","Category exist by email",null);
        }
        category.setCategoryTitle(categoryDTO.getCategoryTitle());
        category.setEmail(categoryDTO.getEmail());
        return prepareResponseObject("038","Category Created Successfully",categoryRepository.save(category));
    }

    @Override
    public Result getAllCategory(PageableDTO pageableDTO) {
        Pageable pageable=null;
        pageable = PageRequest.of(pageableDTO.getPage(), pageableDTO.getPageSize());
        Page<Category> all = categoryRepository.findAll(pageable);
        log.warn("calling to the findAll method {}",all);
        PageableResponseVO pageableResponseVO=new PageableResponseVO(all.getContent(),all,pageableDTO);
        return prepareResponseObject("038","Category fetched successfully",pageableResponseVO);
    }

    @Override
    public Result updateCategory(int id, CategoryDTO categoryDTO) {
        return null;
    }

    @Override
    public Result getSingleCategory(int id) {
        return null;
    }

    @Override
    public Result deleteCAtegory(int id) {
        return null;
    }

    @Override
    public Result getsAllCategory() {
        List<Category> all = categoryRepository.findAll();
        return prepareResponseObject("043","All Records Fetched Successfully",all);
    }

    @Override
    public Result updateProduct(int id, String productName, String productDescription, String price, String quantity, String file) {
        return null;
    }
}
