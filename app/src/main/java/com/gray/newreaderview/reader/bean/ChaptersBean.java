package com.gray.newreaderview.reader.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author wjy on 2018/4/17.
 */
public class ChaptersBean implements Serializable {
    /**
     * id : 1458
     * name : 第001章 头上绿了
     * content : null
     * authorContent : null
     * currency : 0
     * updateTime : 1523933724
     * isBuy : false
     */

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("content")
    private String content;
    @SerializedName("authorContent")
    private String authorContent;
    @SerializedName("currency")
    private int currency;
    @SerializedName("updateTime")
    private long updateTime;
    @SerializedName("isBuy")
    private boolean isBuy;
    //自加字段 是否被选中
    private transient boolean isChecked;
    //是否正在下载
    private transient boolean isDownload;
    //在目录的序号
    private transient int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isDownload() {
        return isDownload;
    }

    public void setDownload(boolean download) {
        isDownload = download;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name == null ? "" : name;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content == null ? "" : content;
    }

    public String getAuthorContent() {
        return authorContent == null ? "" : authorContent;
    }

    public void setAuthorContent(String authorContent) {
        this.authorContent = authorContent == null ? "" : authorContent;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isBuy() {
        return isBuy;
    }

    public void setBuy(boolean buy) {
        isBuy = buy;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ChaptersBean && id == ((ChaptersBean) obj).getId();
    }

    @Override
    public String toString() {
        return name;
    }
}