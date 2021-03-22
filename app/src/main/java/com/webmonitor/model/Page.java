package com.webmonitor.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class Page {

    private Long id;
    private String title;
    private String url;
    private String imageSource;
    private String date;
    private Long timeInterval; //minutos
    private Date lastTime;


    public Page(Long id, String title, String url, String imageSource, String date, Long timeInterval) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.imageSource = imageSource;
        this.date = date;
        this.timeInterval = timeInterval;
        this.lastTime = Calendar.getInstance().getTime();
    }

    public Page() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getTimeInterval(){ return timeInterval; }

    public void setTimeInterval(Long timeInterval) { this.timeInterval = timeInterval; }

    public Date getLastTime(){ return lastTime; }

    public void setLastTime(Date lastTime){ this.lastTime = lastTime; }
}
