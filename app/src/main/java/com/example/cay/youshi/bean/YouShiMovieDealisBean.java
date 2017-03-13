package com.example.cay.youshi.bean;

/**
 * Created by Cay on 2017/3/11.
 */

public class YouShiMovieDealisBean {
    private String movie_count; //数据ID
    private String id;  //电影id
    private String name;// 电影名字
    private String now_num;//当前集数
    private String total_num;//总共集数
    private String subtype;// 视频类型  1 为电视剧  2为电影
    private String ratings_count;// 评价人数
    private String genres;// 类型
    private String countries;// 制片地区
    private String year;//  上映时间
    private String director;//  导演
    private String act;//  主演
    private String code;// 评分
    private String other_name;//  又名
    private String baidu_url;//  百度链接
    private String img_url;// 图片地址
    private String up_time;// 上传时间
    private String log;// 简介

    public String getMovie_count() {
        return movie_count;
    }

    public void setMovie_count(String movie_count) {
        this.movie_count = movie_count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNow_num() {
        return now_num;
    }

    public void setNow_num(String now_num) {
        this.now_num = now_num;
    }

    public String getTotal_num() {
        return total_num;
    }

    public void setTotal_num(String total_num) {
        this.total_num = total_num;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getRatings_count() {
        return ratings_count;
    }

    public void setRatings_count(String ratings_count) {
        this.ratings_count = ratings_count;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOther_name() {
        return other_name;
    }

    public void setOther_name(String other_name) {
        this.other_name = other_name;
    }

    public String getBaidu_url() {
        return baidu_url;
    }

    public void setBaidu_url(String baidu_url) {
        this.baidu_url = baidu_url;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getUp_time() {
        return up_time;
    }

    public void setUp_time(String up_time) {
        this.up_time = up_time;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    @Override
    public String toString() {
        return "YouShiMovieDealisBean{" +
                "movie_count='" + movie_count + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", now_num='" + now_num + '\'' +
                ", total_num='" + total_num + '\'' +
                ", subtype='" + subtype + '\'' +
                ", ratings_count='" + ratings_count + '\'' +
                ", genres='" + genres + '\'' +
                ", countries='" + countries + '\'' +
                ", year='" + year + '\'' +
                ", director='" + director + '\'' +
                ", act='" + act + '\'' +
                ", code='" + code + '\'' +
                ", other_name='" + other_name + '\'' +
                ", baidu_url='" + baidu_url + '\'' +
                ", img_url='" + img_url + '\'' +
                ", up_time='" + up_time + '\'' +
                ", log='" + log + '\'' +
                '}';
    }
}
