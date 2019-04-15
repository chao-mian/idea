package com.zhbit.oa.dao;

import com.zhbit.oa.domain.Processes1;

import java.util.List;

public interface ProcessesMapper {
    List<Processes1> selectAll();
    Processes1 selecrByPrid(String prid);
    boolean insert(Processes1 processes1);
    boolean delete(String prid);
}
