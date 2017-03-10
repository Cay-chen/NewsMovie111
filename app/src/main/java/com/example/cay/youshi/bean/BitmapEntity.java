package com.example.cay.youshi.bean;

public class BitmapEntity {
	private String name;
	private String uri;
	private long size;
	private String uri_thumb;
	private long duration;//持续时间
	public BitmapEntity(String name, String uri, long size, String uri_thumb, long duration) {
		super();
		this.name = name;
		this.uri = uri;
		this.size = size;
		this.uri_thumb = uri_thumb;
		this.duration = duration;
	}
	public String getUri_thumb() {
		return uri_thumb;
	}

	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public BitmapEntity() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}

}
