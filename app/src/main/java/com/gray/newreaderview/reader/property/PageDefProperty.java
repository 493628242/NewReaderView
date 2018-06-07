package com.gray.newreaderview.reader.property;

import android.graphics.Color;

import com.gray.newreaderview.reader.element.LineElement;

/**
 * @author wjy on 2018/6/7.
 */
public enum PageDefProperty {
    BLACK(0xFF3A3637, 0xFF94928E, 0xFF676465),
    PINK(0xFFFDDFE7, 0xFF5D1A2E, 0xFFAD7C8A),
    GREEN(0xFFD3E4D3, 0xFF455146, 0xFF919F91),
    PROTECT_EYE(0xFFFBF0D9, 0xFF5F4B32, 0xFFBDAE96),
    NORMAL(Color.WHITE, LineElement.DEF_TEXT_COLOR, 0xFF999999);

    int bgColor;
    int textColor;
    int otherTextColor;

    PageDefProperty(int bgColor, int textColor, int otherTextColor) {
        this.bgColor = bgColor;
        this.textColor = textColor;
        this.otherTextColor = otherTextColor;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getOtherTextColor() {
        return otherTextColor;
    }

    public void setOtherTextColor(int otherTextColor) {
        this.otherTextColor = otherTextColor;
    }
}
