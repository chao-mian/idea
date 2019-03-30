package com.zhbit.oa.controller;

import com.zhbit.oa.domain.LayuiJson;
import com.zhbit.oa.domain.Leave;
import com.zhbit.oa.domain.Processes;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProcesserController {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private FormService formService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private HistoryService historyService;

    String processId;
    String processDefinitionId;
    Map variables = new HashMap();


    @RequestMapping(value = "/start")
    public String Start1(Model model) {

        DeploymentBuilder builder = repositoryService.createDeployment();
        builder.addClasspathResource("processes/leave/leave.bpmn");
        builder.addClasspathResource("processes/leave/hr.form");
        builder.addClasspathResource("processes/leave/leader.form");
        builder.addClasspathResource("processes/leave/start.form");
        Deployment deployment = builder.deploy();
        //获取流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        processDefinitionId = processDefinition.getId();
//                 ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().singleResult();

        // 读取启动表单
        Object renderedStartForm = formService.getRenderedStartForm(processDefinition.getId());
//                 assertNotNull(renderedStartForm);
        System.out.println("表单内容---" + renderedStartForm.toString());
        model.addAttribute("form", renderedStartForm);
//        System.out.println("任务名称---"+deptLeaderTask.getName());
        // 启动流程
        // 设置当前用户
        /*ProcessInstance processInstance = formService.submitStartFormData(processDefinition.getId(), variables);
//                 assertNotNull(processInstance);
        processId = processInstance.getId();*/
        return "leave_start";
    }

    @RequestMapping(value = "/StartForm")
    public String Start(Leave leave, Model model) {

        System.out.println("接收到的值leave  " + leave);

        // 启动流程

//        variables.put("leave", leave);
        variables.put("name", leave.getName());
        variables.put("zhiwei", leave.getZhiwei());
        variables.put("bumen", leave.getBumen());
        variables.put("date", leave.getDate());
        variables.put("leaveType", leave.getLeaveType());
        variables.put("startDate", leave.getStartDate());
        variables.put("endDate", leave.getEndDate());
        variables.put("reason", leave.getReason());

        String currentUserId = leave.getName();
        identityService.setAuthenticatedUserId(currentUserId);
        System.out.println(processDefinitionId);
        ProcessInstance processInstance = formService.submitStartFormData(processDefinitionId, variables);
//                 assertNotNull(processInstance);
        processId = processInstance.getId();

        Task deptLeaderTask = taskService.createTaskQuery().processInstanceId(processId).singleResult();
//          ID       assertNotNull(formService.getRenderedTaskForm(deptLeaderTask.getId()));
        System.out.println("taskId:" + deptLeaderTask.getId() +
                ",taskName:" + deptLeaderTask.getName() +
                ",assignee:" + deptLeaderTask.getAssignee() +
                ",createTime:" + deptLeaderTask.getCreateTime());
        Object renderedTaskForm = formService.getRenderedTaskForm(deptLeaderTask.getId());
        System.out.println("表单内容---" + renderedTaskForm.toString());
        System.out.println("任务名称---" + deptLeaderTask.getName());
        model.addAttribute("form", renderedTaskForm);
        System.out.println("创建的流程id" + processId);
        return "leave_approval";
    }

    @RequestMapping(value = "/Shenpi")
    public String Shenpi(String leaderApproval, String leaderOpinion, Model model) {
        System.out.println("审批接收到" + leaderApproval);
        // 部门领导审批通过
        System.out.println("流程ID" + processId);
        variables = new HashMap<String, String>();
        variables.put("leaderApproval", leaderApproval);
        variables.put("leaderOpinion", leaderOpinion);
        Task deptLeaderTask = taskService.createTaskQuery().processInstanceId(processId).singleResult();
        System.out.println("领导审批流程节点id" + deptLeaderTask.getId());
        formService.submitTaskFormData(deptLeaderTask.getId(), variables);


        deptLeaderTask = taskService.createTaskQuery().processInstanceId(processId).singleResult();
//          ID       assertNotNull(formService.getRenderedTaskForm(deptLeaderTask.getId()));
        System.out.println("taskId:" + deptLeaderTask.getId() +
                ",taskName:" + deptLeaderTask.getName() +
                ",assignee:" + deptLeaderTask.getAssignee() +
                ",createTime:" + deptLeaderTask.getCreateTime());
        Object renderedTaskForm = formService.getRenderedTaskForm(deptLeaderTask.getId());
        System.out.println("表单内容---" + renderedTaskForm.toString());
        System.out.println("任务名称---" + deptLeaderTask.getName());
        model.addAttribute("form", renderedTaskForm);


        return "leave_hr";
    }

    @RequestMapping(value = "/Hr")
    public String Hr(String hrApproval) {

        System.out.println("Hr审批接收到" + hrApproval);
        // 人事审批通过
        Task hrTask = taskService.createTaskQuery().processInstanceId(processId).singleResult();
//                 assertNotNull(formService.getRenderedTaskForm(hrTask.getId()));// 读取任务表单
        variables = new HashMap<String, String>();
        variables.put("hrApproval", hrApproval);
        System.out.println("hr审批流程节点id" + hrTask.getId());
        formService.submitTaskFormData(hrTask.getId(), variables);
        return "ok";
    }

    @ResponseBody
    @RequestMapping(value = "/getProcesses")
    public LayuiJson ShowProcesses(Model model) {
        List<HistoricProcessInstance> list = historyService
                .createHistoricProcessInstanceQuery()
                .orderByProcessInstanceStartTime().asc()//排序
                .list();
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
                if(taskService.createTaskQuery().processInstanceId(list.get(i).getId()).singleResult()!=null){
                    System.out.println(taskService.createTaskQuery().processInstanceId(list.get(i).getId()).singleResult().getName());
                    processes.setProcessesTask(taskService.createTaskQuery().processDefinitionId(list.get(i).getProcessDefinitionId()).singleResult().getName());
                }else {
                    processes.setProcessesTask("");
                }
                System.out.println("=======================================");
                processes.setProcessesName(repositoryService.createProcessDefinitionQuery().processDefinitionId(list.get(i).getProcessDefinitionId()).singleResult().getName());
                processes.setProcessesStartUser(list.get(i).getStartUserId());
                //转换日期格式
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                processes.setProcessesStartTime(sdf.format(list.get(i).getStartTime()));

            processesList.add(processes);
            }
        }
