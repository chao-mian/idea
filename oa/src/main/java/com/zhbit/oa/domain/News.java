package com.zhbit.oa.domain;

public class News {
    private String nid;
    private String ntitle;
    private String ntype;
    private String nsend;
    private String ntime;
    private String nmessage;

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getNtitle() {
        return ntitle;
    }

    public void setNtitle(String ntitle) {
        this.ntitle = ntitle;
    }

    public String getNtype() {
        return ntype;
    }

    public void setNtype(String ntype) {
        this.ntype = ntype;
    }

    public String getNsend() {
        return nsend;
    }

    public void setNsend(String nsend) {
        this.nsend = nsend;
    }

    public String getNtime() {
        return ntime;
    }

    public void setNtime(String ntime) {
        this.ntime = ntime;
    }

    public String getNmessage() {
        return nmessage;
    }

    public void setNmessage(String nmessage) {
        this.nmessage = nmessage;
    }

    @Override
    public String toString() {
        return "News{" +
                "nid='" + nid + '\'' +
                ", ntitle='" + ntitle + '\'' +
                ", ntype='" + ntype + '\'' +
                ", nsend='" + nsend + '\'' +
                ", ntime='" + ntime + '\'' +
                ", nmessage='" + nmessage + '\'' +
                '}';
    }
}
