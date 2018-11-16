package com.r1123.fans.admin.controller;

import com.r1123.fans.admin.form.CategoryForm;
import com.r1123.fans.admin.service.CategoryService;
import com.r1123.fans.core.common.GlobalErrorCode;
import com.r1123.fans.core.entity.Category;
import com.r1123.fans.core.repo.CategoryRepo;
import com.r1123.fans.core.repo.CategoryXrefRepo;
import com.r1123.fans.core.type.CategoryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

/**
 * Created by helloqdz on 2018/10/21.
 */
@Controller
@RequestMapping("/task")
public class TaskController {

    @Resource(name = "bsCategoryRepos")
    CategoryRepo repo;

    @Resource(name = "bsCategoryXrefRepos")
    CategoryXrefRepo xrefRepo;


    @Autowired
    private CategoryService categoryService;

    /**
     * 新增类目
     * @param categoryForm
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    private Map<String, Object> hello(@Valid CategoryForm categoryForm){
        Long[] parents = categoryForm.getParent();
        Set<Category> parentCategories = new HashSet<Category>();
        Map<String, Object> result = new HashMap(2);
        Category parentCategory;
        if (parents != null){
            for (Long p : parents){
                parentCategory = categoryService.find(p);
                if (parentCategory == null){
                    result.put("err", GlobalErrorCode.NOT_FOUND);
                    result.put("msg", "父级类目不存在");
                    return result;
                }
                parentCategories.add(parentCategory);
            }
        }
        Category category = categoryForm.getBasiCategory();
        try{
            category = categoryService.save(category, parentCategories);
            result.put("err", GlobalErrorCode.SUCESS);
        }catch (Exception e){
            result.put("err", GlobalErrorCode.INTERNAL_ERROR);
            result.put("msg", "服务器报错");
        }
        return result;
    }

    @GetMapping("/list")
    public List<Category> list(Pageable pageable, @RequestParam("key") String key){
        CategoryType categoryType = CategoryType.getInstance("HOME");
        List<Category> cat = categoryService.find(categoryType);
        return cat;
    }


    @RequestMapping(value = "/date2", method = RequestMethod.DELETE)
    private @ResponseBody String hello2(){
        return "hello2" + new Date().toString();
    }
}
