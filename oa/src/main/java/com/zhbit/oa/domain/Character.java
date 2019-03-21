package com.zhbit.oa.domain;

import java.util.Arrays;

public class Character {
    private String cid;
    private String cname;
    private String cusing;
    private String permission[];

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCusing() {
        return cusing;
    }

    public void setCusing(String cusing) {
        this.cusing = cusing;
    }

    public String[] getPermission() {
        return permission;
    }

    public void setPermission(String[] permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "Character{" +
                "cid='" + cid + '\'' +
                ", cname='" + cname + '\'' +
                ", cusing='" + cusing + '\'' +
                ", permission=" + Arrays.toString(permission) +
                '}';
    }
}
