package com.example.cay.youshi.bean;

/**
 * Created by Cay-chen on 2017/3/11.
 */

public class YouShiBannerDataBean {
    private String banner_id;
    private String name;
    private String img_url;
    private String movie_id;
    private String type;
    private String summary;

    public String getBanner_id() {
        return banner_id;
    }

    public void setBanner_id(String banner_id) {
        this.banner_id = banner_id;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "YouShiBannerDataBean{" +
                "banner_id='" + banner_id + '\'' +
                ", name='" + name + '\'' +
                ", img_url='" + img_url + '\'' +
                ", movie_id='" + movie_id + '\'' +
                ", type='" + type + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}
