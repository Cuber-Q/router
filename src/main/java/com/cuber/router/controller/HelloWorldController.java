package com.cuber.router.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * test demo controller
 */
@RestController
public class HelloWorldController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello, springboot";
    }

    @RequestMapping("/helloJson")
    public JSONObject helloJson() {
        JSONObject json = new JSONObject();
        json.put("user","cuber");
        return json;
    }
}
