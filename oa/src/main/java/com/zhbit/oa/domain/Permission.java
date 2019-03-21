package com.zhbit.oa.domain;

public class Permission {
    private String pid;
    private String pname;
    private String pvalue;
    private String pusing;
    private String pdescription;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPvalue() {
        return pvalue;
    }

    public void setPvalue(String pvalue) {
        this.pvalue = pvalue;
    }

    public String getPusing() {
        return pusing;
    }

    public void setPusing(String pusing) {
        this.pusing = pusing;
    }

    public String getPdescription() {
        return pdescription;
    }

    public void setPdescription(String pdescription) {
        this.pdescription = pdescription;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "pid='" + pid + '\'' +
                ", pname='" + pname + '\'' +
                ", pvalue='" + pvalue + '\'' +
                ", pusing='" + pusing + '\'' +
                ", pdescription='" + pdescription + '\'' +
                '}';
    }
}
