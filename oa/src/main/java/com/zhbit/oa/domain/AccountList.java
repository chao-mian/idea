package com.zhbit.oa.domain;

public class AccountList {
    private String cid;
    private String ausername;
    private String apassword;
    private String aMname;
    private String aMmechanism;
    private String astatus;

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

    public String getaMname() {
        return aMname;
    }

    public void setaMname(String aMname) {
        this.aMname = aMname;
    }

    public String getaMmechanism() {
        return aMmechanism;
    }

    public void setaMmechanism(String aMmechanism) {
        this.aMmechanism = aMmechanism;
    }

    public String getAstatus() {
        return astatus;
    }

    public void setAstatus(String astatus) {
        this.astatus = astatus;
    }

    @Override
    public String toString() {
        return "AccountList{" +
                "cid='" + cid + '\'' +
                ", ausername='" + ausername + '\'' +
                ", apassword='" + apassword + '\'' +
                ", aMname='" + aMname + '\'' +
                ", aMmechanism='" + aMmechanism + '\'' +
                ", astatus='" + astatus + '\'' +
                '}';
    }
}
