package com.r1123.fans.site.controller;

import com.r1123.fans.core.common.GlobalErrorCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by helloqdz on 2018/11/19.
 */
@RestController("siteController")
@RequestMapping("/site")
public class LoginController {

    @RequestMapping("/login")
    public Object loginPage() {
        return GlobalErrorCode.UNAUTHORIZED;
    }
}
