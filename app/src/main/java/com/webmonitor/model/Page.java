package com.webmonitor.model;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Page {

    private Long id;
    private String title;
    private String url;
    private String imageSource;
    private String content;
    private Long timeInterval; //miliseconds
    private Double percentage;
    private Date lastTime;
    private Date lastUpdate;
    private boolean allowMobileConnection;
    private String httpRequestMethod;


    public Page(Long id, String title, String url, String imageSource, Long timeInterval, boolean allowMobileConnection, Double percentage) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.imageSource = imageSource;
        this.timeInterval = timeInterval;
        this.lastTime = Calendar.getInstance().getTime();
        this.allowMobileConnection = allowMobileConnection;
        this.percentage = percentage;
    }

    public Page(Long id, String title, String url, String imageSource, String content, Long timeInterval, boolean allowMobileConnection, Double percentage) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.imageSource = imageSource;
        this.timeInterval = timeInterval;
        this.lastTime = Calendar.getInstance().getTime();
        this.allowMobileConnection = allowMobileConnection;
        this.percentage = percentage;
        this.content = content;
    }

    public Page(Long id, String title, String url, String imageSource, String content, Long timeInterval, boolean allowMobileConnection, Double percentage, String httpRequestMethod) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.imageSource = imageSource;
        this.timeInterval = timeInterval;
        this.lastTime = Calendar.getInstance().getTime();
        this.allowMobileConnection = allowMobileConnection;
        this.percentage = percentage;
        this.content = content;
        this.httpRequestMethod = httpRequestMethod;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public Long getTimeInterval(){ return timeInterval; }

    public void setTimeInterval(Long timeInterval) { this.timeInterval = timeInterval; }

    public Date getLastTime(){ return lastTime; }

    public void setLastTime(Date lastTime){ this.lastTime = lastTime; }

    public Date getLastUpdate(){ return lastUpdate; }

    public void setLastUpdate(Date lastUpdate){ this.lastUpdate = lastUpdate; }

    public boolean getAllowMobileConnection(){ return this.allowMobileConnection; }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public void setAllowMobileConnection(boolean allowMobileConnection) { this.allowMobileConnection = allowMobileConnection; }

    public String getHttpRequestMethod() {
        return httpRequestMethod;
    }

    public void setHttpRequestMethod(String httpRequestMethod) {
        this.httpRequestMethod = httpRequestMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return Objects.equals(id, page.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}