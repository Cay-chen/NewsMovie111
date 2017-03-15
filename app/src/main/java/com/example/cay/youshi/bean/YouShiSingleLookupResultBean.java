package com.example.cay.youshi.bean;

import java.util.List;

/**
 * Created by Cay on 2017/3/13.
 */

public class YouShiSingleLookupResultBean {
    private String resCode;
    private List<YouShiMovieDealisBean> result;

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public List<YouShiMovieDealisBean> getResult() {
        return result;
    }

    public void setResult(List<YouShiMovieDealisBean> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "YouShiSingleLookupResultBean{" +
                "resCode='" + resCode + '\'' +
                ", result=" + result +
                '}';
    }
}
