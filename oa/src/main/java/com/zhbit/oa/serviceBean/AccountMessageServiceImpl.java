package com.zhbit.oa.serviceBean;

import com.zhbit.oa.dao.AccountMapper;
import com.zhbit.oa.dao.AccountMessageMapper;
import com.zhbit.oa.domain.Account;
import com.zhbit.oa.domain.AccountMessage;
import com.zhbit.oa.domain.Mechanism;
import com.zhbit.oa.service.AccountMessageService;
import com.zhbit.oa.service.AccountService;
import com.zhbit.oa.service.MechanismService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class AccountMessageServiceImpl implements AccountMessageService {

    @Autowired
    private AccountMessageMapper accountMessageMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountService accountService;
    @Autowired
    private MechanismService mechanismService;

    //通过aid查找数据
    public AccountMessage findByAid(String aid) {
        AccountMessage accountMessage = accountMessageMapper.selectByAid(aid);
        return accountMessage;
    }

    //通过aMid查询
    public AccountMessage findByaMid(String aMid) {
        AccountMessage accountMessage = accountMessageMapper.selectByaMid(aMid);
        accountMessage.setaMmechanism(mechanismService.findOne(accountMessage.getaMmechanism()).getMname());
        return accountMessage;
    }

    //插入新数据
    public boolean addAccountMessage(AccountMessage accountMessage) {
        System.out.println("MessageService中输出message===>" + accountMessage);
        accountMessage.setaMid(newaMid());
        //将accountMessage中的部门改为对应mid
        for (int i = 0; i < mechanismService.findAll().size(); i++) {
            if (mechanismService.findAll().get(i).getMname().equals(accountMessage.getaMmechanism())) {
                accountMessage.setaMmechanism(mechanismService.findAll().get(i).getMid());
            }
        }
        accountMessage.setaMleave(accountMessage.getaMentry());
        //插入数据
        if (accountMessageMapper.addAccountMessage(accountMessage)) {
            System.out.println("MessageService中输出:插入成功");
            List<AccountMessage> messagelist = accountMessageMapper.selectAll();
            List<Mechanism> mechanismlist = mechanismService.findAll();
            for (int i = 0; i < mechanismlist.size(); i++) {
                String mid = mechanismlist.get(i).getMid();
                Integer num = 0;
                for (AccountMessage accountMessage1 : messagelist) {
                    if (accountMessage1.getaMmechanism().equals(mid)) {
                        num++;
                    }
                }
                mechanismlist.get(i).setMpeopleNum(num);
                System.out.println(mechanismlist.get(i));
                mechanismService.updateMechanism(mechanismlist.get(i));
            }
            return true;
        } else {
            System.out.println("MessageService中输出:插入失败");
            return false;
        }
    }

    //更新
    public boolean updateMessage(AccountMessage accountMessage) {
        accountMessage.setaMmechanism(mechanismService.findByMname(accountMessage.getaMmechanism()).getMid());
        if(accountMessage.getaMwork().equals("离职")){
            Account account = accountService.findOne(accountMessage.getAid());
            account.setAstatus("N");
            accountMapper.updateAccount(account);
            accountMessageMapper.updateMessage(accountMessage);
        }else {
            Account account = accountService.findOne(accountMessage.getAid());
            account.setAstatus("Y");
            accountMapper.updateAccount(account);
            accountMessageMapper.updateMessage(accountMessage);
        }
        return true;
    }

    //生成mid并匹配数据库是否已经存在该id
    public String newaMid() {
        //生成两位16进制数
        StringBuffer m = new StringBuffer();
        for (int i = 0; i < 3; i++) {
            m.append(Integer.toHexString(new Random().nextInt(16)));
        }
        String aMid = m.toString().toUpperCase();
        //数据库中查找该mid，能查到则重新生成
        if (accountMessageMapper.selectByaMid(aMid) != null) {
            System.out.println("aMid已存在，重新生成");
            return newaMid();
        }
        return aMid;
    }

    //遍历accountMessage数据表
    public List<AccountMessage> findAll() {
        List<AccountMessage> list = accountMessageMapper.selectAll();
//        将列表中部门id替换成部门名
        for (int i = 0; i < list.size(); i++) {

            list.get(i).setaMmechanism(mechanismService.findOne(list.get(i).getaMmechanism()).getMname());
        }
        return list;
    }
}
