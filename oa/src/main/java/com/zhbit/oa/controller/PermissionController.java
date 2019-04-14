package com.zhbit.oa.controller;

import com.zhbit.oa.domain.Character;
import com.zhbit.oa.domain.CharacterPermission;
import com.zhbit.oa.domain.Permission;
import com.zhbit.oa.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    private String jsonpData[];
    private String jsonpName;
    private String jsonpStatus;

    //获取页面jsona传回来的jsona
    @RequestMapping(value = "/jsonpData")
    public String jsonpGet(@RequestBody String jsonp[]) {
        for (int i = 0; i < jsonp.length; i++) {
            System.out.println("jsonaDate接收jsona===》" + jsonp[i]);
        }
        jsonpData = jsonp;
        return "ok";
    }

    @RequestMapping(value = "/jsonpName")
    public String jsonpName(@RequestBody String jsonp[]) {
        System.out.println("jsonpmName接收jsonam===》" + jsonp[0]);
        jsonpName = jsonp[0];
        return "ok";
    }

    @RequestMapping(value = "/jsonpStatus")
    public String jsonpStatus(@RequestBody String jsonp[]) {
        System.out.println("jsonamStatus接收jsonam===》" + jsonp[0]);
        jsonpStatus = jsonp[0];
        return "ok";
    }

    @RequestMapping(value = "/addPermission")
    public String addPermission(Permission permission, Model model) {
        System.out.println("add中接收到的===》" + permission);
        if (permissionService.addPermission(permission)) {
            model.addAttribute("flag", "添加成功");
            return "forward:/permissionTable";
        }
        return "no";
    }

    @RequestMapping(value = "/permissionTable")
    public String permissionTable(Model model) {
        List<Permission> list = permissionService.findAll();
        System.out.println("permissionTable中输出list==>" + list);
        List<Permission> listByaPermission = new ArrayList<>();
        if (jsonpName != null) {
            for (Permission permission : list) {
                if (permission.getPname().equals(jsonpName)) {
                    listByaPermission.add(permission);
                }
            }
            list = listByaPermission;
        } else if (jsonpStatus != null) {
            for (Permission permission : list) {
                if (permission.getPusing().equals(jsonpStatus)) {
                    listByaPermission.add(permission);
                }
            }
            list = listByaPermission;
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPusing().equals("Y")) {
                list.get(i).setPusing("启用");
            } else if (list.get(i).getPusing().equals("N")) {
                list.get(i).setPusing("停用");
            }
        }
        model.addAttribute("list", list);
        jsonpName = null;
        jsonpStatus = null;
        return "permissionTable";
    }

    @RequestMapping(value = "/deletePermission")
    public String deletePermission(Model model) {
        permissionService.deletePermission(jsonpData);
        model.addAttribute("flag", "删除成功");
        jsonpData = null;
        return "forward:/permissionTable";
    }

    @RequestMapping(value = "/showPermission")
    public String showPermission(Model model) {
        Permission permission = permissionService.findOne(jsonpData[0]);
        model.addAttribute("permission", permission);
        jsonpData = null;
        return "updatePermissionForm";
    }

    @RequestMapping(value = "/showCharacter")
    public String showCharacter(Model model) {
        Character character = permissionService.findByCid(jsonpData[0]);
        List<CharacterPermission> list2 = permissionService.findByCidInCp(jsonpData[0]);
        String[] str = new String[list2.size()];
        for (int i = 0; i < list2.size(); i++) {
            str[i] = list2.get(i).getPid();
            str[i] = permissionService.findOne(str[i]).getPname();
        }
        character.setPermission(str);
        System.out.println(character);
        model.addAttribute("character", character);
        jsonpData = null;

        List<Permission> list = permissionService.findAll();
        if (jsonpData != null) {
            List<Permission> list1 = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (jsonpData[0] == "a") {
                    list1 = list;
                } else if (list.get(i).getPvalue().substring(0, 1).equals(jsonpData[0])) {
                    list1.add(list.get(i));
                }
            }
            list = list1;
        }
        System.out.println(list);
        model.addAttribute("list", list);

        jsonpData = null;
//        return "updatePermissionForm";
        return "updateCharacterForm";
    }

    @RequestMapping(value = "/updatePermission")
    public String updatePermission(Permission permission, Model model) {
        permissionService.updatePermission(permission);
        model.addAttribute("flag", "修改成功");
        return "forward:/permissionTable";
    }

    @RequestMapping(value = "/updateCharacter")
    public String updateCharacter(Model model) {
        permissionService.updateCharacter(jsonpData);
        model.addAttribute("flag", "修改成功");
        jsonpData = null;
        return "forward:/CharacterTable";
    }

    @RequestMapping(value = "/newCharacterForm")
    public String newCharacterForm(Model model) {
        List<Permission> list = permissionService.findAll();
        if (jsonpData != null) {
            List<Permission> list1 = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (jsonpData[0] == "a") {
                    list1 = list;
                } else if (list.get(i).getPvalue().substring(0, 1).equals(jsonpData[0])) {
                    list1.add(list.get(i));
                }
            }
            list = list1;
        }
        System.out.println(list);
        model.addAttribute("list", list);
        jsonpData = null;
        return "newCharacterForm";
    }

    @RequestMapping(value = "/newCharacter")
    public String newCharacter(Model model) {
        //存入角色权限链表
        permissionService.addCharacterPermission(jsonpData);
        model.addAttribute("flag", "添加成功");
        jsonpData = null;
        return "forward:/CharacterTable";
    }

    @RequestMapping(value = "/deleteCharacter")
    public String deleteCharacter(Model model) {
        permissionService.deleteCharacter(jsonpData);
        model.addAttribute("flag", "删除成功");
        jsonpData = null;
        return "forward:/CharacterTable";
    }

    @RequestMapping(value = "/CharacterTable")
    public String characterTable(Model model) {
        List<Character> list = permissionService.findAllCharacter();
        System.out.println(list);
        List<Character> listByaCharacter = new ArrayList<>();
        if (jsonpName != null) {
            for (Character character : list) {
                if (character.getCname().equals(jsonpName)) {
                    listByaCharacter.add(character);
                }
            }
            list = listByaCharacter;
        } else if (jsonpStatus != null) {
            for (Character character : list) {
                if (character.getCusing().equals(jsonpStatus)) {
                    listByaCharacter.add(character);
                }
            }
            list = listByaCharacter;
        }
        model.addAttribute("list", list);
        jsonpName = null;
        jsonpStatus = null;
        return "forward:/characterTable";
    }
}
