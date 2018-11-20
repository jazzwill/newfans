package com.r1123.fans.admin.controller;

import com.r1123.fans.core.repo.CategoryRepo;
import com.r1123.fans.core.repo.CategoryXrefRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

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
