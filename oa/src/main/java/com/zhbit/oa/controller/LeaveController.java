package com.zhbit.oa.controller;


import com.zhbit.oa.domain.*;
import com.zhbit.oa.domain.Character;
import com.zhbit.oa.service.AccountMessageService;
import com.zhbit.oa.service.AccountService;
import com.zhbit.oa.service.MechanismService;
import com.zhbit.oa.service.PermissionService;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LeaveController {
    //    private String jsonProcessesData;
    @Autowired
    private MechanismService mechanismService;
    @Autowired
    private AccountMessageService accountMessageService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PermissionService permissionService;

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

    @RequestMapping(value = "/jsonProcessDefinitionId")
    public String jsonProcessDefinitionId(@RequestBody Object processDefinitionId) {
        System.out.println("showForm接收到的流程ID" + processDefinitionId);
        this.processDefinitionId = processDefinitionId.toString();
        return "ok";
    }

    @RequestMapping(value = "/start")
    public String Start1(Model model) {

        //获取部门信息
        List<Mechanism> mechanismList = mechanismService.findAll();
        System.out.println("所有部门：" + mechanismList);
        model.addAttribute("mechanismList", mechanismList);
        //获取所有人员信息
        List<AccountMessage> accountMessagesList = accountMessageService.findAll();
        System.out.println("所有人员：" + accountMessagesList);
        List<AccountMessage> leaderList = new ArrayList<>();
        for (int i = 0; i < accountMessagesList.size(); i++) {
            //取出用户列表中角色为部门领导的用户
            //根据Aid获取该用户账号信息
            Account account = accountService.findOne(accountMessagesList.get(i).getAid());
            //根据账号中的角色ID获取该角色信息
            Character character = permissionService.findByCid(account.getCid());
            //判断该角色是不是领导
            if (character.getCname().equals("部门领导")) {
                leaderList.add(accountMessagesList.get(i));
            }
        }
        System.out.println("人事列表" + leaderList);
        model.addAttribute("leaderList", leaderList);
        //存人事部员工信息
        List<AccountMessage> hrlist = new ArrayList<>();
        for (int i = 0; i < accountMessagesList.size(); i++) {
            if (accountMessagesList.get(i).getaMmechanism().equals("人事部")) {
                hrlist.add(accountMessagesList.get(i));
            }
        }
        System.out.println("人事列表" + hrlist);
        model.addAttribute("hrlist", hrlist);
//        model.addAttribute("form", renderedStartForm);
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

        AccountMessage accountMessage = accountMessageService.findByAMname(leave.getName());

        if(accountMessage==null){
            model.addAttribute("flag","申请人不存在，申请失败！");
            return "forward:/start";
        }else{
            accountMessage.setaMmechanism(mechanismService.findOne(accountMessage.getaMmechanism()).getMname());
            if(!leave.getBumen().equals(accountMessage.getaMmechanism())
                    ||!leave.getZhiwei().equals(accountMessage.getaMposition())){

                model.addAttribute("flag","申请人职位或所在部门不对应，申请失败！");
                return "forward:/start";
            }
        }
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
        variables.put("name", leave.getName());
        variables.put("zhiwei", leave.getZhiwei());
        variables.put("bumen", leave.getBumen());
        variables.put("date", leave.getDate());
        variables.put("leaveType", leave.getLeaveType());
        variables.put("startDate", leave.getStartDate());
        variables.put("endDate", leave.getEndDate());
        variables.put("reason", leave.getReason());
        variables.put("leaderAssignee", leave.getLeaderAssignee());
        variables.put("hrAssignee", leave.getHrAssignee());
        // 读取启动表单
        System.out.println("definitionID---" + processDefinition.getId());


        //设置流程创建人
        String currentUserId = leave.getName();
        identityService.setAuthenticatedUserId(currentUserId);
        System.out.println(processDefinitionId);
        ProcessInstance processInstance = formService.submitStartFormData(processDefinitionId, variables);
        /*       Object renderedStartForm = formService.getRenderedStartForm(processDefinition.getId());

        System.out.println("表单内容---" + renderedStartForm.toString());*/
        processId = processInstance.getId();
        model.addAttribute("flag", "流程发送成功，请到我的流程中查看流程");
        return "newProcesses";

    }

    //领导办理流程页面
    @RequestMapping(value = "/leaderApprovalForm")
    public String LeaderApproval(Model model) {
        Task deptLeaderTask = taskService.createTaskQuery().processDefinitionId(processDefinitionId).singleResult();
        System.out.println("taskId:" + deptLeaderTask.getId() +
                ",taskName:" + deptLeaderTask.getName() +
                ",assignee:" + deptLeaderTask.getAssignee() +
                ",createTime:" + deptLeaderTask.getCreateTime());
        Object renderedTaskForm = formService.getRenderedTaskForm(deptLeaderTask.getId());
        System.out.println("表单内容---" + renderedTaskForm.toString());
        System.out.println("任务名称---" + deptLeaderTask.getName());
        model.addAttribute("form", renderedTaskForm);
        model.addAttribute("processDefinitionId", processDefinitionId);
        return "leave_approval";
    }

    @RequestMapping(value = "/Shenpi")
    public String Shenpi(String processDefinitionId, String leaderApproval, String leaderOpinion, Model model) {
        System.out.println("审批接收到" + leaderApproval);
        // 部门领导审批通过
        System.out.println("流程ID" + processDefinitionId);
        variables.put("leaderApproval", leaderApproval);
        variables.put("leaderOpinion", leaderOpinion);
        Task deptLeaderTask = taskService.createTaskQuery().processDefinitionId(processDefinitionId).singleResult();
//        deptLeaderTask.setAssignee("");
        System.out.println("领导审批流程节点id" + deptLeaderTask.getId());
        formService.submitTaskFormData(deptLeaderTask.getId(), variables);
        model.addAttribute("flag", "流程办理成功");
        return "forward:/showToDoProcesses";
    }

    //人事办理流程页面
    @RequestMapping(value = "/hrApprovalForm")
    public String HrApproval(Model model) {
        Task deptLeaderTask = taskService.createTaskQuery().processDefinitionId(processDefinitionId).singleResult();
        System.out.println("taskId:" + deptLeaderTask.getId() +
                ",taskName:" + deptLeaderTask.getName() +
                ",assignee:" + deptLeaderTask.getAssignee() +
                ",createTime:" + deptLeaderTask.getCreateTime());
        Object renderedTaskForm = formService.getRenderedTaskForm(deptLeaderTask.getId());
        System.out.println("表单内容---" + renderedTaskForm.toString());
        System.out.println("任务名称---" + deptLeaderTask.getName());
        model.addAttribute("form", renderedTaskForm);
        model.addAttribute("processDefinitionId", processDefinitionId);
        return "leave_hr";
    }

    @RequestMapping(value = "/Hr")
    public String Hr(String processDefinitionId,String hrApproval, String hrOpinion,Model model) {

        System.out.println("Hr审批接收到" + hrApproval);
        // 人事审批通过
        Task hrTask = taskService.createTaskQuery().processDefinitionId(processDefinitionId).singleResult();
        // 读取任务表单
        variables = new HashMap<String, String>();
        variables.put("hrApproval", hrApproval);
        variables.put("hrOpinion", hrOpinion);
        System.out.println("hr审批流程节点id" + hrTask.getId());
        formService.submitTaskFormData(hrTask.getId(), variables);
        model.addAttribute("flag", "流程办理成功");
        return "forward:/showToDoProcesses";
    }


    @RequestMapping(value = "/showForm")
    public String ShowForm(Model model) {
        System.out.println("showForm接收到的流程ID" + processDefinitionId);
        //根据流程id查到对应的历史流程
        HistoricProcessInstance hi = historyService
                .createHistoricProcessInstanceQuery()
                .processDefinitionId(processDefinitionId)
                .singleResult();
        //将该流程所有历史变量存进valuablelist
        System.out.println("hi-----" + hi.getId());
        List<HistoricVariableInstance> valuablelist = historyService
                //创建一个历史的流程变量查询对象
                .createHistoricVariableInstanceQuery()
                .processInstanceId(hi.getId())
                .list();
        Leave leave = new Leave();
        for (int i = 0; i < valuablelist.size(); i++) {
            if (valuablelist.get(i).getVariableName().equals("name")) {
                //创建者
                leave.setName(valuablelist.get(i).getValue().toString());
            } else if (valuablelist.get(i).getVariableName().equals("zhiwei")) {
                //职位
                leave.setZhiwei(valuablelist.get(i).getValue().toString());
            } else if (valuablelist.get(i).getVariableName().equals("bumen")) {
                //部门
                leave.setBumen(valuablelist.get(i).getValue().toString());
            } else if (valuablelist.get(i).getVariableName().equals("date")) {
                //申请日期
                leave.setDate(valuablelist.get(i).getValue().toString());
            } else if (valuablelist.get(i).getVariableName().equals("startDate")) {
                //开始时间
                leave.setStartDate(valuablelist.get(i).getValue().toString());
            } else if (valuablelist.get(i).getVariableName().equals("endDate")) {
                //结束时间
                leave.setEndDate(valuablelist.get(i).getValue().toString());
            } else if (valuablelist.get(i).getVariableName().equals("reason")) {
                //请假事由
                leave.setReason(valuablelist.get(i).getValue().toString());
            } else if (valuablelist.get(i).getVariableName().equals("leaderOpinion")) {
                //领导意见
                leave.setLeaderOpinion(valuablelist.get(i).getValue().toString());
            } else if (valuablelist.get(i).getVariableName().equals("leaderApproval")) {
                //领导审批
                leave.setLeaderApproval(valuablelist.get(i).getValue().toString());
            } else if (valuablelist.get(i).getVariableName().equals("hrOpinion")) {
                //人事意见
                leave.setHrOpinion(valuablelist.get(i).getValue().toString());
            } else if (valuablelist.get(i).getVariableName().equals("hrApproval")) {
                //人事审批
                leave.setHrApproval(valuablelist.get(i).getValue().toString());
            } else if (valuablelist.get(i).getVariableName().equals("leaveType")) {
                //请假类型
                leave.setLeaveType(valuablelist.get(i).getValue().toString());
            } else if (valuablelist.get(i).getVariableName().equals("leaderAssignee")) {
                //审批的领导
                leave.setLeaderAssignee(valuablelist.get(i).getValue().toString());
            } else if (valuablelist.get(i).getVariableName().equals("hrAssignee")) {
                //审批的人事
                leave.setHrAssignee(valuablelist.get(i).getValue().toString());
            }

        }
        System.out.println("leave-----" + leave);
        model.addAttribute("leave", leave);
        return "showDoneProcessesForm";
    }

}
