package com.einsteinford.kknews.beans;

/**
 * Created by KK on 2016-10-31.
 */

public class NewsRequestParams {

    private String channelId;
    private String channelName;
    private String title;
    private int page;
    private int needContent;
    private int needHtml;

    public NewsRequestParams() {
        this.channelId = "";
        this.channelName = "";
        this.title = "";
        this.page = 1;
        this.needContent = 0;
        this.needHtml = 1;
    }

    public String getChannelId() {
        return channelId;
    }

    public NewsRequestParams setChannelId(String channelId) {
        this.channelId = channelId;
        return this;        //每次set对象后还返回自己
    }

    public String getChannelName() {
        return channelName;
    }

    public NewsRequestParams setChannelName(String channelName) {
        this.channelName = channelName;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public NewsRequestParams setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getPage() {
        return page;
    }

    public NewsRequestParams setPage(int page) {
        this.page = page;
        return this;
    }

    public int getNeedContent() {
        return needContent;
    }

    public NewsRequestParams setNeedContent(int needContent) {
        this.needContent = needContent;
        return this;
    }

    public int getNeedHtml() {
        return needHtml;
    }

    public NewsRequestParams setNeedHtml(int needHtml) {
        this.needHtml = needHtml;
        return this;
    }
}
