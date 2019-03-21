package com.zhbit.oa.dao;

import com.zhbit.oa.domain.CharacterPermission;

import java.util.List;

public interface CharacterPermissionMapper {
    List<CharacterPermission> selectAll();

    CharacterPermission selectByCpid(String cpid);

    List<CharacterPermission> selectByCid(String cid);

    boolean insert(CharacterPermission characterPermission);

    boolean delete(String cid);

    boolean update(CharacterPermission characterPermission);

}
