package com.zhbit.oa.serviceBean;

import com.zhbit.oa.domain.Processes;
import com.zhbit.oa.service.ProcessesService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessesServiceImpl implements ProcessesService {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;

    @Override
    public List<Processes> getAllProcessesList() {

        List<HistoricProcessInstance> list = historyService
                .createHistoricProcessInstanceQuery()
                .orderByProcessInstanceStartTime().asc()//排序
                .list();

        //获取所有流程
        List<Processes> processesList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
//            for (HistoricActivityInstance hpi : list) {
                Processes processes = new Processes();
                System.out.println("流程定义ID：" + list.get(i).getProcessDefinitionId());
                System.out.println("流程实例ID：" + list.get(i).getId());
                System.out.println("流程名：" + repositoryService.createProcessDefinitionQuery().processDefinitionId(list.get(i).getProcessDefinitionId()).singleResult().getName());
                System.out.println("开始时间：" + list.get(i).getStartTime());
                System.out.println("结束时间：" + list.get(i).getEndTime());
                System.out.println("流程持续时间：" + list.get(i).getDurationInMillis());
                System.out.println("流程创建人：" + list.get(i).getStartUserId());
                if (taskService.createTaskQuery().processInstanceId(list.get(i).getId()).singleResult() != null) {
                    System.out.println(taskService.createTaskQuery().processInstanceId(list.get(i).getId()).singleResult().getName());
                    processes.setProcessesTask(taskService.createTaskQuery().processDefinitionId(list.get(i).getProcessDefinitionId()).singleResult().getName());
                } else {
                    processes.setProcessesTask("结束");
                }
                System.out.println("=======================================");
                processes.setProcessesDefinitionId(list.get(i).getProcessDefinitionId());
                processes.setProcessesName(repositoryService.createProcessDefinitionQuery().processDefinitionId(list.get(i).getProcessDefinitionId()).singleResult().getName());
                processes.setProcessesStartUser(list.get(i).getStartUserId());
                //转换日期格式
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                processes.setProcessesStartTime(sdf.format(list.get(i).getStartTime()));
                processesList.add(processes);
            }
        }
        System.out.println("processesList------" + processesList);
        return processesList;
    }

    @Override
    public List<Processes> getActivityProcessesList(String username) {
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
                .orderByHistoricActivityInstanceStartTime().asc()//排序
                .list();
        List<Processes> processesList = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Processes processes = new Processes();
                System.out.println("历史活动");
                System.out.println("流程定义ID：" + list.get(i).getProcessDefinitionId());
                System.out.println("流程实例ID：" + list.get(i).getId());
                System.out.println("流程名：" + list.get(i).getActivityName());
                System.out.println("开始时间：" + list.get(i).getStartTime());
                System.out.println("结束时间：" + list.get(i).getEndTime());
                System.out.println("流程持续时间：" + list.get(i).getDurationInMillis());
                System.out.println("流程节点id：" + list.get(i).getTaskId());

                System.out.println("流程操作者：" + list.get(i).getAssignee());
                System.out.println("=======================================");

                if (!list.get(i).getActivityName().equals("StartEvent")&&!list.get(i).getActivityName().equals("EndEvent")) {
                    if (list.get(i).getEndTime() != null && list.get(i).getAssignee().equals(username)) {
                        HistoricProcessInstance historicProcessInstance = historyService
                                .createHistoricProcessInstanceQuery().processDefinitionId(list.get(i).getProcessDefinitionId()).singleResult();
                        processes.setProcessesDefinitionId(list.get(i).getProcessDefinitionId());
                        processes.setProcessesName(repositoryService.createProcessDefinitionQuery().processDefinitionId(list.get(i).getProcessDefinitionId()).singleResult().getName());
                        processes.setProcessesStartUser(historicProcessInstance.getStartUserId());
                        //转换日期格式
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        processes.setProcessesStartTime(sdf.format(list.get(i).getStartTime()));
                        if (taskService.createTaskQuery().processDefinitionId(list.get(i).getProcessDefinitionId()).singleResult() != null) {
                            System.out.println(taskService.createTaskQuery().processDefinitionId(list.get(i).getProcessDefinitionId()).singleResult().getName());
                            processes.setProcessesTask(taskService.createTaskQuery().processDefinitionId(list.get(i).getProcessDefinitionId()).singleResult().getName());
                        } else {
                            processes.setProcessesTask("结束");
                        }
                        processesList.add(processes);
                    }
                }
            }
        }
        System.out.println("processesService中输出activitylist-----" + processesList);
        return processesList;
    }
}
