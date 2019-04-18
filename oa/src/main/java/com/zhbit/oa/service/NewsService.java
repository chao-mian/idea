package com.zhbit.oa.service;

import com.zhbit.oa.domain.News;

import java.util.List;

public interface NewsService {
    List<News> getAllNews();
    News getOneNews(String nid);
    boolean addNews(News news);
    boolean deleteNews(String nid);
    List<News> getOnepageNews(List<News> list,Integer limit,Integer page);
    List<News> getSearchNews(List<News> list,String inquire);
}
