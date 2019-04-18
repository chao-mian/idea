package com.zhbit.oa.controller;


import com.zhbit.oa.domain.Account;
import com.zhbit.oa.domain.AccountMessage;
import com.zhbit.oa.domain.LayuiNews;
import com.zhbit.oa.domain.News;
import com.zhbit.oa.service.AccountMessageService;
import com.zhbit.oa.service.NewsService;
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
public class NewsController {

    @Autowired
    private NewsService newsService;
    @Autowired
    private AccountMessageService accountMessageService;

    private String id;

    @RequestMapping(value = "/getNewsId")
    public String GetNewsId(@RequestBody Object nid) {
        this.id = nid.toString();
        System.out.println("get the nid is-------" + nid);
        return "no";
    }

    @RequestMapping(value = "/showNewsTable")
    public String ShowNewsTable(Model model) {
        String url = "/getNewsTable";
        model.addAttribute("url", url);
        return "newsTable";
    }

    @RequestMapping(value = "/showMyNewsTable")
    public String ShowMyNewsTable(Model model) {
        String url = "/getNewsTable";
        model.addAttribute("url", url);
        return "mainNewsTable";
    }

    @ResponseBody
    @RequestMapping(value = "/getNewsTable")
    public LayuiNews GetNewsTable(HttpServletRequest request) {
        Integer limit = Integer.parseInt(request.getParameter("limit"));
        Integer page = Integer.parseInt(request.getParameter("page"));
        String inquire = request.getParameter("inquire");
        System.out.println("inquire--------" + inquire);
        List<News> allNewslist = newsService.getAllNews();

        System.out.println("allNewslist-------------" + allNewslist);
        List<News> showNewsList = new ArrayList<>();
        if (inquire == null) {
            showNewsList = newsService.getOnepageNews(allNewslist, limit, page);
        } else {
            showNewsList = newsService.getSearchNews(allNewslist, inquire);
        }
        LayuiNews layuiNews = new LayuiNews();
        layuiNews.setCode("0");
        layuiNews.setMsg("成功");
        layuiNews.setCount(String.valueOf(showNewsList.size()));
        layuiNews.setData(showNewsList);
        return layuiNews;
    }

    @RequestMapping(value = "/insertNews")
    public String InsertNews(News news, HttpServletRequest request) {
        Account user = (Account) request.getSession().getAttribute("loginUser");
        AccountMessage accountMessage = accountMessageService.findByAid(user.getAusername());
        String username = accountMessage.getaMname();
        news.setNsend(username);
        newsService.addNews(news);
        return "ok";
    }

    @RequestMapping(value = "/deleteNews")
    public String DeleteNews() {
        newsService.deleteNews(id);
        return "forward:/showNewsTable";
    }

    @RequestMapping(value = "/showTheNews")
    public String ShowTheNews(Model model){
        News news = newsService.getOneNews(id);
        model.addAttribute("news",news);
        return "showNews";
    }
}
