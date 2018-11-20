package com.r1123.fans.admin.service;

import com.r1123.fans.core.entity.Category;
import com.r1123.fans.core.entity.CategoryXref;
import com.r1123.fans.core.repo.CategoryRepo;
import com.r1123.fans.core.repo.CategoryXrefRepo;
import com.r1123.fans.core.type.CategoryType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import org.springframework.data.domain.Sort.Direction;
import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * Created by helloqdz on 2018/10/23.
 */
@Service("categoryServiceImpl")
public class CategoryServiceImpl implements CategoryService {

    @Resource(name = "bsCategoryRepos")
    private CategoryRepo categoryRepo;

    @Resource(name = "bsCategoryXrefRepos")
    private CategoryXrefRepo categoryXrefRepo;


    @Override
    public Category save(Category category, Set<Category> parentCategories) {
        category = setParentToCat(category, parentCategories);
        category = categoryRepo.save(category);
        return category;
    }

    @Override
    public Category find(Long id) {
        return categoryRepo.findOne(id);
    }

    @Override
    public List<Category> find(final CategoryType categoryType) {
        Sort sort = new Sort(Direction.DESC, "displayOrder");
        return categoryRepo.findAll(new Specification<Category>() {
            @Override
            public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> ps = new ArrayList<Predicate>(1);
                if (categoryType != null){
                    ps.add(cb.equal(root.get("categoryType"), categoryType.getType()));
                }
                Predicate[] pre = new Predicate[ps.size()];
                query = query.where(ps.toArray(pre));
                return query.getRestriction();
            }
        }, sort);
    }

    @Override
    public void save(Category category) {
        categoryRepo.save(category);
    }

    @Override
    public List<Category> find(Category parent) {
        List<CategoryXref> categoryXrefs = categoryXrefRepo.findByCategory(parent);
        List<Category> categories;
        if (!CollectionUtils.isEmpty(categoryXrefs)){
            categories = new ArrayList<Category>(categoryXrefs.size());
            for (CategoryXref xref: categoryXrefs){
                categories.add(xref.getSubCategory());
            }
        }else {
            categories = Collections.emptyList();
        }
        return categories;
    }

    @Override
    public List<CategoryXref> findInXref(Category category) {
        return categoryXrefRepo.findByCategory(category);
    }

    @Override
    public void remove(Category category) {
        categoryRepo.delete(category);
    }

    @Override
    public Category update(Category category, Set<Category> parentCategories) {
        final Category finalCategory = category;
        if (!CollectionUtils.isEmpty(parentCategories)){
            //remove
            List<CategoryXref> xrefs = categoryXrefRepo.findAll(new Specification<CategoryXref>() {
                @Override
                public Predicate toPredicate(Root<CategoryXref> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    return query.where(cb.equal(root.get("subCategory"), finalCategory)).getRestriction();
                }
            });
            categoryXrefRepo.deleteInBatch(xrefs);
        }
        category = setParentToCat(category, parentCategories);
        category = categoryRepo.save(category);
        return category;
    }

    private Category setParentToCat(Category category, Set<Category> parentCategories){
        if (!CollectionUtils.isEmpty(parentCategories)){
            CategoryXref categoryXref;
            for (Category p: parentCategories){
                categoryXref = new CategoryXref();
                categoryXref.setSubCategory(category);
                categoryXref.setCategory(p);
                category.getAllParentCategoryXrefs().add(categoryXref);
            }
        }
        return category;
    }
}