//        model.addAttribute("processesList",processesList);
        LayuiJson layuiJson = new LayuiJson();
        layuiJson.setCode("0");
        layuiJson.setMsg("成功");
        layuiJson.setCount(String.valueOf(processesList.size()));
        layuiJson.setData(processesList);
        return layuiJson;
    }

    @RequestMapping(value = "/chaxun")
    public String Chaxun() {
        List<ProcessDefinition> list = //与流程定义和部署对象相关的Service
                repositoryService.createProcessDefinitionQuery()//创建一个流程定义查询
                        /*指定查询条件,where条件*/
                        //.deploymentId(deploymentId)//使用部署对象ID查询
                        //.processDefinitionId(processDefinitionId)//使用流程定义ID查询
                        //.processDefinitionKey(processDefinitionKey)//使用流程定义的KEY查询
                        //.processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询
                        /*排序*/
                        .orderByProcessDefinitionVersion().asc()//按照版本的升序排列
                        //.orderByProcessDefinitionName().desc()//按照流程定义的名称降序排列
                        .list();//返回一个集合列表，封装流程定义
        //.singleResult();//返回唯一结果集
        //.count();//返回结果集数量
        //.listPage(firstResult, maxResults)//分页查询

        if (list != null && list.size() > 0) {
            for (ProcessDefinition processDefinition : list) {
                System.out.println("流程定义ID:" + processDefinition.getId());//流程定义的key+版本+随机生成数
                System.out.println("流程定义名称:" + processDefinition.getName());//对应HelloWorld.bpmn文件中的name属性值
                System.out.println("流程定义的key:" + processDefinition.getKey());//对应HelloWorld.bpmn文件中的id属性值
                System.out.println("流程定义的版本:" + processDefinition.getVersion());//当流程定义的key值相同的情况下，版本升级，默认从1开始
                System.out.println("资源名称bpmn文件:" + processDefinition.getResourceName());
                System.out.println("资源名称png文件:" + processDefinition.getDiagramResourceName());
                System.out.println("部署对象ID:" + processDefinition.getDeploymentId());

//                System.out.println("删除该流程");
                System.out.println("################################");
//                String deploymentId = processDefinition.getDeploymentId();
// 第二个参数代表级联操作  
//                repositoryService.deleteDeployment(deploymentId, true);
// 删除所有相关的activiti信息  

            }
        }
        List<Task> list1 = taskService.createTaskQuery().list();
        if (list != null && list.size() > 0) {
            for (Task task : list1) {
                System.out.println("流程节点ID:" + task.getId());//流程定义的key+版本+随机生成数
                System.out.println("流程节点名称:" + task.getName());//对应HelloWorld.bpmn文件中的name属性值
                System.out.println("流程节点操作者:" + task.getAssignee());//对应HelloWorld.bpmn文件中的id属性值
//                System.out.println("删除该流程");
                System.out.println("################################");
//                String deploymentId = processDefinition.getDeploymentId();
// 第二个参数代表级联操作  
//                repositoryService.deleteDeployment(deploymentId, true);
// 删除所有相关的activiti信息  

            }
        }
        return "ok";
    }
}
