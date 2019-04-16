package com.zhbit.oa.dao;

import com.zhbit.oa.domain.Bulletin;

import java.util.List;

public interface BulletinMapper {
    boolean add(Bulletin bulletin);
    List<Bulletin> selectAll();
    Bulletin selectByBid(String bid);
    boolean delete(String bid);
    boolean update(Bulletin bulletin);
}
