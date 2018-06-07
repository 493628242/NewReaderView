package com.gray.newreaderview.reader.element;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.gray.newreaderview.reader.util.PageProperty;

import java.lang.ref.SoftReference;

/**
 * @author wjy on 2018/4/10.
 */
public class LineElement extends Element {
    public static final int DEF_TEXT_SIZE = 18;
    public static final int DEF_LINE_SPACE = DEF_TEXT_SIZE - 6;
    public static final int DEF_TEXT_COLOR = Color.BLACK;
    private SoftReference<Context> contextSoftReference;
    private int textSize = DEF_TEXT_SIZE;
    private int textColor = DEF_TEXT_COLOR;
    private String content;
    private PageProperty pageProperty;

    public void setPageProperty(PageProperty pageProperty) {
        this.pageProperty = pageProperty;
    }

    public LineElement(Context context, String content) {
        contextSoftReference = new SoftReference<>(context);
        this.content = content;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setTextSize(pageProperty.getTextSize());
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(pageProperty.getTextColor());
        canvas.drawText(content, x, y, paint);
    }


}
