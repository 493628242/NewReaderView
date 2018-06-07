package com.gray.newreaderview.reader.draw;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.gray.newreaderview.reader.element.BottomElement;
import com.gray.newreaderview.reader.element.Element;
import com.gray.newreaderview.reader.util.PageProperty;

import java.util.ArrayList;

/**
 * @author wjy on 2018/6/7.
 */
public abstract class Draw {
    private Canvas mCanvas;
    private Paint mPaint;
    private PageProperty mPageProperty;
    private Bitmap mCurrentBM;
    private Bitmap mPreviousBM;
    private Bitmap mNextBM;

    public Draw(PageProperty pageProperty) {
        this.mCanvas = new Canvas();
        mPaint = new Paint();
        mPageProperty = pageProperty;
        resetPaint();
    }

    public void drawPage(Bitmap bitmap, ArrayList<Element> elements) {
        mCanvas.setBitmap(bitmap);
        mCanvas.drawColor(mPageProperty.getBgColor());
        if (elements == null) {
            return;
        }
        for (Element element : elements) {
            if (element instanceof BottomElement) {
                BottomElement element1 = (BottomElement) element;
                element1.setPower(mPageProperty.getPower());
            }
            element.draw(mCanvas, mPaint);
            resetPaint();
        }
    }

    private void resetPaint() {
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    public abstract boolean onTouchEvent(MotionEvent event);

    public abstract void drawToNext(Bitmap mPreviousBM, Bitmap mCurrentBM,
                                    Bitmap mNextBM, Canvas canvas);

    public abstract void onDraw(Canvas canvas);

    public void setCurrentBM(Bitmap mCurrentBM) {
        this.mCurrentBM = mCurrentBM;
    }

    public void setPreviousBM(Bitmap mPreviousBM) {
        this.mPreviousBM = mPreviousBM;
    }

    public void setNextBM(Bitmap mNextBM) {
        this.mNextBM = mNextBM;
    }
}
