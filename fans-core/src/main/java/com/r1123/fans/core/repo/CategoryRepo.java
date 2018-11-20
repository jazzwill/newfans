package com.r1123.fans.core.repo;

import com.r1123.fans.core.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by helloqdz on 2018/11/15.
 */
@Repository("bsCategoryRepos")
public interface CategoryRepo extends JpaRepository<Category,Long>,JpaSpecificationExecutor<Category>,Serializable {



}
