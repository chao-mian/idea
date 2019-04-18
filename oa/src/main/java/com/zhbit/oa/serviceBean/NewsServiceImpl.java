package com.zhbit.oa.serviceBean;

import com.zhbit.oa.dao.NewsMapper;
import com.zhbit.oa.domain.News;
import com.zhbit.oa.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsMapper newsMapper;

    @Override
    public List<News> getAllNews() {
        return newsMapper.selectAll();
    }

    @Override
    public News getOneNews(String nid){
        return newsMapper.selecrByNid(nid);
    }

    @Override
    public boolean deleteNews(String nid){
        return newsMapper.delete(nid);

    }

    @Override
    public boolean addNews(News news) {
        news.setNid(newnid());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        news.setNtime(df.format(new Date()));
        return newsMapper.insert(news);
    }
    //生成nid并匹配数据库是否已经存在该id
    public String newnid() {
        //生成16位位16进制数
        StringBuffer n = new StringBuffer();
        for (int i = 0; i < 16; i++) {
            n.append(Integer.toHexString(new Random().nextInt(16)));
        }
        String nid = n.toString().toUpperCase();
        //数据库中查找该bid，能查到则重新生成
        if (newsMapper.selecrByNid(nid) != null) {
            System.out.println("nid已存在，重新生成");
            return newnid();
        }
        return nid;
    }
    //分页操作单页数据
    @Override
    public List<News> getOnepageNews(List<News> list, Integer limit, Integer page) {
        Integer pagesize = list.size() / limit + 1;
        Integer listStart = (page - 1) * limit;
        Integer listEnd;
        if (page != pagesize) {
            listEnd = (page - 1) * limit + limit - 1;
        } else {
            listEnd = list.size();
        }
        System.out.println("pagesize--------" + pagesize);
        System.out.println("listStart--------" + listStart);
        System.out.println("listEnd--------" + listEnd);
        List<News> newsList = new ArrayList<>();
        for (int i = listStart; i < listEnd; i++) {
            newsList.add(list.get(i));
        }
        return newsList;
    }

    //根据关键字查询
    @Override
    public List<News> getSearchNews(List<News> list, String inquire) {
        List<News> newsList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getNtitle().indexOf(inquire) >= 0
                    || list.get(i).getNsend().indexOf(inquire) >= 0
                    || list.get(i).getNtime().indexOf(inquire) >= 0
                    || list.get(i).getNtype().indexOf(inquire) >= 0) {
                newsList.add(list.get(i));
            }
        }
        return newsList;
    }
}
