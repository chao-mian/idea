package com.zhbit.oa.dao;

import com.zhbit.oa.domain.BulletinAccount;

import java.util.List;

public interface BulletinAccountMapper {
    List<BulletinAccount> selectAll();
    BulletinAccount selectByBaid(String baid);
    BulletinAccount selectByBidAndAid(BulletinAccount bulletinAccount);
    List<BulletinAccount> selectByBidAndBastatus(BulletinAccount bulletinAccount);
    BulletinAccount selectByBstatus(String bastatus);
    boolean add(BulletinAccount bulletinAccount);
    boolean delete(String baid);
    boolean deleteByBid(String bid);
    boolean update(BulletinAccount bulletinAccount);
}
