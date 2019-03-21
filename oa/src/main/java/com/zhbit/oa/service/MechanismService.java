package com.zhbit.oa.service;

import com.zhbit.oa.domain.Mechanism;

import java.util.List;

public interface MechanismService {
    boolean addMechanism(Mechanism mechanism);

    String newMid();

    Mechanism findOne(String mid);

    Mechanism findByMname(String mname);

    List<Mechanism> findAll();

    boolean deleteMechanism(String mid[]);

    boolean updateMechanism(Mechanism mechanism);
}
