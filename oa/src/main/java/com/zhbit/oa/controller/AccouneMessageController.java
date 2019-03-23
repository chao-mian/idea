package com.zhbit.oa.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.zhbit.oa.domain.Account;
import com.zhbit.oa.domain.AccountMessage;
import com.zhbit.oa.domain.Mechanism;
import com.zhbit.oa.service.AccountMessageService;
import com.zhbit.oa.service.MechanismService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class AccouneMessageController {
    private String jsonamData[];
    private String jsonamMechanism;
    private String jsonamName;
    private String jsonamSex;
    private String jsonamStatus;

    @Autowired
    private AccountMessageService accountMessageService;
    @Autowired
    private MechanismService mechanismService;

    @RequestMapping(value = "/jsonamDate")
    public String jsonamGet(@RequestBody String jsonam[]) {
        for (int i = 0; i < jsonam.length; i++) {
            System.out.println("jsonamDate接收jsonam===》" + jsonam[i]);
        }
        jsonamData = jsonam;
        return "ok";
    }

    @RequestMapping(value = "/jsonamMechanism")
    public String jsonamMechanism(@RequestBody String jsonam[]) {
        System.out.println("jsonamMechanism接收jsonam===》" + jsonam[0]);
        jsonamMechanism = jsonam[0];
        return "ok";
    }

    @RequestMapping(value = "/jsonamName")
    public String jsonamName(@RequestBody String jsonam[]) {
        System.out.println("jsonamName接收jsonam===》" + jsonam[0]);
        jsonamName = jsonam[0];
        return "ok";
    }

    @RequestMapping(value = "/jsonamSex")
    public String jsonamSex(@RequestBody String jsonam[]) {
        System.out.println("jsonamAccount接收jsonam===》" + jsonam[0]);
        jsonamSex = jsonam[0];
        return "ok";
    }

    @RequestMapping(value = "/jsonamStatus")
    public String jsonamStatus(@RequestBody String jsonam[]) {
        System.out.println("jsonamStatus接收jsonam===》" + jsonam[0]);
        jsonamStatus = jsonam[0];
        return "ok";
    }

    @RequestMapping(value = "/newPersonnelForm")
    public String newPersonnelForm(Model model) {
        List<Mechanism> mechanismList = mechanismService.findAll();
        System.out.println("所有部门：" + mechanismList);
        model.addAttribute("mechanismList", mechanismList);
        return "newPersonnelForm";
    }

    @RequestMapping(value = "/addMessage")
    public String addMessage(Model model, AccountMessage accountMessage, MultipartFile pictureFile) throws IllegalStateException, IOException {
        System.out.println("addMessage中输出message==>" + accountMessage);
        System.out.println("addMessage中输出file==>" + pictureFile);
        if (!pictureFile.isEmpty()) {
            // 获取图片的完整路径
            String fileName = pictureFile.getOriginalFilename();
            System.out.println("路径+++++》" + fileName);
            // 使用随机生成的字符串   + 原图片的扩展名 组成
//            String NewFileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
            // 将图片保存在服务器  （硬盘）
//            pictureFile.transferTo(new File("F:\\ideawork\\oa\\src\\main\\resources\\static\\img\\avatar\\" + fileName));
            pictureFile.transferTo(new File("C:\\avatar\\" + fileName));
            // 将图片的路径保存一份到数据库中（可以用网站显示）
            accountMessage.setaMavatar(fileName);
        }
//        return "ok";
        if (accountMessageService.addAccountMessage(accountMessage)) {
            model.addAttribute("flag", "添加成功");
            return "forward:/personnelTable";
        } else {
            return "newPersonnelForm";
        }
    }

    @RequestMapping(value = "/updateMessage")
    public String updateMessage(Model model, AccountMessage accountMessage, MultipartFile pictureFile) throws IllegalStateException, IOException {

        System.out.println("addMessage中输出message==>" + accountMessage);
        System.out.println("addMessage中输出file==>" + pictureFile);

        if (!pictureFile.isEmpty()) {
            String avatar = accountMessageService.findByaMid(accountMessage.getaMid()).getaMavatar();
            File file = new File("C:\\avatar\\" + avatar);
            // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
            if (file.exists() && file.isFile()) {
                if (file.delete()) {
                    System.out.println("删除单个文件" + avatar + "成功！");
                } else {
                    System.out.println("删除单个文件" + avatar + "失败！");
                }
            } else {
                System.out.println("删除单个文件失败：" + avatar + "不存在！");
            }
            // 获取图片的完整路径
            String fileName = pictureFile.getOriginalFilename();
            System.out.println("路径+++++》" + fileName);
            // 使用随机生成的字符串   + 原图片的扩展名 组成
//            String NewFileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
            // 将图片保存在服务器  （硬盘）
//            pictureFile.transferTo(new File("F:\\ideawork\\oa\\src\\main\\resources\\static\\img\\avatar\\" + fileName));
            pictureFile.transferTo(new File("C:\\avatar\\" + fileName));
            // 将图片的路径保存一份到数据库中（可以用网站显示）
            accountMessage.setaMavatar(fileName);
        }
        accountMessageService.updateMessage(accountMessage);
        AccountMessage accountMessage1 = accountMessageService.findByaMid(accountMessage.getaMid());
        System.out.println("json过来查到的数据" + accountMessage1);
        model.addAttribute("accountMessage", accountMessage1);
        return "showPersonnel";
    }

    @RequestMapping(value = "/showPersonnel")
    public String showPersonnel(Model model, HttpServletRequest request, HttpServletResponse response) {
        AccountMessage accountMessage;
        if (jsonamData == null) {
            Account user = (Account) request.getSession().getAttribute("loginUser");
            accountMessage = accountMessageService.findByAid(user.getAid());
            accountMessage = accountMessageService.findByaMid(accountMessage.getaMid());
        } else {
            accountMessage = accountMessageService.findByaMid(jsonamData[0]);
        }
        System.out.println("json过来查到的数据" + accountMessage);
        model.addAttribute("accountMessage", accountMessage);
        jsonamData = null;
        return "showPersonnel";
    }

    @RequestMapping(value = "/updatePersonnelForm")
    public String updatePersonnelForm(Model model) {
        List<Mechanism> mechanismList = mechanismService.findAll();
        model.addAttribute("mechanismList", mechanismList);
        AccountMessage accountMessage = accountMessageService.findByaMid(jsonamData[0]);
        System.out.println("json过来查到的数据" + accountMessage);
        model.addAttribute("accountMessage", accountMessage);
        jsonamData = null;
        return "updatePersonnelForm";
    }


    @RequestMapping(value = "/personnelTable")
    public String showPersonnelTable(Model model) {
        List<Mechanism> mechanismList = mechanismService.findAll();
        model.addAttribute("mechanismList", mechanismList);
        List<AccountMessage> list = accountMessageService.findAll();

        List<AccountMessage> listByaMessage = new ArrayList<>();
        if (jsonamMechanism != null) {
            for (AccountMessage accountMessage : list) {
                if (accountMessage.getaMmechanism().equals(jsonamMechanism)) {
                    listByaMessage.add(accountMessage);
                }
            }
            list = listByaMessage;
        } else if (jsonamName != null) {
            for (AccountMessage accountMessage : list) {
                if (accountMessage.getaMname().equals(jsonamName)) {
                    listByaMessage.add(accountMessage);
                }
            }
            list = listByaMessage;
        } else if (jsonamSex != null) {
            for (AccountMessage accountMessage : list) {
                if (accountMessage.getaMsex().equals(jsonamSex)) {
                    listByaMessage.add(accountMessage);
                }
            }
            list = listByaMessage;
        } else if (jsonamStatus != null) {
            for (AccountMessage accountMessage : list) {
                if (accountMessage.getaMwork().equals(jsonamStatus)) {
                    listByaMessage.add(accountMessage);
                }
            }
            list = listByaMessage;
        }
        model.addAttribute("list", list);
        jsonamMechanism = null;
        jsonamName = null;
        jsonamSex = null;
        jsonamStatus = null;
        return "personnelTable";
    }
}
