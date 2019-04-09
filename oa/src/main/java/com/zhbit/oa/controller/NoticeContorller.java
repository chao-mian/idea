package com.zhbit.oa.controller;

import com.zhbit.oa.domain.*;
import com.zhbit.oa.service.AccountMessageService;
import com.zhbit.oa.service.ProcessesService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class NoticeContorller {

    private String id;
    @Autowired
    private AccountMessageService accountMessageService;
    @Autowired
    private ProcessesService processesService;
    @Autowired
    private TaskService taskService;
    @ResponseBody
    @RequestMapping(value = "/getNotice")
    public LayuiNotice GetNotice(HttpServletRequest request){
        Account user = (Account) request.getSession().getAttribute("loginUser");
        System.out.println("user不为空" + user);
        AccountMessage accountMessage = accountMessageService.findByAid(user.getAusername());

        String username = accountMessage.getaMname();
        List<Processes> processesList = processesService.getAllProcessesList();
        List<Notice> noticeList = new ArrayList<>();
        for (int i = 0; i < processesList.size(); i++) {
            Notice notice = new Notice();
            Task task = taskService.createTaskQuery()
                    .processDefinitionId(processesList.get(i)
                            .getProcessesDefinitionId()).singleResult();
            String processesStartUser = processesList.get(i).getProcessesStartUser();
            System.out.println("流程创建者----" + processesStartUser);
            if (task != null) {
                if (task.getAssignee().equals(username)) {
                    notice.setId(processesList.get(i).getProcessesDefinitionId());
                    notice.setType("待办流程");
                    notice.setTitle(processesList.get(i).getProcessesName());
                    notice.setName(processesList.get(i).getProcessesStartUser());
                    notice.setTime(processesList.get(i).getProcessesStartTime());
                    noticeList.add(notice);
                }
            }
        }
        LayuiNotice layuiNotice = new LayuiNotice();
        layuiNotice.setData(noticeList);
        layuiNotice.setCode("0");
        layuiNotice.setMsg("成功");
        layuiNotice.setCount(String.valueOf(noticeList.size()));
        System.out.println("noticeList----" + noticeList);
        return layuiNotice;
    }

    @RequestMapping(value = "/getJsonId")
    public String GetJsonId(@RequestBody Object id){
        System.out.println("接收到的id-----"+id);
        this.id=id.toString();
        return "no";
    }
    @RequestMapping(value = "/openNotice")
    public String OpenNotice(HttpSession session){
        session.setAttribute("noticeId", id);
        if(id.indexOf("leave")>=0){
            return "forward:/leaderApprovalForm";
        }
        return "no";
    }
}
