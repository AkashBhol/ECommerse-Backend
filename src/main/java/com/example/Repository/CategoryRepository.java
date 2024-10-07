package com.example.Repository;

import com.example.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    public boolean existsByCategoryTitle(String categoryName);

    Category findByCategoryTitle(String categoryTitle);
}
