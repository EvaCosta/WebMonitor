package com.webmonitor.model;

public class Page {

    private String title;
    private String url;
    private String imageSource;
    private String date;


    public Page(String title, String url, String imageSource, String date) {
        this.title = title;
        this.url = url;
        this.imageSource = imageSource;
        this.date = date;
    }

    public Page() { }

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
}
