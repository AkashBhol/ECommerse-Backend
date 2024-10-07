package com.example.Service;

import com.example.DTO.CategoryDTO;
import com.example.DTO.PageableDTO;
import com.example.DTO.Result;

public interface CategoryService {

    public Result createCategory(CategoryDTO categoryDTO);

    public Result getAllCategory(PageableDTO pageableDTO);

    public Result updateCategory(int id,CategoryDTO categoryDTO);

    public  Result getSingleCategory(int id);

    public Result deleteCAtegory(int id);

    public Result getsAllCategory();
}
