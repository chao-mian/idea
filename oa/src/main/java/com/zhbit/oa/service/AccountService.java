package com.zhbit.oa.service;

import com.zhbit.oa.domain.Account;
import com.zhbit.oa.domain.AccountMessage;

import java.util.List;

public interface AccountService {
    String login(Account account);

    List<Account> findAll();

    Account findOne(String aid);

    AccountMessage findMessageByAid(String aid);

    boolean deleteAccount(String aid[]);

    boolean updateAccount(Account account);

    List accountList();

    boolean addAccount(Account account);
}
