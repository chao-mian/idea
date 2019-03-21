package com.zhbit.oa.dao;

import com.zhbit.oa.domain.Character;

import java.util.List;

public interface CharacterMapper {
    List<Character> selectAll();

    Character selectByCid(String cid);

    Character selectByCname(String cname);

    boolean insert(Character character);

    boolean delete(String cid);

    boolean update(Character character);
}
