package com.example.cay.youshi.bean;

import java.util.List;

/**
 * Created by Cay-chen on 2017/3/11.
 */

public class YouShiFirstDataBean {
    private String resCode;
    private List<List<YouShiMovieDealisBean>> result;
    private List<YouShiBannerDataBean> banners;
    private YouShiBannerDataBean ads;
    private String almm_url;

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public List<List<YouShiMovieDealisBean>> getResult() {
        return result;
    }

    public void setResult(List<List<YouShiMovieDealisBean>> result) {
        this.result = result;
    }

    public List<YouShiBannerDataBean> getBanners() {
        return banners;
    }

    public void setBanners(List<YouShiBannerDataBean> banners) {
        this.banners = banners;
    }

    public YouShiBannerDataBean getAds() {
        return ads;
    }

    public void setAds(YouShiBannerDataBean ads) {
        this.ads = ads;
    }

    public String getAlmm_url() {
        return almm_url;
    }

    public void setAlmm_url(String almm_url) {
        this.almm_url = almm_url;
    }

    @Override
    public String toString() {
        return "YouShiFirstDataBean{" +
                "resCode='" + resCode + '\'' +
                ", result=" + result +
                ", banners=" + banners +
                ", ads=" + ads +
                ", almm_url='" + almm_url + '\'' +
                '}';
    }
}
