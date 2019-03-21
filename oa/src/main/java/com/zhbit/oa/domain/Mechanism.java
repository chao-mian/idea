package com.zhbit.oa.domain;

public class Mechanism {
    private String mid;
    private String mname;
    private Integer mpeopleNum;
    private String mdescription;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public Integer getMpeopleNum() {
        return mpeopleNum;
    }

    public void setMpeopleNum(Integer mpeopleNum) {
        this.mpeopleNum = mpeopleNum;
    }

    public String getMdescription() {
        return mdescription;
    }

    public void setMdescription(String mdescription) {
        this.mdescription = mdescription;
    }

    @Override
    public String toString() {
        return "Mechanism{" +
                "mid='" + mid + '\'' +
                ", mname='" + mname + '\'' +
                ", mpeopleNum=" + mpeopleNum +
                ", mdescription='" + mdescription + '\'' +
                '}';
    }
}
