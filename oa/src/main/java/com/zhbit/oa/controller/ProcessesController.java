package com.zhbit.oa.controller;

import com.zhbit.oa.domain.*;
import com.zhbit.oa.service.AccountMessageService;
import com.zhbit.oa.service.MechanismService;
import com.zhbit.oa.service.ProcessesService;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProcessesController {
    @Autowired
    private MechanismService mechanismService;
    @Autowired
    private AccountMessageService accountMessageService;
    @Autowired
    private ProcessesService processesService;


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


    @RequestMapping(value = "/showAllProcesses")
    public String ShowAllProcesses(Model model) {
        String url = "/getAllProcesses";
        model.addAttribute("url", url);
        return "showProcesses";
    }

    @RequestMapping(value = "/showMyProcesses")
    public String ShowMyProcesses(Model model) {
        String url = "/getMyProcesses";
        model.addAttribute("url", url);
        return "showProcesses";
    }

    @RequestMapping(value = "/showToDoProcesses")
    public String ShowToDoProcesses(Model model) {
        String url = "/getToDoProcesses";
        model.addAttribute("url", url);
        return "showProcesses";
    }

    @RequestMapping(value = "/showDoneProcesses")
    public String ShowDoneProcesses(Model model) {

        String url = "/getDoneProcesses";
        model.addAttribute("url", url);
        return "showProcesses";
    }

    @ResponseBody
    @RequestMapping(value = "/getAllProcesses")
    public LayuiJson ShowProcesses(Model model) {
        List<Processes> processesList = processesService.getAllProcessesList();
//        model.addAttribute("processesList",processesList);
        LayuiJson layuiJson = new LayuiJson();
        layuiJson.setCode("0");
        layuiJson.setMsg("成功");
        layuiJson.setCount(String.valueOf(processesList.size()));
        layuiJson.setData(processesList);
        return layuiJson;
    }

    @ResponseBody
    @RequestMapping(value = "/getToDoProcesses")
    public LayuiJson ShowToDoProcesses(Model model, HttpServletRequest request) {

        //获取登录用户
        Account user = (Account) request.getSession().getAttribute("loginUser");
        AccountMessage accountMessage = accountMessageService.findByAid(user.getAusername());
        String username = accountMessage.getaMname();
        System.out.println("登录用户---" + user);
        System.out.println("登录用户名---" + username);
        List<Processes> processesList = processesService.getAllProcessesList();

        List<Processes> ToDoprocesses = new ArrayList<>();
        for (int i = 0; i < processesList.size(); i++) {
            Task task = taskService.createTaskQuery().processDefinitionId(processesList.get(i).getProcessesDefinitionId()).singleResult();
            String processesStartUser = processesList.get(i).getProcessesStartUser();
            System.out.println("流程创建者----" + processesStartUser);
            if (task != null) {
                if (task.getAssignee().equals(username)) {
                    ToDoprocesses.add(processesList.get(i));
                }
            }

        }
        System.out.println("ToDoProcesses----" + ToDoprocesses);
        LayuiJson layuiJson = new LayuiJson();
        layuiJson.setCode("0");
        layuiJson.setMsg("成功");
        layuiJson.setCount(String.valueOf(ToDoprocesses.size()));
        layuiJson.setData(ToDoprocesses);
        return layuiJson;
    }

    @ResponseBody
    @RequestMapping(value = "/getDoneProcesses")
    public LayuiJson ShowDoneProcesses( Model model, HttpServletRequest request) {


        //获取登录用户
        Account user = (Account) request.getSession().getAttribute("loginUser");
        AccountMessage accountMessage = accountMessageService.findByAid(user.getAusername());
        String username = accountMessage.getaMname();
        System.out.println("登录用户---" + user);
        System.out.println("登录用户名---" + username);
        List<Processes> doneProcessesList = processesService.getActivityProcessesList(username);
        LayuiJson layuiJson = new LayuiJson();

        System.out.println("doneProcessesList----" + doneProcessesList);

        layuiJson.setCode("0");
        layuiJson.setMsg("成功");
        layuiJson.setCount(String.valueOf(doneProcessesList.size()));
        layuiJson.setData(doneProcessesList);
        return layuiJson;
    }

    @ResponseBody
    @RequestMapping(value = "/getMyProcesses")
    public LayuiJson ShowMyProcesses(Model model, HttpServletRequest request) {
        //获取登录用户
        Account user = (Account) request.getSession().getAttribute("loginUser");
        AccountMessage accountMessage = accountMessageService.findByAid(user.getAusername());
        String username = accountMessage.getaMname();
        System.out.println("登录用户---" + user);
        System.out.println("登录用户名---" + username);
//获取所有流程
        List<Processes> processesList = processesService.getAllProcessesList();
        //将流程创建者或流程中有节点有该用户的流程存到Myprocesses
        List<Processes> Myprocesses = new ArrayList<>();
        for (int i = 0; i < processesList.size(); i++) {

            String processesStartUser = processesList.get(i).getProcessesStartUser();
            System.out.println("流程创建者----" + processesStartUser);
            if (processesStartUser.equals(username)) {
                Myprocesses.add(processesList.get(i));
            }
        }
        LayuiJson layuiJson = new LayuiJson();
        layuiJson.setCode("0");
        layuiJson.setMsg("成功");
        layuiJson.setCount(String.valueOf(Myprocesses.size()));
        layuiJson.setData(Myprocesses);
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
                System.out.println("流程定义ID:" + task.getProcessDefinitionId());
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

        List<HistoricActivityInstance> list3 = historyService.createHistoricActivityInstanceQuery()

                .orderByHistoricActivityInstanceStartTime().asc()//排序
                .list();

        if (list != null && list.size() > 0) {
            for (HistoricActivityInstance hpi : list3) {
                System.out.println("历史活动");
                System.out.println("流程定义ID：" + hpi.getProcessDefinitionId());
                System.out.println("流程实例ID：" + hpi.getId());
                System.out.println("流程名：" + hpi.getActivityName());
                System.out.println("开始时间：" + hpi.getStartTime());
                System.out.println("结束时间：" + hpi.getEndTime());
                System.out.println("流程持续时间：" + hpi.getDurationInMillis());
                System.out.println("流程节点id：" + hpi.getTaskId());
                System.out.println("流程操作者：" + hpi.getAssignee());

                HistoricProcessInstance hi = historyService
                        .createHistoricProcessInstanceQuery()
                        .processDefinitionId(hpi.getProcessDefinitionId())
                        .singleResult();
                List<HistoricVariableInstance> list4 = historyService
                        .createHistoricVariableInstanceQuery()                                      //创建一个历史的流程变量查询对象
                        .processInstanceId(hi.getId())
                        .list();
                if(list !=null && list.size()>0){
                    for(HistoricVariableInstance hvi:list4){
                        System.out.println(hvi.getId()+"   "+hvi.getProcessInstanceId()+"   "+hvi.getVariableName()+"   "+hvi.getVariableTypeName()+"    "+hvi.getValue());
                        System.out.println("###############################################");
                    }
                }
                System.out.println("=======================================");
            }
        }
        return "ok";
    }
}
