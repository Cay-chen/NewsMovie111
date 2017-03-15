package com.example.cay.youshi.bean;

import java.util.List;

/**
 * Created by Cay on 2017/3/15.
 */

public class YouShiTodayBackResultBean {
    private String resCode;
    private List<YouShiTodayUpdateBean> result;

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public List<YouShiTodayUpdateBean> getResult() {
        return result;
    }

    public void setResult(List<YouShiTodayUpdateBean> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "YouShiTodayBackResultBean{" +
                "resCode='" + resCode + '\'' +
                ", result=" + result +
                '}';
    }
}
