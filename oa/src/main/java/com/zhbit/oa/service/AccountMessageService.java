package com.zhbit.oa.service;

import com.zhbit.oa.domain.AccountMessage;

import java.util.List;

public interface AccountMessageService {
    AccountMessage findByaMid(String aMid);

    AccountMessage findByAid(String aid);

    AccountMessage findByAMname(String aMname);

    List<AccountMessage> findAll();

    boolean updateMessage(AccountMessage accountMessage);

    boolean addAccountMessage(AccountMessage accountMessage);

    List<AccountMessage> getOnepageAccountMessage(List<AccountMessage> list, Integer limit, Integer page);

    List<AccountMessage> getSearchAccountMessage(List<AccountMessage> list, String inquire);
}
