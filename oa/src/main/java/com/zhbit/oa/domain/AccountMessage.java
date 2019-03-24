package com.zhbit.oa.domain;

import java.util.Date;

public class AccountMessage {
    private String aMid;
    private String aid;
    private String aMavatar;
    private String aMname;
    private String aMIDcard;
    private String aMnationality;
    private String aMmarriage;
    private Integer aMage;
    private String aMsex;
    private String aMbirthday;
    private String aMmechanism;
    private String aMBirthplace;
    private String aMeducation;
    private String aMpoliticalStatus;
    private String aMposition;
    private String aMphone;
    private String aMemail;
    private String aMaddress;
    private String aMentry;
    private String aMleave;
    private String aMwork;

    public String getaMid() {
        if (aMavatar == null) {
            aMavatar = "";
        }
        return aMid;
    }

    public void setaMid(String aMid) {
        this.aMid = aMid;
    }

    public String getAid() {
        if (aMavatar == null) {
            aMavatar = "";
        }
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getaMavatar() {
        if (aMavatar == null) {
            aMavatar = "";
        }
        return aMavatar;
    }

    public void setaMavatar(String aMavatar) {
        this.aMavatar = aMavatar;
    }

    public String getaMname() {
        if (aMname == null) {
            aMname = "";
        }
        return aMname;
    }

    public void setaMname(String aMname) {
        this.aMname = aMname;
    }

    public String getaMIDcard() {
        if (aMIDcard == null) {
            aMIDcard = "";
        }
        return aMIDcard;
    }

    public void setaMIDcard(String aMIDcard) {
        this.aMIDcard = aMIDcard;
    }

    public String getaMnationality() {
        if (aMnationality == null) {
            aMnationality = "";
        }
        return aMnationality;
    }

    public void setaMnationality(String aMnationality) {
        this.aMnationality = aMnationality;
    }

    public String getaMmarriage() {
        if (aMmarriage == null) {
            aMmarriage = "";
        }
        return aMmarriage;
    }

    public void setaMmarriage(String aMmarriage) {
        this.aMmarriage = aMmarriage;
    }

    public Integer getaMage() {
        if (aMage == null) {
            aMage = 0;
        }
        return aMage;
    }

    public void setaMage(Integer aMage) {
        this.aMage = aMage;
    }

    public String getaMsex() {
        if (aMsex == null) {
            aMsex = "";
        }
        return aMsex;
    }

    public void setaMsex(String aMsex) {
        this.aMsex = aMsex;
    }

    public String getaMbirthday() {
        if (aMbirthday == null) {
            aMbirthday = "";
        }
        return aMbirthday;
    }

    public void setaMbirthday(String aMbirthday) {
        this.aMbirthday = aMbirthday;
    }

    public String getaMmechanism() {
        if (aMmechanism == null) {
            aMmechanism = "";
        }
        return aMmechanism;
    }

    public void setaMmechanism(String aMmechanism) {
        this.aMmechanism = aMmechanism;
    }

    public String getaMBirthplace() {
        if (aMBirthplace == null) {
            aMBirthplace = "";
        }
        return aMBirthplace;
    }

    public void setaMBirthplace(String aMBirthplace) {
        this.aMBirthplace = aMBirthplace;
    }

    public String getaMeducation() {
        if (aMeducation == null) {
            aMeducation = "";
        }
        return aMeducation;
    }

    public void setaMeducation(String aMeducation) {
        this.aMeducation = aMeducation;
    }

    public String getaMpoliticalStatus() {
        if (aMpoliticalStatus == null) {
            aMpoliticalStatus = "";
        }
        return aMpoliticalStatus;
    }

    public void setaMpoliticalStatus(String aMpoliticalStatus) {
        this.aMpoliticalStatus = aMpoliticalStatus;
    }

    public String getaMposition() {
        if (aMposition == null) {
            aMposition = "";
        }
        return aMposition;
    }

    public void setaMposition(String aMposition) {
        this.aMposition = aMposition;
    }

    public String getaMphone() {
        if (aMphone == null) {
            aMphone = "";
        }
        return aMphone;
    }

    public void setaMphone(String aMphone) {
        this.aMphone = aMphone;
    }

    public String getaMaddress() {
        if (aMaddress == null) {
            aMaddress = "";
        }
        return aMaddress;
    }

    public void setaMaddress(String aMaddress) {
        this.aMaddress = aMaddress;
    }

    public String getaMentry() {
        if (aMentry == null) {
            aMentry = "";
        }
        return aMentry;
    }

    public void setaMentry(String aMentry) {
        this.aMentry = aMentry;
    }

    public String getaMleave() {
        if (aMleave == null) {
            aMleave = "";
        }
        return aMleave;
    }

    public void setaMleave(String aMleave) {
        this.aMleave = aMleave;
    }

    public String getaMwork() {
        if (aMwork == null) {
            aMwork = "";
        }
        return aMwork;
    }

    public void setaMwork(String aMwork) {
        this.aMwork = aMwork;
    }

    public String getaMemail() {
        return aMemail;
    }

    public void setaMemail(String aMemail) {
        this.aMemail = aMemail;
    }

    @Override
    public String toString() {
        return "AccountMessage{" +
                "aMid='" + aMid + '\'' +
                ", aid='" + aid + '\'' +
                ", aMavatar='" + aMavatar + '\'' +
                ", aMname='" + aMname + '\'' +
                ", aMIDcard='" + aMIDcard + '\'' +
                ", aMnationality='" + aMnationality + '\'' +
                ", aMmarriage='" + aMmarriage + '\'' +
                ", aMage=" + aMage +
                ", aMsex='" + aMsex + '\'' +
                ", aMbirthday='" + aMbirthday + '\'' +
                ", aMmechanism='" + aMmechanism + '\'' +
                ", aMBirthplace='" + aMBirthplace + '\'' +
                ", aMeducation='" + aMeducation + '\'' +
                ", aMpoliticalStatus='" + aMpoliticalStatus + '\'' +
                ", aMposition='" + aMposition + '\'' +
                ", aMphone='" + aMphone + '\'' +
                ", aMemail='" + aMemail + '\'' +
                ", aMaddress='" + aMaddress + '\'' +
                ", aMentry='" + aMentry + '\'' +
                ", aMleave='" + aMleave + '\'' +
                ", aMwork='" + aMwork + '\'' +
                '}';
    }
}
