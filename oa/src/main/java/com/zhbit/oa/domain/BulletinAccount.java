package com.zhbit.oa.domain;

public class BulletinAccount {
    private String baid;
    private String bid;
    private String aid;
    private String bastatus;

    public String getBaid() {
        return baid;
    }

    public void setBaid(String baid) {
        this.baid = baid;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getBastatus() {
        return bastatus;
    }

    public void setBastatus(String bastatus) {
        this.bastatus = bastatus;
    }

    @Override
    public String toString() {
        return "BulletinAccount{" +
                "baid='" + baid + '\'' +
                ", bid='" + bid + '\'' +
                ", aid='" + aid + '\'' +
                ", bastatus='" + bastatus + '\'' +
                '}';
    }
}
