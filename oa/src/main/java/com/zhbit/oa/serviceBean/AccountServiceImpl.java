package com.zhbit.oa.serviceBean;

import com.zhbit.oa.dao.AccountMapper;
import com.zhbit.oa.dao.AccountMessageMapper;
import com.zhbit.oa.dao.MechanismMapper;
import com.zhbit.oa.domain.Account;
import com.zhbit.oa.domain.AccountList;
import com.zhbit.oa.domain.AccountMessage;
import com.zhbit.oa.domain.Mechanism;
import com.zhbit.oa.service.AccountMessageService;
import com.zhbit.oa.service.AccountService;
import com.zhbit.oa.service.MechanismService;
import com.zhbit.oa.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private MechanismService mechanismService;
    @Autowired
    private AccountMessageService accountMessageService;
    @Autowired
    private PermissionService permissionService;

    //验证数据库中账号密码
    public String login(Account account) {
        System.out.println("service=>" + account);
        Account account1 = this.accountMapper.login(account);
        System.out.println("search=>" + account1);
        //根据账号密码判断数据库中是否存在该用户，如果查询结果为空则账号或密码错误
        if (account1 != null) {
            //判断账户是否为冻结状态
            if (account1.getAstatus().equals("N")) {
                return "dongjie";
            }else if(accountMessageService.findByAid(account.getAusername())==null){
                System.out.println("service  null");
                return "null";
            }
            return "success";
        } else {
            return "false";
        }
    }

    //添加新账户
    public boolean addAccount(Account account) {
        account.setAid(account.getAusername());
        //将account中的cid角色名字改为真的cid
        account.setCid(permissionService.findByCname(account.getCid()).getCid());
        //账户新建时状态默认为开启

        accountMapper.addAccount(account);
        return true;
    }

    //将账户和姓名、部门组成list
    public List<AccountList> accountList() {
        //获得所有账户
        List<Account> listAll = findAll();
        List<AccountList> list = new ArrayList();
        System.out.println("listAll的size==" + listAll.size());
        for (int i = 0; i < listAll.size(); i++) {
            AccountList accountList = new AccountList();
            //用获得的账号获得对应的账号信息
            AccountMessage accountMessage = findMessageByAid(listAll.get(i).getAid());
            //将账号、密码、姓名和部门存进一个实体类
            accountList.setAusername(listAll.get(i).getAusername());
            //获取账号角色id并改为角色名
            accountList.setCid(permissionService.findByCid(listAll.get(i).getCid()).getCname());
            //获取账号状态并判断
            accountList.setApassword(listAll.get(i).getApassword());
            if (listAll.get(i).getAstatus().equals("Y")) {
                accountList.setAstatus("启用");
            } else {
                accountList.setAstatus("冻结");
            }
            //以防空指针报错
            if (accountMessage == null) {
                accountList.setaMname("");
                accountList.setaMmechanism("");
            } else {
                accountList.setaMname(accountMessage.getaMname());
                Mechanism mechanism = mechanismService.findOne(accountMessage.getaMmechanism());
                accountList.setaMmechanism(mechanism.getMname());
            }
            list.add(accountList);
        }
        System.out.println("Service中输出list的==" + list);
        return list;
    }

    //删除对应id数据
    public boolean deleteAccount(String aid[]) {
        for (int i = 0; i < aid.length; i++) {
            System.out.println("deleteService中aid==>" + aid[i]);
            accountMapper.deleteAccount(aid[i]);
        }
        return true;
    }

    //查询对应aid的数据
    public Account findOne(String aid) {
        return accountMapper.selectByAid(aid);
    }

    //更新
    public boolean updateAccount(Account account) {
        accountMapper.deleteAccount(account.getAid());
        account.setAid(account.getAusername());
        addAccount(account);
        return true;
    }

    //通过aid查询对应的账户个人信息
    public AccountMessage findMessageByAid(String aid) {
        AccountMessage accountMessage = accountMessageService.findByAid(aid);
        return accountMessage;
    }

    //获取表中所有数据
    public List<Account> findAll() {
        List<Account> list = accountMapper.findAll();
        return list;
    }
}
