package com.example.cay.youshi.bean;

public class YouShiTodayUpdateBean {
	private String date;
	private String movie;
	private String cha_tv;
	private String rihan_tv;
	private String usa_tv;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMovie() {
		return movie;
	}
	public void setMovie(String movie) {
		this.movie = movie;
	}
	public String getCha_tv() {
		return cha_tv;
	}
	public void setCha_tv(String cha_tv) {
		this.cha_tv = cha_tv;
	}
	public String getRihan_tv() {
		return rihan_tv;
	}
	public void setRihan_tv(String rihan_tv) {
		this.rihan_tv = rihan_tv;
	}
	public String getUsa_tv() {
		return usa_tv;
	}
	public void setUsa_tv(String usa_tv) {
		this.usa_tv = usa_tv;
	}

	@Override
	public String toString() {
		return "YouShiTodayUpdateBean{" +
				"date='" + date + '\'' +
				", movie='" + movie + '\'' +
				", cha_tv='" + cha_tv + '\'' +
				", rihan_tv='" + rihan_tv + '\'' +
				", usa_tv='" + usa_tv + '\'' +
				'}';
	}
}
