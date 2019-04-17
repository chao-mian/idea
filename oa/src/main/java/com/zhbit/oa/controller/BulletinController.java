package com.zhbit.oa.controller;

import com.zhbit.oa.domain.Account;
import com.zhbit.oa.domain.AccountMessage;
import com.zhbit.oa.domain.Bulletin;
import com.zhbit.oa.domain.LayuiBulletin;
import com.zhbit.oa.service.AccountMessageService;
import com.zhbit.oa.service.BulletinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;

import java.util.List;

@Controller
public class BulletinController {

    @Autowired
    private BulletinService bulletinService;
    @Autowired
    private AccountMessageService accountMessageService;


    private String id;

    @RequestMapping(value = "/getBulletinId")
    public String GetBulletinId(@RequestBody Object bid) {
        this.id = bid.toString();
        System.out.println("get the bid is-------"+bid);
        return "no";
    }

    @RequestMapping(value = "/showTheBulletin")
    public String ShowTheBulletin(Model model){
        Bulletin bulletin =bulletinService.getOneBulletin(id);
        System.out.println("bulletin---------"+bulletin);
        model.addAttribute("bulletin",bulletin);
        return "showBulletin";
    }

    @RequestMapping(value = "/showBulletinTable")
    public String ShowBulletinTable(Model model) {
        String url = "/getBulletinTable";
        model.addAttribute("url", url);
        return "bulletinTable";
    }
    @RequestMapping(value = "/showMyBulletinTable")
    public String ShowMyBulletinTable(Model model) {
        String url = "/geMytBulletinTable";
        model.addAttribute("url", url);
        return "mainBulletinTable";
    }

    @ResponseBody
    @RequestMapping(value = "/geMytBulletinTable")
    public LayuiBulletin GetMyBulletinTable(HttpServletRequest request) {
        Integer limit = Integer.parseInt(request.getParameter("limit"));
        Integer page = Integer.parseInt(request.getParameter("page"));
        String inquire = request.getParameter("inquire");
        System.out.println("inquire--------" + inquire);
        List<Bulletin> allBulletinlist = bulletinService.getMyBulletin();

        System.out.println("allBulletinlist-------------" + allBulletinlist);
        List<Bulletin> showBulletinList = new ArrayList<>();
        if (inquire == null) {
            showBulletinList = bulletinService.getOnepageBulletin(allBulletinlist, limit, page);
        } else {
            showBulletinList = bulletinService.getSearchBulletin(allBulletinlist, inquire);
        }
        LayuiBulletin layuiBulletin = new LayuiBulletin();
        layuiBulletin.setCode("0");
        layuiBulletin.setMsg("成功");
        layuiBulletin.setCount(String.valueOf(allBulletinlist.size()));
        layuiBulletin.setData(showBulletinList);
        return layuiBulletin;
    }

    @ResponseBody
    @RequestMapping(value = "/getBulletinTable")
    public LayuiBulletin GetBulletinTable(HttpServletRequest request) {
        Integer limit = Integer.parseInt(request.getParameter("limit"));
        Integer page = Integer.parseInt(request.getParameter("page"));
        String inquire = request.getParameter("inquire");
        System.out.println("inquire--------" + inquire);
        List<Bulletin> allBulletinlist = bulletinService.getAllBulletin();

        System.out.println("allBulletinlist-------------" + allBulletinlist);
        List<Bulletin> showBulletinList = new ArrayList<>();
        if (inquire == null) {
            showBulletinList = bulletinService.getOnepageBulletin(allBulletinlist, limit, page);
        } else {
            showBulletinList = bulletinService.getSearchBulletin(allBulletinlist, inquire);
        }
        LayuiBulletin layuiBulletin = new LayuiBulletin();
        layuiBulletin.setCode("0");
        layuiBulletin.setMsg("成功");
        layuiBulletin.setCount(String.valueOf(allBulletinlist.size()));
        layuiBulletin.setData(showBulletinList);
        return layuiBulletin;
    }

    @RequestMapping(value = "/insertBulletin")
    public String InsertBulletin(Bulletin bulletin, HttpServletRequest request) {
        //获取登录用户
        Account user = (Account) request.getSession().getAttribute("loginUser");
        AccountMessage accountMessage = accountMessageService.findByAid(user.getAusername());
        String username = accountMessage.getaMname();
        bulletin.setBsend(username);

        bulletinService.addBulletin(bulletin);

        return "ok";
    }

    @RequestMapping(value = "/deleteBulletin")
    public String DeleteBulletin(){
        bulletinService.deleteBulletin(id);
        return "forward:/showBulletinTable";
    }
}
