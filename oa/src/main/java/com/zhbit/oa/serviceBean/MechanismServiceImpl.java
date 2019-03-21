package com.zhbit.oa.serviceBean;

import com.zhbit.oa.dao.MechanismMapper;
import com.zhbit.oa.domain.Mechanism;
import com.zhbit.oa.service.MechanismService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MechanismServiceImpl implements MechanismService {
    @Autowired
    private MechanismMapper mechanismMapper;

    //添加部门逻辑函数
    public boolean addMechanism(Mechanism mechanism) {
        //将生成的mid赋予mechanism
        mechanism.setMid(newMid());
        mechanism.setMpeopleNum(0);
        System.out.println("service中输出" + mechanism);
        if (mechanismMapper.addMechanism(mechanism)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteMechanism(String mid[]) {
        for (int i = 0; i < mid.length; i++) {
            mechanismMapper.deleteMechanism(mid[i]);
        }
        return true;
    }

    //生成mid并匹配数据库是否已经存在该id
    public String newMid() {
        //生成两位16进制数
        StringBuffer m = new StringBuffer();
        for (int i = 0; i < 2; i++) {
            m.append(Integer.toHexString(new Random().nextInt(16)));
        }
        String mid = m.toString().toUpperCase();
        //数据库中查找该mid，能查到则重新生成
        if (mechanismMapper.selectbyMid(mid) != null) {
            System.out.println("mid已存在，重新生成");
            return newMid();
        }
        return mid;
    }

    //更新操作
    public boolean updateMechanism(Mechanism mechanism) {
        mechanismMapper.updateMechanism(mechanism);
        return true;
    }

    //获取相应mid数据
    public Mechanism findOne(String mid) {
        Mechanism mechanism = mechanismMapper.selectbyMid(mid);
        return mechanism;
    }

    public Mechanism findByMname(String mname) {
        Mechanism mechanism = mechanismMapper.selectbymname(mname);
        return mechanism;
    }

    //获取数据库中所有数据
    public List<Mechanism> findAll() {
        List<Mechanism> list = mechanismMapper.findAll();
        for (Mechanism mechanism : list) {
            if (mechanism.getMpeopleNum() == null) {
                mechanism.setMpeopleNum(0);
            }
            if (mechanism.getMdescription() == null) {
                mechanism.setMdescription(" ");
            }
        }
        return list;
    }
}
