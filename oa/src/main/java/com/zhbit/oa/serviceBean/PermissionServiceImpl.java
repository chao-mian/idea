package com.zhbit.oa.serviceBean;

import com.zhbit.oa.dao.CharacterMapper;
import com.zhbit.oa.dao.CharacterPermissionMapper;
import com.zhbit.oa.dao.PermissionMapper;
import com.zhbit.oa.domain.Character;
import com.zhbit.oa.domain.CharacterPermission;
import com.zhbit.oa.domain.Permission;
import com.zhbit.oa.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private CharacterMapper characterMapper;
    @Autowired
    private CharacterPermissionMapper characterPermissionMapper;

    public boolean addPermission(Permission permission) {
        permission.setPid(newPid());
        permissionMapper.insert(permission);
        return true;
    }

    public List<Permission> findAll() {
        List<Permission> list = permissionMapper.selectAll();
        return list;
    }

    public Permission findOne(String pid) {
        Permission permission = permissionMapper.selecrByPid(pid);
        return permission;
    }

    public boolean deletePermission(String pid[]) {
        for (int i = 0; i < pid.length; i++) {
            permissionMapper.delete(pid[i]);
        }
        return true;
    }

    public boolean updatePermission(Permission permission) {
        permissionMapper.update(permission);
        return true;
    }

    public String newPid() {
        //生成两位16进制数
        StringBuffer m = new StringBuffer();
        for (int i = 0; i < 2; i++) {
            m.append(Integer.toHexString(new Random().nextInt(16)));
        }
        String pid = m.toString().toUpperCase();
        //数据库中查找该mid，能查到则重新生成
        if (permissionMapper.selecrByPid(pid) != null) {
            System.out.println("pid已存在，重新生成");
            return newPid();
        }
        return pid;
    }

    public boolean addCharacter(Character character) {
        character.setCid(newCid());
        character.setCusing("Y");
        characterMapper.insert(character);
        return true;
    }

    public List<Character> findAllCharacter() {
        //查到所有角色名
        List<Character> list = characterMapper.selectAll();
        System.out.println("所有角色" + list);
        for (int i = 0; i < list.size(); i++) {
            //根据角色id查到对应的权限
            List<CharacterPermission> cPlist = characterPermissionMapper.selectByCid(list.get(i).getCid());

            String[] str = new String[cPlist.size()];
            for (int j = 0; j < cPlist.size(); j++) {
                str[j] = permissionMapper.selecrByPid(cPlist.get(j).getPid()).getPname();
            }
            list.get(i).setPermission(str);
            if (list.get(i).getCusing().equals("Y")) {
                list.get(i).setCusing("启用");
            } else {
                list.get(i).setCusing("停用");
            }
        }
        return list;
    }

    public Character findByCid(String cid) {
        Character character = characterMapper.selectByCid(cid);
        return character;
    }

    public Character findByCname(String cname) {
        Character character = characterMapper.selectByCname(cname);
        return character;
    }

    public List<CharacterPermission> findByCidInCp(String cid) {
        List<CharacterPermission> list = characterPermissionMapper.selectByCid(cid);
        return list;
    }

    public boolean deleteCharacter(String cid[]) {
        for (int i = 0; i < cid.length; i++) {
            characterMapper.delete(cid[i]);
            characterPermissionMapper.delete(cid[i]);
        }

        return true;
    }

    public boolean updateCharacter(String str[]) {
        Character character = new Character();
        CharacterPermission characterPermission = new CharacterPermission();
        character.setCid(str[0]);
        character.setCname(str[1]);
        character.setCusing(str[2]);

        characterPermissionMapper.delete(str[0]);
        characterPermission.setCid(str[0]);
        for (int i = 3; i < str.length; i++) {
            characterPermission.setCpid(newCpid());
            characterPermission.setPid(str[i]);
            characterPermissionMapper.insert(characterPermission);
        }
        characterMapper.update(character);
        return true;
    }

    public boolean addCharacterPermission(String str[]) {
        CharacterPermission characterPermission = new CharacterPermission();
        Character character = new Character();
        String cid = newCid();
        character.setCid(cid);
        character.setCname(str[0]);
        character.setCusing("Y");
        characterMapper.insert(character);

        characterPermission.setCid(cid);
        for (int i = 1; i < str.length; i++) {
            characterPermission.setCpid(newCpid());
            characterPermission.setPid(str[i]);
            characterPermissionMapper.insert(characterPermission);
        }
        System.out.println("character中" + character);
        System.out.println("\ncharacterPermission中" + characterPermission);
        return true;
    }


    public String newCid() {
        //生成两位16进制数
        StringBuffer m = new StringBuffer();
        for (int i = 0; i < 2; i++) {
            m.append(Integer.toHexString(new Random().nextInt(16)));
        }
        String cid = m.toString().toUpperCase();
        //数据库中查找该mid，能查到则重新生成
        if (characterMapper.selectByCid(cid) != null) {
            System.out.println("cid已存在，重新生成");
            return newCid();
        }
        return cid;
    }

    public String newCpid() {
        //生成两位16进制数
        StringBuffer m = new StringBuffer();
        for (int i = 0; i < 3; i++) {
            m.append(Integer.toHexString(new Random().nextInt(16)));
        }
        String cpid = m.toString().toUpperCase();
        //数据库中查找该id，能查到则重新生成
        if (characterPermissionMapper.selectByCpid(cpid) != null) {
            System.out.println("cpid已存在，重新生成");
            return newCpid();
        }
        return cpid;
    }
}
