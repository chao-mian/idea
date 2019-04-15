package com.zhbit.oa.domain;

public class Processes1 {
    private String prid;
    private String prname;
    private String prtype;
    private String prstatus;

    public String getPrid() {
        return prid;
    }

    public void setPrid(String prid) {
        this.prid = prid;
    }

    public String getPrname() {
        return prname;
    }

    public void setPrname(String prname) {
        this.prname = prname;
    }

    public String getPrtype() {
        return prtype;
    }

    public void setPrtype(String prtype) {
        this.prtype = prtype;
    }

    public String getPrstatus() {
        return prstatus;
    }

    public void setPrstatus(String prstatus) {
        this.prstatus = prstatus;
    }

    @Override
    public String toString() {
        return "Processes1{" +
                "prid='" + prid + '\'' +
                ", prname='" + prname + '\'' +
                ", prtype='" + prtype + '\'' +
                ", prstatus='" + prstatus + '\'' +
                '}';
    }
}
