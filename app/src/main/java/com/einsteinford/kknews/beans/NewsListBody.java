package com.einsteinford.kknews.beans;

import com.google.gson.annotations.SerializedName;

public class NewsListBody {

    private int retCode;
    @SerializedName("pagebean")
    private NewsPage page;

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public NewsPage getPage() {
        return page;
    }

    public void setPage(NewsPage page) {
        this.page = page;
    }
}
