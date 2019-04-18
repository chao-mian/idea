package com.zhbit.oa.dao;

import com.zhbit.oa.domain.News;

import java.util.List;

public interface NewsMapper {
    List<News> selectAll();

    News selecrByNid(String nid);

    boolean insert(News news);

    boolean delete(String nid);

    boolean update(News news);
}
