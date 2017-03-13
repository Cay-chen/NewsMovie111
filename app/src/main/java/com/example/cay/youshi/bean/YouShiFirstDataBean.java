package com.example.cay.youshi.bean;

import java.util.List;

/**
 * Created by Cay-chen on 2017/3/11.
 */

public class YouShiFirstDataBean {
    private String resCode;
    private List<YouShiMovieDealisBean> movie;
    private List<YouShiMovieDealisBean> tv;
    private List<YouShiBannerDataBean> banners;
    private String almm_url;

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public List<YouShiMovieDealisBean> getMovie() {
        return movie;
    }

    public void setMovie(List<YouShiMovieDealisBean> movie) {
        this.movie = movie;
    }

    public List<YouShiMovieDealisBean> getTv() {
        return tv;
    }

    public void setTv(List<YouShiMovieDealisBean> tv) {
        this.tv = tv;
    }

    public List<YouShiBannerDataBean> getBanners() {
        return banners;
    }

    public void setBanners(List<YouShiBannerDataBean> banners) {
        this.banners = banners;
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
                ", movie=" + movie +
                ", tv=" + tv +
                ", banners=" + banners +
                ", almm_url='" + almm_url + '\'' +
                '}';
    }
}
