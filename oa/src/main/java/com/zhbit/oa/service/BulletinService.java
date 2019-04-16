package com.zhbit.oa.service;

import com.zhbit.oa.domain.Bulletin;

import java.util.List;

public interface BulletinService {
    List<Bulletin> getAllBulletin();
    List<Bulletin> getOnepageBulletin(List<Bulletin> list, Integer limit, Integer page);
    public List<Bulletin> getSearchBulletin(List<Bulletin> list, String inquire);
}
