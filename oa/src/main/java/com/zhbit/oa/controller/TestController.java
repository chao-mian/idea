package com.zhbit.oa.controller;

import com.zhbit.oa.domain.Account;
import com.zhbit.oa.domain.LayuiJson;
import com.zhbit.oa.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TestController {

    @Autowired
    private AccountService accountService;

    @ResponseBody
    @RequestMapping(value = "/testDemo")
    public LayuiJson TestDemo() {
        List<Account> list = accountService.findAll();
        LayuiJson test = new LayuiJson();
        test.setCode("0");
        test.setMsg("成功");
        test.setCount(String.valueOf(list.size()));
//        test.setData(list);
        return test;
    }
}
