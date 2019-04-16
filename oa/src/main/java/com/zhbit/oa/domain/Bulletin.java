package com.zhbit.oa.domain;

public class Bulletin {
    private String bid;
    private String btitle;
    private String bsend;
    private String btime;
    private String bcontent;

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getBtitle() {
        return btitle;
    }

    public void setBtitle(String btitle) {
        this.btitle = btitle;
    }

    public String getBsend() {
        return bsend;
    }

    public void setBsend(String bsend) {
        this.bsend = bsend;
    }

    public String getBtime() {
        return btime;
    }

    public void setBtime(String btime) {
        this.btime = btime;
    }

    public String getBcontent() {
        return bcontent;
    }

    public void setBcontent(String bcontent) {
        this.bcontent = bcontent;
    }

    @Override
    public String toString() {
        return "Bulletin{" +
                "bid='" + bid + '\'' +
                ", btitle='" + btitle + '\'' +
                ", bsend='" + bsend + '\'' +
                ", btime='" + btime + '\'' +
                ", bcontent='" + bcontent + '\'' +
                '}';
    }
}
