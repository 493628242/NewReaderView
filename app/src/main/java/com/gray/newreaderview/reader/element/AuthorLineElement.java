package com.gray.newreaderview.reader.element;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.gray.newreaderview.reader.util.PageProperty;

/**
 * @author wjy on 2018/4/12.
 */
public class AuthorLineElement extends Element {
    private String content;
    private PageProperty pageProperty;

    public void setPageProperty(PageProperty pageProperty) {
        this.pageProperty = pageProperty;
    }

    public AuthorLineElement(String content) {
        this.content = content;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setTextSize(pageProperty.getAuthorTextSize());
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(pageProperty.getTextColor());
        canvas.drawText(content, x, y, paint);
    }
}
