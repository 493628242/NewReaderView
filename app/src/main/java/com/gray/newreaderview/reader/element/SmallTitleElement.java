package com.gray.newreaderview.reader.element;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.gray.newreaderview.reader.util.PageProperty;

/**
 * @author wjy on 2018/4/10.
 */
public class SmallTitleElement extends Element {
    public static final int DEF_TEXT_SIZE = 11;
    public static final int DEF_TEXT_COLOR = 0xFF999999;
    private static int textSize = DEF_TEXT_SIZE;
    private static int textColor = DEF_TEXT_COLOR;

    private String title;
    private PageProperty pageProperty;

    public SmallTitleElement(Context context, String title) {
        this.title = title;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setTextSize(pageProperty.getOtherTextSize());
        paint.setColor(pageProperty.getTextColor());
        canvas.drawText(title, x, y, paint);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPageProperty(PageProperty pageProperty) {
        this.pageProperty = pageProperty;
    }
}
