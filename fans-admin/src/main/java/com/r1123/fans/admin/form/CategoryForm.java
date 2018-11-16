package com.r1123.fans.admin.form;

import com.r1123.fans.core.entity.Category;
import com.r1123.fans.core.type.CategoryType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by helloqdz on 2018/11/15.
 */
@Setter
@Getter
public class CategoryForm {

    @NotBlank
    private String name;

    @NotNull
    private Boolean isOnShow;

    private Long[] parent;

    private String[] imgs;

    @NotNull
    private Long displayOrder;

    @NotBlank
    private String type;


    public Category getBasiCategory() {
        Category category = new Category();
        category.setName(name);
        category.setDisplayOrder(displayOrder);
        category.setIsOnShow(isOnShow);
        category.setCategoryType(CategoryType.getInstance(type));
        return category;
    }
}
