package com.zhbit.oa.service;

import com.zhbit.oa.domain.Bulletin;
import com.zhbit.oa.domain.BulletinAccount;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface BulletinService {
    List<Bulletin> getAllBulletin();
    Bulletin getOneBulletin(String bid);
    List<Bulletin> getOnepageBulletin(List<Bulletin> list, Integer limit, Integer page);
    public List<Bulletin> getSearchBulletin(List<Bulletin> list, String inquire);
    boolean addBulletin(Bulletin bulletin);

//    boolean addBulletinAccount(Bulletin bulletin);
}
