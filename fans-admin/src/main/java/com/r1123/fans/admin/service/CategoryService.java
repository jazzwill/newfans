package com.r1123.fans.admin.service;


import com.r1123.fans.core.entity.Category;
import com.r1123.fans.core.entity.CategoryXref;
import com.r1123.fans.core.type.CategoryType;

import java.util.List;
import java.util.Set;

/**
 * Created by helloqdz on 2018/10/23.
 */
public interface CategoryService {


    public Category save(Category category, Set<Category> parentCategories);

    public Category find(Long id);

    List<Category> find(CategoryType categoryType);

    void save(Category category);

    List<Category> find(Category parent);

    List<CategoryXref> findInXref(Category category);

    void remove(Category category);

    Category update(Category category, Set<Category> parentCategories);
}
