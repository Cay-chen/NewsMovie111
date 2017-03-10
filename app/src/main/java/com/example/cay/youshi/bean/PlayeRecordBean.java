package com.example.cay.youshi.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by Cay on 2017/3/1.
 */

public class PlayeRecordBean extends DataSupport {
    private String img_url;
    private String name;
    private int type;
    private String movie_id;
    private String play_time;

    public String getPlay_time() {
        return play_time;
    }

    public void setPlay_time(String play_time) {
        this.play_time = play_time;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
