package com.zhbit.oa.service;

import com.zhbit.oa.domain.Processes;
import com.zhbit.oa.domain.Processes1;


import java.util.List;

public interface ProcessesService {
    List<Processes> getAllProcessesList();
    List<Processes1> getAllProcesses1List();
    List<Processes> getActivityProcessesList(String username);
    List<Processes1> getOnepageProcesses1(List<Processes1> list, Integer limit, Integer page);
    List<Processes> getOnepageDate(List<Processes> list,Integer limit,Integer page);
    List<Processes> getSearchDate(List<Processes> list, String inquire);
    boolean addProcesses(Processes1 processes1);
}
