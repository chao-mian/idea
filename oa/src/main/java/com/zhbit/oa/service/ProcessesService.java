package com.zhbit.oa.service;

import com.zhbit.oa.domain.Processes;


import java.util.List;

public interface ProcessesService {
    List<Processes> getAllProcessesList();
    List<Processes> getActivityProcessesList(String username);
}
