package com.r1123.fans.admin.controller;

import com.r1123.fans.core.entity.Category;
import com.r1123.fans.core.entity.CategoryXref;
import com.r1123.fans.core.repo.CategoryRepo;
import com.r1123.fans.core.repo.CategoryXrefRepo;
import com.r1123.fans.core.type.CategoryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by helloqdz on 2018/10/21.
 */
@Controller
@RequestMapping("/hello")
public class HelloController {

    @Resource(name = "bsCategoryRepos")
    CategoryRepo repo;

    @Resource(name = "bsCategoryXrefRepos")
    CategoryXrefRepo xrefRepo;

    @RequestMapping(value = "/date", method = RequestMethod.GET)
    private @ResponseBody String hello(@RequestParam("params") String params){


        return "中文日期：" + new Date().toString();
    }
    @RequestMapping(value = "/date2", method = RequestMethod.DELETE)
    private @ResponseBody String hello2(){
        return "hello2" + new Date().toString();
    }
}
