package com.zhbit.oa.dao;

import com.zhbit.oa.domain.Account;

import java.util.List;

public interface AccountMapper {
    Account login(Account account);

    Account selectByAid(String aid);

    boolean addAccount(Account account);

    boolean deleteAccount(String aid);

    boolean updateAccount(Account account);

    boolean updateByAusername(Account account);

    List<Account> findAll();
}
