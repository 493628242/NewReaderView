package com.gray.newreaderview.reader.util;

import android.content.Context;
import android.graphics.Color;

import com.gray.newreaderview.reader.element.AuthorHeadElement;
import com.gray.newreaderview.reader.element.BigTitleElement;
import com.gray.newreaderview.reader.element.LineElement;
import com.gray.newreaderview.reader.element.SmallTitleElement;
import com.gray.newreaderview.reader.property.PageDefProperty;

import java.io.Serializable;

public class PageProperty implements Serializable {
    private int textSize;
    private int lineSpace;
    private int otherTextSize;
    private int otherTextColor;
    private int bigTitleSize;
    private int bigTitleLineSpace;
    private int textColor;
    private int bgColor;
    private float power;
    private int contentMarginBottom;
    private int authorHeadHeight;
    private int authorContentSpace;
    private int authorHeadContentSpace;
    private int authorLineSpace;
    private int authorTextSize;
    private int type;
    private Context mContext;
    public static PageProperty mPageProperty;

    private PageProperty(Context mContext) {
        this.mContext = mContext;
        initDef();
    }

    public static PageProperty getInstance(Context context) {
        if (mPageProperty == null) {
            if (context == null) {
                return null;
            } else {
                synchronized (PageProperty.class) {
                    if (mPageProperty == null) {
                        return new PageProperty(context);
                    }
                }
            }
        }
        return mPageProperty;
    }

    private void initDef() {
        textSize = UIUtils.sp2px(mContext, LineElement.DEF_TEXT_SIZE);
        lineSpace = UIUtils.dip2px(mContext, LineElement.DEF_TEXT_SIZE - 6);
        otherTextSize = UIUtils.sp2px(mContext, SmallTitleElement.DEF_TEXT_SIZE);
        otherTextColor = 0xFF999999;
        bigTitleSize = UIUtils.sp2px(mContext, BigTitleElement.DEF_TEXT_SIZE);
        bigTitleLineSpace = UIUtils.dip2px(mContext, BigTitleElement.DEF_LINE_SPACE);
        textColor = LineElement.DEF_TEXT_COLOR;
        bgColor = Color.WHITE;
        power = 0.5f;
        contentMarginBottom = UIUtils.dip2px(mContext, 38);
        authorHeadHeight = UIUtils.dip2px(mContext, AuthorHeadElement.DEF_HEAD_HEIGHT);
        authorHeadContentSpace = UIUtils.dip2px(mContext, 30);
        authorLineSpace = UIUtils.dip2px(mContext, 5);
        authorTextSize = UIUtils.sp2px(mContext, 15);
    }


    public void setStyle(PageDefProperty property) {
        bgColor = property.getBgColor();
        textColor = property.getTextColor();
        otherTextColor = property.getOtherTextColor();
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = UIUtils.dip2px(mContext, textSize);
        this.lineSpace = UIUtils.dip2px(mContext, textSize - 6);
    }

    public int getLineSpace() {
        return lineSpace;
    }

    public void setLineSpace(int lineSpace) {
        this.lineSpace = UIUtils.dip2px(mContext, lineSpace);
    }

    public int getOtherTextSize() {
        return otherTextSize;
    }

    public void setOtherTextSize(int otherTextSize) {
        this.otherTextSize = UIUtils.sp2px(mContext, otherTextSize);
    }

    public int getOtherTextColor() {
        return otherTextColor;
    }

    public void setOtherTextColor(int otherTextColor) {
        this.otherTextColor = otherTextColor;
    }

    public int getBigTitleSize() {
        return bigTitleSize;
    }

    public void setBigTitleSize(int bigTitleSize) {
        this.bigTitleSize = UIUtils.sp2px(mContext, bigTitleSize);
    }

    public int getBigTitleLineSpace() {
        return bigTitleLineSpace;
    }

    public void setBigTitleLineSpace(int bigTitleLineSpace) {
        this.bigTitleLineSpace = UIUtils.dip2px(mContext, bigTitleLineSpace);
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }

    public int getContentMarginBottom() {
        return contentMarginBottom;
    }

    public void setContentMarginBottom(int contentMarginBottom) {
        this.contentMarginBottom = UIUtils.dip2px(mContext, contentMarginBottom);
    }

    public int getAuthorHeadHeight() {
        return authorHeadHeight;
    }

    public void setAuthorHeadHeight(int authorHeadHeight) {
        this.authorHeadHeight = UIUtils.dip2px(mContext, authorHeadHeight);
    }

    public int getAuthorContentSpace() {
        return authorContentSpace;
    }

    public void setAuthorContentSpace(int authorContentSpace) {
        this.authorContentSpace = UIUtils.dip2px(mContext, authorContentSpace);
    }

    public int getAuthorHeadContentSpace() {
        return authorHeadContentSpace;
    }

    public void setAuthorHeadContentSpace(int authorHeadContentSpace) {
        this.authorHeadContentSpace = UIUtils.dip2px(mContext, authorHeadContentSpace);
    }

    public int getAuthorLineSpace() {
        return authorLineSpace;
    }

    public void setAuthorLineSpace(int authorLineSpace) {
        this.authorLineSpace = UIUtils.dip2px(mContext, authorLineSpace);
    }

    public int getAuthorTextSize() {
        return authorTextSize;
    }

    public void setAuthorTextSize(int authorTextSize) {
        this.authorTextSize = UIUtils.sp2px(mContext, authorTextSize);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "{" +
                "textSize=" + textSize +
                ", lineSpace=" + lineSpace +
                ", otherTextSize=" + otherTextSize +
                ", otherTextColor=" + otherTextColor +
                ", bigTitleSize=" + bigTitleSize +
                ", bigTitleLineSpace=" + bigTitleLineSpace +
                ", textColor=" + textColor +
                ", bgColor=" + bgColor +
                ", power=" + power +
                ", contentMarginBottom=" + contentMarginBottom +
                ", authorHeadHeight=" + authorHeadHeight +
                ", authorContentSpace=" + authorContentSpace +
                ", authorHeadContentSpace=" + authorHeadContentSpace +
                ", authorLineSpace=" + authorLineSpace +
                ", authorTextSize=" + authorTextSize +
                '}';
    }
}