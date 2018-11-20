package com.r1123.fans.site.controller;

import com.r1123.fans.core.entity.Category;
import com.r1123.fans.core.type.CategoryType;
import com.r1123.fans.site.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by helloqdz on 2018/11/19.
 */
@RestController
@RequestMapping("/category")
public class SiteCategoryController {



    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/list")
    public List<Map<String, Object>> categories(@RequestParam("categoryType") String categoryType){
        if (StringUtils.isEmpty(categoryType)){
            categoryType = "CATEGORY";
        }
        CategoryType categoryType1 = CategoryType.getInstance(categoryType);
        List<Category> categories = categoryService.find(categoryType1);
        Map<String, Object> objs = new HashMap();
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        if (!CollectionUtils.isEmpty(categories)){
            for (Category category: categories){
                objs = new HashMap();
                objs.put("categoryId", category.getId());
                objs.put("categoryName", category.getName());
                result.add(objs);
            }
        }
        return result;
    }
}
