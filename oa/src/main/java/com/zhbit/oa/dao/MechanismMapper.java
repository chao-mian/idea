package com.zhbit.oa.dao;

import com.zhbit.oa.domain.Mechanism;

import java.util.List;

public interface MechanismMapper {
    boolean addMechanism(Mechanism mechanism);

    Mechanism selectbyMid(String mid);

    Mechanism selectbymname(String mname);

    List<Mechanism> findAll();

    boolean deleteMechanism(String mid);

    boolean updateMechanism(Mechanism mechanism);
}
