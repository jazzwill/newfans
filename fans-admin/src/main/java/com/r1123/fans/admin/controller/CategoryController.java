package com.r1123.fans.admin.controller;

import com.r1123.fans.admin.form.CategoryForm;
import com.r1123.fans.admin.service.CategoryService;
import com.r1123.fans.core.common.GlobalErrorCode;
import com.r1123.fans.core.entity.Category;
import com.r1123.fans.core.entity.CategoryXref;
import com.r1123.fans.core.repo.CategoryRepo;
import com.r1123.fans.core.repo.CategoryXrefRepo;
import com.r1123.fans.core.type.CategoryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

/**
 * Created by helloqdz on 2018/10/21.
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

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

    /**
     * 获取类目列表
     * @param type
     * @return
     */
    @GetMapping("/list")
    public List<Map<String, Object>> list(@RequestParam("type") String type){
        CategoryType categoryType = CategoryType.getInstance(type);
        List<Category> cat = categoryService.find(categoryType);
        List<Map<String, Object>> results = convertToCatVo(cat);
        return results;
    }

    /**
     * 更新状态
     * @param id
     * @param isOnShow
     * @return
     */
    @PostMapping("/updateOnShow")
    public GlobalErrorCode updateOnShow(@RequestParam("id") Long id,
                                        @RequestParam("isOnShow") Boolean isOnShow){
        Category category = categoryService.find(id);
        if (category == null){
            return GlobalErrorCode.NOT_FOUND;
        }
        category.setIsOnShow(isOnShow);
        categoryService.save(category);
        return GlobalErrorCode.SUCESS;
    }

    /**
     * 更新类目
     * @return
     */
    @PostMapping("/updateCategory")
    public Map<String, Object> updateCategory(@Valid CategoryForm categoryForm){
        Category category = categoryService.find(categoryForm.getId());
        category = categoryForm.getCategory(categoryForm, category);
        Long[] parents = categoryForm.getParent();
        Set<Category> parentCategories = new HashSet<Category>();
        Map<String, Object> result = new HashMap(2);
        Category parentCategory;
        if (parents != null){
            for (Long p : parents){
                parentCategory = categoryService.find(p);
                if (parentCategory == null){
                    result.put("err", GlobalErrorCode.NOT_FOUND);
                    result.put("msg", "父级类目不存在，更新失败");
                    return result;
                }
                parentCategories.add(parentCategory);
            }
        }
        category = categoryService.update(category, parentCategories);
        return result;
    }

    /**
     * 获取二级类目列表
     * @return
     */
    @GetMapping("/listByParent")
    public List<Map<String, Object>> listByParent(@RequestParam("id") Long id){
        Category parent = categoryService.find(id);
        if (parent == null){

        }
        List<Category> cat = categoryService.find(parent);
        List<Map<String, Object>> results = convertToCatVo(cat);
        return results;
    }

    private List<Map<String, Object>> convertToCatVo(List<Category> cat){
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>(cat.size());
        List<Category> pcat;
        Map<String, Object> obj;
        for (Category category:cat){
            obj = new HashMap();
            obj.put("name",category.getName());
            obj.put("id", category.getId());
            obj.put("displayOrder", category.getDisplayOrder());
            obj.put("isOnShow", category.getIsOnShow());
            pcat = category.getAllparents();
            if (!CollectionUtils.isEmpty(pcat)){
                String[] pnames = new String[pcat.size()];
                for (int i = 0; i < pcat.size(); i++){
                    pnames[i] = pcat.get(i).getName();
                }
                obj.put("parents",pnames);
            }
            results.add(obj);
        }
        return results;
    }

    /**
     * 删除一级类目
     * @param id
     * @return
     */
    @DeleteMapping("/remove")
    public Map<String, Object> remove(@RequestParam("id") Long id){
        Map<String, Object> results = new HashMap(3);
        Category category =  categoryService.find(id);
        if (category == null){

        }
        List<CategoryXref> categoryXrefs = categoryService.findInXref(category);
        if (!CollectionUtils.isEmpty(categoryXrefs)){
            results.put("err", GlobalErrorCode.INTERNAL_ERROR);
            results.put("msg", "该类目下有子类，请先解绑");
        }else {
            try {
                categoryService.remove(category);
                results.put("err", GlobalErrorCode.SUCESS);
            }catch (Exception e){
                results.put("msg", e);
            }
        }
        return results;
    }

    @RequestMapping(value = "/date2", method = RequestMethod.DELETE)
    private @ResponseBody String hello2(){
        return "hello2" + new Date().toString();
    }
}
