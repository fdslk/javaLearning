package com.zengqiangfang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author bestpay
 * @version V1.0
 * @Title: IndexController
 * @Package
 * @Description: TODO
 * @date
 */
@Controller
public class IndexController {
    @RequestMapping("/index")
    public String index(){
        return "index-page";
    }
}
