package com.zhbit.oa.controller;

import com.zhbit.oa.domain.Bulletin;
import com.zhbit.oa.domain.LayuiBulletin;
import com.zhbit.oa.service.BulletinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BulletinController {

    @Autowired
    private BulletinService bulletinService;

    private String id;

    @RequestMapping(value = "/getBulletinId")
    public String GetBulletinId() {

        return "no";
    }

    @RequestMapping(value = "/showBulletinTable")
    public String ShowBulletinTable(Model model) {
        String url = "/getBulletinTable";
        model.addAttribute("url", url);
        return "bulletinTable";
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
}
