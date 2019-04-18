package com.zhbit.oa.controller;

import com.zhbit.oa.domain.*;
import com.zhbit.oa.service.AccountMessageService;
import com.zhbit.oa.service.BulletinService;
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
    private BulletinService bulletinService;

    @Autowired
    private ProcessesService processesService;
    @Autowired
    private TaskService taskService;
    @ResponseBody
    @RequestMapping(value = "/getNotice")
    public LayuiNotice GetNotice( HttpServletRequest request){
        LayuiNotice layuiNotice = (LayuiNotice)request.getSession().getAttribute("layuiNotice");
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
        }else if(bulletinService.getOneBulletin(id)!=null){
            return "forward:/showMyBulletin";
        }
        return "no";
    }
}
