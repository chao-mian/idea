package com.zhbit.oa.dao;

import com.zhbit.oa.domain.Permission;

import java.util.List;

public interface PermissionMapper {
    boolean insert(Permission permission);

    List<Permission> selectAll();

    Permission selecrByPid(String pid);

    boolean delete(String pid);

    boolean update(Permission permission);
}
