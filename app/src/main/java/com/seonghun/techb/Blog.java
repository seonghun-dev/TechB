package com.seonghun.techb;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class Blog {

    private int resourceID;
    private String title;
    private String url;
    private String content;
    private String company;
    private String img;
    private boolean bookMark;
    public Blog() {

    }

    public Blog(int id, String title, String url, String content, String img) {
        this.resourceID = id;
        this.title = title;
        this.url = url;
        this.content = content;
        this.img = img;
    }

    private String clearTag(String withTag) {
        return withTag.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "").replaceAll("&amp;", "");
    }

    public int getResourceID() {
        return resourceID;
    }

    public String getTitle() {
        return title;
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

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public void setTitle(String title) {
        this.title = clearTag(title);
    }

    public void setContent(String content) {
        this.content = clearTag(content) + "...";
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return this.company;
    }

}