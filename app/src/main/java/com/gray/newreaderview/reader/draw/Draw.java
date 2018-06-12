package com.gray.newreaderview.reader.draw;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.gray.newreaderview.reader.adapter.ReaderAdapter;
import com.gray.newreaderview.reader.element.BottomElement;
import com.gray.newreaderview.reader.element.Element;
import com.gray.newreaderview.reader.util.PageProperty;
import com.gray.newreaderview.reader.view.ReaderView;

import java.util.List;

/**
 * @author wjy on 2018/6/7.
 */
public abstract class Draw {
    protected Canvas mCanvas;
    protected Paint mPaint;
    protected PageProperty mPageProperty;
    protected Bitmap mCurrentBM;
    protected Bitmap mPreviousBM;
    protected Bitmap mNextBM;
    protected ReaderView mReaderView;
    protected RectF mRectF;
    protected Rect mRect;
    protected ReaderAdapter mAdapter;

    public Draw(PageProperty pageProperty, ReaderView view) {
        this.mCanvas = new Canvas();
        mPaint = new Paint();
        mPageProperty = pageProperty;
        mReaderView = view;
        mAdapter = view.getReaderAdapter();
        mRectF = new RectF();
        mRect = new Rect();
        resetPaint();
    }

    public void drawPage(Bitmap bitmap, List<Element> elements) {
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

    public abstract void drawNext(Bitmap mCurrentBM,
                                  Bitmap mNextBM);

    public abstract void drawPrevious(Bitmap mPreviousBM, Bitmap mCurrentBM);

    public abstract void onDraw(Canvas canvas, Bitmap mPreviousBM,
                                Bitmap mCurrentBM, Bitmap mNextBM);

    public abstract void moveToNext(Canvas canvas, Bitmap mCurrentBM,
                                    Bitmap mNextBM, int currX, int currY);

    public abstract void moveToPrevious(Canvas canvas, Bitmap mPreviousBM,
                                        Bitmap mCurrentBM, int currX, int currY);

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
