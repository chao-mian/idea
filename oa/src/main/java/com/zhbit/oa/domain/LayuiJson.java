package com.zhbit.oa.domain;

import java.util.List;

public class LayuiJson {
    private String code;
    private String msg;
    private String count;
    private List<Processes> data;

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

    public List<Processes> getData() {
        return data;
    }

    public void setData(List<Processes> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LayuiJson{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", count='" + count + '\'' +
                ", data=" + data +
                '}';
    }
}
