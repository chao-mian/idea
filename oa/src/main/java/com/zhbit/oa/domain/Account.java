package com.zhbit.oa.domain;

public class Account {
    private String aid;
    private String cid;
    private String ausername;
    private String apassword;
    private String astatus;

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getAusername() {
        return ausername;
    }

    public void setAusername(String ausername) {
        this.ausername = ausername;
    }

    public String getApassword() {
        return apassword;
    }

    public void setApassword(String apassword) {

        this.apassword = apassword;
    }

    public String getAstatus() {
        return astatus;
    }

    public void setAstatus(String astatus) {
        this.astatus = astatus;
    }

    @Override
    public String toString() {
        return "Account{" +
                "aid='" + aid + '\'' +
                ", cid='" + cid + '\'' +
                ", ausername='" + ausername + '\'' +
                ", apassword='" + apassword + '\'' +
                ", astatus='" + astatus + '\'' +
                '}';
    }
}
