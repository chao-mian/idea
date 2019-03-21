package com.zhbit.oa.domain;

public class AccountCharacter {
    private String acid;
    private String aid;
    private String cid;

    public String getAcid() {
        return acid;
    }

    public void setAcid(String acid) {
        this.acid = acid;
    }

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

    @Override
    public String toString() {
        return "AccountCharacter{" +
                "acid='" + acid + '\'' +
                ", aid='" + aid + '\'' +
                ", cid='" + cid + '\'' +
                '}';
    }
}
