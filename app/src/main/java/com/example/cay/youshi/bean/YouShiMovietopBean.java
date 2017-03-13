package com.example.cay.youshi.bean;

/**
 * Created by Cay on 2017/3/13.
 */

public class YouShiMovietopBean {
    private String name;
    private String img_url;
    private String movie_id;
    private String title;
    private String ad_url;
    private String type_ad;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAd_url() {
        return ad_url;
    }

    public void setAd_url(String ad_url) {
        this.ad_url = ad_url;
    }

    public String getType_ad() {
        return type_ad;
    }

    public void setType_ad(String type_ad) {
        this.type_ad = type_ad;
    }

    @Override
    public String toString() {
        return "YouShiMovietopBean{" +
                "name='" + name + '\'' +
                ", img_url='" + img_url + '\'' +
                ", movie_id='" + movie_id + '\'' +
                ", title='" + title + '\'' +
                ", ad_url='" + ad_url + '\'' +
                ", type_ad='" + type_ad + '\'' +
                '}';
    }
}
