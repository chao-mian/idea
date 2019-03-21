package com.zhbit.oa.service;

import com.zhbit.oa.domain.AccountMessage;

import java.util.List;

public interface AccountMessageService {
    AccountMessage findByaMid(String aMid);

    AccountMessage findByAid(String aid);

    List<AccountMessage> findAll();

    boolean updateMessage(AccountMessage accountMessage);

    boolean addAccountMessage(AccountMessage accountMessage);
}
