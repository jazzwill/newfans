package com.r1123.fans.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by helloqdz on 2018/11/15.
 */
@Entity
@Component
@Inheritance(strategy = InheritanceType.JOINED)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Getter
@Setter
@Table(name = "BS_CATEGORY_XREF")
public class CategoryXref implements Serializable {


    private static final long serialVersionUID = 1L;

    private Date createdAt = new Date(); // 创建日期

    private Date updatedAt = new Date(); // 更新日期

    private Boolean archived = true;

    @Id
    @Column(name = "CATEGORY_XREF_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @ManyToOne(targetEntity = Category.class, optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "CATEGORY_ID")
    @JsonIgnore
    protected Category category = new Category();

    @ManyToOne(targetEntity = Category.class, optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "SUB_CATEGORY_ID")
    @JsonIgnore
    protected Category subCategory = new Category();

    @Column(name = "DISPLAY_ORDER", precision = 10, scale = 6)
    protected BigDecimal displayOrder;

    @Column(name = "DEFAULT_REFERENCE")
    protected Boolean defaultReference;

    @JsonIgnore
    public Category getSubCategory(){
        return subCategory;
    }

    @JsonIgnore
    public Category getCcategory(){
        return category;
    }
}
