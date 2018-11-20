package com.r1123.fans.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.r1123.fans.core.type.CategoryType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.*;

/**
 * Created by helloqdz on 2018/11/15.
 */
@Slf4j
@Entity
@Component
@Inheritance(strategy = InheritanceType.JOINED)
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Getter
@Setter
@Table(name = "BS_CATEGORY")
@JsonIgnoreProperties(ignoreUnknown = true, value =
        {"allChildCategoryXrefs","allParentCategoryXrefs","hibernateLazyInitializer", "handler", "fieldHandler"})
public class Category {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CATEGORY_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    private Date createdAt = new Date(); //录入日期

    private Date updatedAt = new Date(); //最近更新日期

    private Boolean archived = true;

    @Column(nullable = false)
    protected String name; // 类目名称

    protected String url;

    protected String description; // 描述

    protected Date activeStartDate; // 有效期

    protected Date activeEndDate; //有效期

    protected String icon;

    private Long displayOrder; // 优先级

    private Boolean isOnShow = true; // 是否展示

    @Lob
    @Column(length = Integer.MAX_VALUE - 1)
    protected String longDescription;

    @OneToMany(targetEntity = CategoryXref.class, mappedBy = "category", orphanRemoval = true, cascade = { CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH })
    @OrderBy(value = "displayOrder")
    protected List<CategoryXref> allChildCategoryXrefs = new ArrayList<CategoryXref>(10);

    @OneToMany(targetEntity = CategoryXref.class, mappedBy = "subCategory", orphanRemoval = true, cascade = { CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH })
    @OrderBy(value = "displayOrder")
    protected List<CategoryXref> allParentCategoryXrefs = new ArrayList<CategoryXref>(10);

   /* @OneToMany(targetEntity = CategoryProductXrefImpl.class, mappedBy = "category", orphanRemoval = true, cascade = { CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH })
    @OrderBy(value = "displayOrder")
    protected List<CategoryProductXref> allProductXrefs = new ArrayList<CategoryProductXref>(10);
*/
    /*@OneToMany(mappedBy = "category", targetEntity = CategoryMediaXrefImpl.class, cascade = { CascadeType.ALL, CascadeType.REMOVE }, orphanRemoval = true)
    @MapKey(name = "key")
    protected Map<String, CategoryMediaXref> categoryMedia = new HashMap<String, CategoryMediaXref>();*/

    /*@OneToMany(mappedBy = "category", targetEntity = FeaturedProductImpl.class, cascade = { CascadeType.ALL })
    //@OrderBy(value = "sequence")
    protected List<FeaturedProduct> featuredProducts = new ArrayList<FeaturedProduct>(10);

    @OneToMany(mappedBy = "category", targetEntity = CategoryAttributeImpl.class, cascade = { CascadeType.ALL }, orphanRemoval = true)
    // @MapKey(name = "name")
    protected List<CategoryAttribute> categoryAttributes = new ArrayList<CategoryAttribute>(10);
*/
    @Column(name = "INVENTORY_TYPE")
    protected String inventoryType;

    @Column(name = "FULFILLMENT_TYPE")
    protected String fulfillmentType;

    @Column(name = "CATEGORY_TYPE")
    protected String categoryType;

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "DEFAULT_PARENT_CATEGORY_ID")
    protected Category defaultParentCategory;
/*

	@OneToMany(targetEntity = CategoryImpl.class, orphanRemoval = false, cascade = {CascadeType.REFRESH})
	protected List<Category> showParents = new ArrayList<Category>(5);
*/


    @Transient
    protected List<CategoryXref> childCategoryXrefs = new ArrayList<CategoryXref>(50);

    @Transient
    protected List<Category> childCategories = new ArrayList<Category>(50);

    @Transient
    protected List<Category> allLegacyChildCategories = new ArrayList<Category>(50);

