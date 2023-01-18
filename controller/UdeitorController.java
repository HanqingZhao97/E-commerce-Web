package com.yunniu.lease.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UdeitorController {

    //百度富文本转发
    @RequestMapping("/config")
    public String config() {
        return "redirect:/js/ueditor/jsp/config.json";
    }


}
