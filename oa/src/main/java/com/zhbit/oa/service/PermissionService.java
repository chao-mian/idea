package com.zhbit.oa.service;

import com.zhbit.oa.domain.Character;
import com.zhbit.oa.domain.CharacterPermission;
import com.zhbit.oa.domain.Permission;

import java.util.List;

public interface PermissionService {
    //对应权限表的
    boolean addPermission(Permission permission);

    List<Permission> findAll();

    Permission findOne(String pid);

    boolean deletePermission(String pid[]);

    boolean updatePermission(Permission permission);

    //对应角色表的
    List<Character> findAllCharacter();

    Character findByCid(String cid);

    Character findByCname(String cname);

    boolean addCharacter(Character character);

    boolean deleteCharacter(String cid[]);

    boolean updateCharacter(String str[]);

    //对应角色权限链表的
    List<CharacterPermission> findByCidInCp(String cid);

    boolean addCharacterPermission(String str[]);

}
