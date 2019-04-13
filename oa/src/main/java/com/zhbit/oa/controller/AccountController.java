package com.zhbit.oa.controller;


import com.zhbit.oa.domain.*;
import com.zhbit.oa.domain.Character;
import com.zhbit.oa.service.*;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AccountController {


    @Autowired
    private AccountService accountService;
    private String jsonaData[];
    private String jsonaMechanism;
    private String jsonaName;
    private String jsonaAccount;
    private String jsonaStatus;
    @Autowired
    MechanismService mechanismService;
    @Autowired
    AccountMessageService accountMessageService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    ProcessesService processesService;
    @Autowired
    TaskService taskService;


    //获取页面jsona传回来的jsona
    @RequestMapping(value = "/jsonaDate")
    public String jsonaGet(@RequestBody String jsona[]) {
        for (int i = 0; i < jsona.length; i++) {
            System.out.println("jsonaDate接收jsona===》" + jsona[i]);
        }
        jsonaData = jsona;
        return "ok";
    }

    @RequestMapping(value = "/jsonaMechanism")
    public String jsonaMechanism(@RequestBody String jsona[]) {
        System.out.println("jsonaMechanism接收jsona===》" + jsona[0]);
        jsonaMechanism = jsona[0];
        return "ok";
    }

    @RequestMapping(value = "/jsonaName")
    public String jsonaName(@RequestBody String jsona[]) {
        System.out.println("jsonaName接收jsona===》" + jsona[0]);
        jsonaName = jsona[0];
        return "ok";
    }

    @RequestMapping(value = "/jsonaAccount")
    public String jsonaAccount(@RequestBody String jsona[]) {
        System.out.println("jsonaAccount接收jsona===》" + jsona[0]);
        jsonaAccount = jsona[0];
        return "ok";
    }

    @RequestMapping(value = "/jsonaStatus")
    public String jsonaStatus(@RequestBody String jsona[]) {
        System.out.println("jsonaStatus接收jsona===》" + jsona[0]);
        jsonaStatus = jsona[0];
        return "ok";
    }

    @RequestMapping(value = "/Login")
    public String login(Account account, Model model, HttpSession session) {
        System.out.println("login输出登录账号" + account);
        model.addAttribute("flag", "success");

        String status = accountService.login(account);
        if (status.equals("success")) {
            model.addAttribute("flag", "success");
            System.out.println("login输出登录账号数据" + accountMessageService.findByAid(account.getAusername()));
            model.addAttribute("accountMessage", accountMessageService.findByAid(account.getAusername()));
            account = accountService.findOne(account.getAusername());
//            session.setMaxInactiveInterval(3600);
            session.setAttribute("loginUser", account);
            return "redirect:/index";
        } else if (status.equals("dongjie")) {
            model.addAttribute("flag", "dongjie");
        } else if (status.equals("false")) {
            model.addAttribute("flag", "false");
        } else if (status.equals("null")) {
            System.out.println("controller  null");
            model.addAttribute("flag", "nomessage");
        }
        return "login";
    }

    @RequestMapping(value = "/login")
    public String login(HttpSession session) {
        session.setAttribute("loginUser", null);
        return "login";
    }

    @RequestMapping(value = "/index")
    public String indexInTo(Model model,  HttpSession session,HttpServletRequest request, HttpServletResponse response) {
        Account user = (Account) request.getSession().getAttribute("loginUser");
        System.out.println("user不为空" + user);
        AccountMessage accountMessage = accountMessageService.findByAid(user.getAusername());
        model.addAttribute("accountMessage", accountMessage);
        List<CharacterPermission> listCp = permissionService.findByCidInCp(user.getCid());
//            String[] str = new String[listCp.size()];
        //将该用户的角色所拥有的权限存在listStr中
        List<String> listStr = new ArrayList<>();
        for (int i = 0; i < listCp.size(); i++) {
            if (i == 0) {
                listStr.add(listCp.get(i).getPid().substring(0, 1));
            } else {
                int flag = 0;
                //判断重复则不存入
                for (int j = i - 1; j < i; j++) {
                    if (!listCp.get(i).getPid().substring(0, 1).equals(listStr.get(j))) {
                        flag = 1;
                    } else {
                        flag = 0;
                        break;
                    }
                }
                if (flag == 1) {
                    listStr.add(listCp.get(i).getPid().substring(0, 1));
                } else {
                    break;
                }
            }
        }
        System.out.println("该用户的cid");
        for (int i = 0; i < listStr.size(); i++) {
            System.out.println(listStr.get(i));
        }
        model.addAttribute("listStr", listStr);

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
        System.out.println("noticeList----" + noticeList);
        session.setAttribute("noticeList",noticeList);
        model.addAttribute("noticeList",noticeList);
        return "index";
    }

    @RequestMapping(value = "/main")
    public String Main(Model model, HttpServletRequest request){
        List<Notice> noticeList = (List<Notice>)request.getSession().getAttribute("noticeList");
        System.out.println(noticeList);
        model.addAttribute("noticeList",noticeList);
        return "main";
    }

    @RequestMapping(value = "/addAccount")
    public String addAccont(Account account, Model model) {
        account.setAstatus("Y");
        System.out.println("Account===>" + account);
        if (accountService.addAccount(account)) {
            model.addAttribute("flag", "新建成功");
        }
        return "forward:/accountTable";
//        return "ok";
    }


    @RequestMapping(value = "deleteAccount")
    public String deleteMechanism(Model model) {
        accountService.deleteAccount(jsonaData);
        model.addAttribute("flag", "删除成功");
        jsonaData = null;
        return "forward:/accountTable";
    }

    @RequestMapping(value = "showOne")
    public String showOne(Model model) {
        Account account = accountService.findOne(jsonaData[0]);
        //将account中的cid改成角色名
        account.setCid(permissionService.findByCid(account.getCid()).getCname());
        System.out.println("showOne中输出" + account);
        List<Character> list = permissionService.findAllCharacter();
        model.addAttribute("account", account);
        model.addAttribute("list", list);
        jsonaData = null;
        return "updateAccountForm";
    }

    @RequestMapping(value = "updateAccount")
    public String updateAccount(Account account) {
        System.out.println("updateAccount中account" + account);
        accountService.updateAccount(account);
        return "forward:/accountTable";
    }

    @RequestMapping(value = "/newAccountForm")
    public String newAccountForm(Model model) {
        model.addAttribute("list", permissionService.findAllCharacter());
        System.out.println("角色list" + permissionService.findAllCharacter());
        return "newAccountForm";
    }

    //显示账户列表
    @RequestMapping(value = "/accountTable")
    public String findAll(Model model) {
        System.out.println("进入findAll");
        List<AccountList> list = accountService.accountList();
        System.out.println("list中所有的东西\n" + list);
        List<AccountList> listByaMmechanism = new ArrayList<>();
        if (jsonaMechanism != null) {
            for (AccountList accountList : list) {
                if (accountList.getaMmechanism().equals(jsonaMechanism)) {
                    listByaMmechanism.add(accountList);
                }
            }
            list = listByaMmechanism;
        } else if (jsonaName != null) {
            for (AccountList accountList : list) {
                if (accountList.getaMname().equals(jsonaName)) {
                    listByaMmechanism.add(accountList);
                }
            }
            list = listByaMmechanism;
        } else if (jsonaAccount != null) {
            for (AccountList accountList : list) {
                if (accountList.getAusername().equals(jsonaAccount)) {
                    listByaMmechanism.add(accountList);
                }
            }
            list = listByaMmechanism;
        } else if (jsonaStatus != null) {
            for (AccountList accountList : list) {
                if (accountList.getAstatus().equals(jsonaStatus)) {
                    listByaMmechanism.add(accountList);
                }
            }
            list = listByaMmechanism;
        }
        System.out.println("最终给页面list中的东西\n" + list);
        model.addAttribute("list", list);
        List<Mechanism> mechanismList = mechanismService.findAll();
        model.addAttribute("mechanismList", mechanismList);
        jsonaMechanism = null;
        jsonaName = null;
        jsonaAccount = null;
        jsonaStatus = null;
        return "accountTable";
    }
}
