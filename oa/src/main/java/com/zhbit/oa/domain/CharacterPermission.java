package com.zhbit.oa.domain;

public class CharacterPermission {
    private String cpid;
    private String cid;
    private String pid;

    public String getCpid() {
        return cpid;
    }

    public void setCpid(String cpid) {
        this.cpid = cpid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "CharacterPermission{" +
                "cpid='" + cpid + '\'' +
                ", cid='" + cid + '\'' +
                ", pid='" + pid + '\'' +
                '}';
    }
}
