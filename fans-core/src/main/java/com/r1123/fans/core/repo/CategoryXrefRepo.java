package com.r1123.fans.core.repo;

import com.r1123.fans.core.entity.Category;
import com.r1123.fans.core.entity.CategoryXref;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by helloqdz on 2018/11/15.
 */
@Repository("bsCategoryXrefRepos")
public interface CategoryXrefRepo extends JpaRepository<CategoryXref,Long>,JpaSpecificationExecutor<CategoryXref>,Serializable {

    List<CategoryXref> findByCategory(Category category);

}
