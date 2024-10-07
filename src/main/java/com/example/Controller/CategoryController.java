package com.example.Controller;
import com.example.DTO.CategoryDTO;
import com.example.DTO.PageableDTO;
import com.example.DTO.Result;
import com.example.Service.CategoryService;
import com.example.ServiceImpl.CategoryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private static final Logger log= LoggerFactory.getLogger(CategoryServiceImpl.class);

    @PostMapping("/category/create")
    public Result createCategory( @RequestBody CategoryDTO categoryDTO){
      return   categoryService.createCategory(categoryDTO);
    }

    @GetMapping("/category/get")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Result getAllCategory(PageableDTO pageableDTO){
       return categoryService.getAllCategory(pageableDTO);
    }

    @GetMapping("/categoory/get/all")
    public Result getsAllCategory(){
        return categoryService.getsAllCategory();
    }
}
