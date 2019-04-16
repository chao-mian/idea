package com.zhbit.oa.serviceBean;

import com.zhbit.oa.dao.BulletinMapper;
import com.zhbit.oa.domain.Bulletin;
import com.zhbit.oa.service.BulletinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BulletinServiceImpl implements BulletinService {

    @Autowired
    private BulletinMapper bulletinMapper;

    @Override
    public List<Bulletin> getAllBulletin() {
       return bulletinMapper.selectAll();
    }

    //分页操作单页数据
    @Override
    public List<Bulletin> getOnepageBulletin(List<Bulletin> list, Integer limit, Integer page) {
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
        List<Bulletin> bulletinList = new ArrayList<>();
        for (int i = listStart; i < listEnd; i++) {
            bulletinList.add(list.get(i));
        }
        return bulletinList;
    }

    //根据关键字查询
    @Override
    public List<Bulletin> getSearchBulletin(List<Bulletin> list, String inquire) {
        List<Bulletin> bulletilnist = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getBsend().indexOf(inquire) >= 0
                    || list.get(i).getBtime().indexOf(inquire) >= 0
                    || list.get(i).getBtitle().indexOf(inquire) >= 0) {
                bulletilnist.add(list.get(i));
            }
        }
        return bulletilnist;
    }
}