/*    @Transient
    protected List<FeaturedProduct> filteredFeaturedProducts = null;*/

    /*@Transient
    protected Map<String, Media> legacySkuMedia = new HashMap<String, Media>();
*/
    public List<CategoryXref> getChildCategoryXrefs() {
        if (childCategoryXrefs.isEmpty()) {
            for (CategoryXref category : allChildCategoryXrefs) {
                if (category.getSubCategory().isActive()) {
                    childCategoryXrefs.add(category);
                }
            }
        }
        return Collections.unmodifiableList(childCategoryXrefs);
    }

    public void setChildCategoryXrefs(List<CategoryXref> childCategories) {
        this.childCategoryXrefs.clear();
        for (CategoryXref category : childCategories) {
            this.childCategoryXrefs.add(category);
        }
    }

    public List<Category> getChildCategories() {
        List<Category> childCategory = new ArrayList<Category>();
        for (CategoryXref category : allChildCategoryXrefs) {
            if (category.getSubCategory().isActive()) {
                childCategory.add(category.getSubCategory());
            }
        }
        return Collections.unmodifiableList(childCategory);
    }

    public List<Category> getAllparents(){
        List<CategoryXref> xrefs = getAllParentCategoryXrefs();
        List<Category> parents;
        if (CollectionUtils.isEmpty(xrefs)){
            parents = Collections.emptyList();
        }else {
            parents = new ArrayList<Category>(xrefs.size());
            for (CategoryXref x: xrefs){
                parents.add(x.getCategory());
            }
        }
        return parents;
    }

    public Category getParentCategory() {
        // TODO Auto-generated method stub
        Category response = null;
        List<CategoryXref> xrefs = getAllParentCategoryXrefs();
        if (!CollectionUtils.isEmpty(xrefs)) {
            for (CategoryXref xref : xrefs) {
                if (xref.getCategory().isActive() && xref.getDefaultReference() != null && xref.getDefaultReference()) {
                    response = xref.getCategory();
                    break;
                }
            }
        }
        if (response == null) {
            if (!CollectionUtils.isEmpty(xrefs)) {
                for (CategoryXref xref : xrefs) {
                    if (xref.getCategory().isActive()) {
                        response = xref.getCategory();
                        break;
                    }
                }
            }
        }
        return response;
    }

    public void setParentCategory(Category category) {
        // TODO Auto-generated method stub
        List<CategoryXref> xrefs = getAllParentCategoryXrefs();
        boolean found = false;
        for (CategoryXref xref : xrefs) {
            if (xref.getCategory().equals(category)) {
                xref.setDefaultReference(true);
                found = true;
            } else if (xref.getDefaultReference() != null && xref.getDefaultReference()) {
                xref.setDefaultReference(null);
            }
        }
        if (!found && category != null) {
            CategoryXref xref = new CategoryXref();
            xref.setSubCategory(this);
            xref.setCategory(category);
            xref.setDefaultReference(true);
            allParentCategoryXrefs.add(xref);
        }
    }

    public boolean isActive() {
        // TODO Auto-generated method stub
        /*if (log.isDebugEnabled()) {
            if (!DateUtil.isActive(getActiveStartDate(), getActiveEndDate(), true)) {
                log.debug("product, " + id + ", inactive due to date");
            }
            if (!getArchived()) {
                log.debug("product, " + id + ", inactive due to archived status");
            }
        }*/
        //return DateUtil.isActive(getActiveStartDate(), getActiveEndDate(), true) && getArchived();
        return true;
    }

   /* public void setCategoryMedia(Map<String, Media> categoryMedia) {
        this.categoryMedia.clear();
        for (Map.Entry<String, Media> entry : categoryMedia.entrySet()) {
            this.categoryMedia.put(entry.getKey(), new CategoryMediaXrefImpl(this, entry.getValue(), entry.getKey()));
        }
    }

    public Map<String, Media> getCategoryMedia() {
        if (legacySkuMedia.size() == 0) {
            for (Map.Entry<String, CategoryMediaXref> entry : getCategoryMediaXref().entrySet()) {
                legacySkuMedia.put(entry.getKey(), entry.getValue().getMedia());
            }
        }
        return Collections.unmodifiableMap(legacySkuMedia);
    }

    public Map<String, CategoryMediaXref> getCategoryMediaXref() {
        return categoryMedia;
    }

    public Set<Product> getShowProducts() {
        return showproducts;
    }*/
/*
	@Override
	public Boolean addShowParents(Category category) {
		if (category != null){
			for ( Category c: showParents ){
				if ( c.getId() == category.getId() ){
					return false;
				}
			}
			showParents.add(category);
			return true;
		}
		return false;
	}*/

    public CategoryType getCategoryType() {
        return CategoryType.getInstance(categoryType);
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType.getType();
    }

    public Category getDefaultParentCategory() {
        Category response;
        if (defaultParentCategory != null) {
            response = defaultParentCategory;
        } else {
            response = getParentCategory();
        }
        return response;
    }

    /*public List<Product> getActiveProducts() {
        // TODO Auto-generated method stub
        List<Product> result = new ArrayList<Product>();
        for (CategoryProductXref product : allProductXrefs) {
            if (product.getProduct().isActive()) {
                result.add(product.getProduct());
            }
        }
        return Collections.unmodifiableList(result);
    }



    public List<CategoryAttribute> getCategoryAttributes() {
        return categoryAttributes;
    }

    public boolean isContain(Product product) {
        // TODO Auto-generated method stub
        for (Product prod : getActiveProducts()) {
            if (prod.getId().equals(product.getId())) {
                return true;
            }
        }
        return false;
    }*/

}
