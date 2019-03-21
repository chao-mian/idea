package com.zhbit.oa.dao;

import com.zhbit.oa.domain.AccountCharacter;

import java.util.List;

public interface AccountCharacterMapper {
    List<AccountCharacter> selectAll();

    List<AccountCharacter> selectByAid(String aid);

    boolean insert(AccountCharacter accountCharacter);

    boolean delete(String cid);
}
