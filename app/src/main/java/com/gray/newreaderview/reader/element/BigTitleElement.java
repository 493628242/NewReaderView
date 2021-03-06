package com.gray.newreaderview.reader.element;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.gray.newreaderview.reader.util.PageProperty;
import com.gray.newreaderview.reader.util.UIUtils;

import java.lang.ref.SoftReference;

/**
 * @author wjy on 2018/4/10.
 */
public class BigTitleElement extends Element {
    public static final int DEF_TEXT_SIZE = 25;//sp
    public static final int DEF_LINE_SPACE = 5;//sp
    public static final int DEF_TEXT_COLOR = Color.BLACK;
    private static int textSize;
    private static int textColor;
    private String title;
    private SoftReference<Context> contextSoftReference;
    private PageProperty pageProperty;

    public BigTitleElement(Context context, String title) {
        contextSoftReference = new SoftReference<>(context);
        this.title = title;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        Context context = contextSoftReference.get();
        paint.setTextSize(pageProperty.getBigTitleSize());
        paint.setColor(pageProperty.getTextColor());
        String[] split = title.split("\n");
        paint.setTextSize(pageProperty.getBigTitleSize());
        paint.setTextAlign(Paint.Align.CENTER);
        int width = UIUtils.getDisplayWidth(context);
        Rect rect = new Rect();
        for (int i = 0; i < split.length; i++) {
            paint.getTextBounds(split[i], 0, split[i].length(), rect);
            int height = i * (rect.height()
                    + pageProperty.getBigTitleLineSpace());
            x = width / 2;
            canvas.drawText(split[i], x, y + height, paint);
        }
    }

    public void setPageProperty(PageProperty pageProperty) {
        this.pageProperty = pageProperty;
    }
}
