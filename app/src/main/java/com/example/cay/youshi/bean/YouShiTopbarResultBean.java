package com.example.cay.youshi.bean;

/**
 * Created by Cay-chen on 2017/3/13.
 */

public class YouShiTopbarResultBean {
    private String resCode;
    private YouShiTopbar result;

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public YouShiTopbar getResult() {
        return result;
    }

    public void setResult(YouShiTopbar result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "YouShiTopbarResultBean{" +
                "resCode='" + resCode + '\'' +
                ", result=" + result +
                '}';
    }
}
