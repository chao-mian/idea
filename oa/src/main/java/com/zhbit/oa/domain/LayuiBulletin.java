package com.zhbit.oa.domain;

import java.util.List;

public class LayuiBulletin {
    private String code;
    private String msg;
    private String count;
    private List<Bulletin> data;
    private int page;
    private int limit;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<Bulletin> getData() {
        return data;
    }

    public void setData(List<Bulletin> data) {
        this.data = data;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "LayuiBulletin{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", count='" + count + '\'' +
                ", data=" + data +
                ", page=" + page +
                ", limit=" + limit +
                '}';
    }
}
