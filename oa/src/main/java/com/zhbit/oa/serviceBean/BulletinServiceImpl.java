package com.zhbit.oa.serviceBean;

import com.zhbit.oa.dao.AccountMessageMapper;
import com.zhbit.oa.dao.BulletinAccountMapper;
import com.zhbit.oa.dao.BulletinMapper;
import com.zhbit.oa.domain.Account;
import com.zhbit.oa.domain.AccountMessage;
import com.zhbit.oa.domain.Bulletin;
import com.zhbit.oa.domain.BulletinAccount;
import com.zhbit.oa.service.BulletinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class BulletinServiceImpl implements BulletinService {

    @Autowired
    private BulletinMapper bulletinMapper;
    @Autowired
    private BulletinAccountMapper bulletinAccountMapper;
    @Autowired
    private AccountMessageMapper accountMessageMapper;

    @Override
    public List<Bulletin> getAllBulletin() {
        return bulletinMapper.selectAll();
    }

    @Override
    public Bulletin getOneBulletin(String bid) {
        return bulletinMapper.selectByBid(bid);
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

    @Override
    public boolean addBulletin(Bulletin bulletin) {
        bulletin.setBid(newbid());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        bulletin.setBtime(df.format(new Date()));
        System.out.println("bulletin----------" + bulletin);
        bulletinMapper.add(bulletin);
        addBulletinAccount(bulletin);
        return true;
    }

    //    @Override
    public boolean addBulletinAccount(Bulletin bulletin) {
        BulletinAccount bulletinAccount = new BulletinAccount();
        bulletinAccount.setBid(bulletin.getBid());
        bulletinAccount.setBastatus("N");
        List<AccountMessage> list = accountMessageMapper.selectAll();
        //给每个人标记未读
        for (int i = 0; i < list.size(); i++) {
            bulletinAccount.setBaid(newbaid());
            bulletinAccount.setAid(list.get(i).getAid());
            System.out.println("bulletinAccount--------------------" + bulletinAccount);
            bulletinAccountMapper.add(bulletinAccount);
        }
        return true;
    }

    //生成bid并匹配数据库是否已经存在该id
    public String newbid() {
        //生成16位位16进制数
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < 16; i++) {
            b.append(Integer.toHexString(new Random().nextInt(16)));
        }
        String bid = b.toString().toUpperCase();
        //数据库中查找该bid，能查到则重新生成
        if (bulletinMapper.selectByBid(bid) != null) {
            System.out.println("bid已存在，重新生成");
            return newbid();
        }
        return bid;
    }

    //生成baid并匹配数据库是否已经存在该id
    public String newbaid() {
        //生成16位位16进制数
        StringBuffer ba = new StringBuffer();
        for (int i = 0; i < 32; i++) {
            ba.append(Integer.toHexString(new Random().nextInt(16)));
        }
        String baid = ba.toString().toUpperCase();
        //数据库中查找该baid，能查到则重新生成
        if (bulletinAccountMapper.selectByBaid(baid) != null) {
            System.out.println("baid已存在，重新生成");
            return newbaid();
        }
        return baid;
    }


}
