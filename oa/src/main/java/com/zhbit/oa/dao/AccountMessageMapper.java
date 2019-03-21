package com.zhbit.oa.dao;

import com.zhbit.oa.domain.AccountMessage;

import java.util.List;

public interface AccountMessageMapper {
    AccountMessage selectByaMid(String aMid);

    AccountMessage selectByAid(String aid);

    List<AccountMessage> selectAll();

    boolean updateMessage(AccountMessage accountMessage);

    boolean addAccountMessage(AccountMessage accountMessage);
}
