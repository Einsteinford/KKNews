package com.einsteinford.kknews.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Article {

//    private String content;
//    private List<String> allList;
    private String pubData;
//    private boolean havePic;
    private String title;
    @SerializedName("imageurls")
    private List<ImageUrls> imgList;
    private String desc;
    private String source;
    private String link;
    private String html;

    public String getPubData() {
        return pubData;
    }

    public void setPubData(String pubData) {
        this.pubData = pubData;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ImageUrls> getImgList() {
        return imgList;
    }

    public void setImgList(List<ImageUrls> imgList) {
        this.imgList = imgList;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }


}
