package com.zhbit.oa.controller;


import com.zhbit.oa.domain.Mechanism;
import com.zhbit.oa.service.MechanismService;


import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MechanismController {
    @Autowired
    private MechanismService mechanismService;
    private String jsonData[];

    @RequestMapping(value = "/addMechanism")
    public String addMechanism(Mechanism mechanism, Model model) {
        System.out.println("Controller接收到=" + mechanism);

        if (mechanismService.addMechanism(mechanism)) {
            System.out.println("插入成功");
            model.addAttribute("flag", "添加成功");
            return "forward:/mechanismTable";
        } else {
            model.addAttribute("flag", "添加失败");
            return "forward:/mechanismTable";
        }
    }

    @RequestMapping(value = "deleteMechanism")
    public String deleteMechanism(Model model) {

        mechanismService.deleteMechanism(jsonData);
        model.addAttribute("flag", "删除成功");
        jsonData = null;
        return "forward:/mechanismTable";
    }

    @RequestMapping(value = "updateMechanism")
    public String updateMechanism(Mechanism mechanism, Model model) {
        mechanismService.updateMechanism(mechanism);
        model.addAttribute("flag", "修改成功");
        return "forward:/mechanismTable";
    }

    //获取页面json传回来的mid
    @RequestMapping(value = "/jsonMid")
    public String jsonMid(@RequestBody String mid[]) {
        for (int i = 0; i < mid.length; i++) {
            System.out.println("接收json===》" + mid[i]);
        }
        jsonData = mid;
        return "ok";
    }

/*    @RequestMapping(value = "findOne")
    public String findOne(Model model) {
        Mechanism mechanism = mechanismService.findOne(jsonData[0]);
        model.addAttribute("mechanism", mechanism);
        return "forward:/mechanismTable";
    }*/

    @RequestMapping(value = "/mechanismTable")
    public String findAll(Model model) {
        Mechanism mechanism;
        if (jsonData == null) {
            mechanism = mechanismService.findOne("0F");
        } else {
            mechanism = mechanismService.findOne(jsonData[0]);
        }
        System.out.println(mechanism);
        model.addAttribute("mechanism", mechanism);
//        Map<String,List> map = new HashMap<String,List>();
        List<Mechanism> list = mechanismService.findAll();
//        map.put("result",list);
        model.addAttribute("list", list);
        System.out.println(list);
        return "mechanismTable";
    }

}
