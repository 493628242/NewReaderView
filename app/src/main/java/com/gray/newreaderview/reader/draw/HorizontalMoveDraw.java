package com.gray.newreaderview.reader.draw;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import com.gray.newreaderview.reader.util.PageProperty;

/**
 * @author wjy on 2018/6/7.
 */
public class HorizontalMoveDraw extends Draw {
    private Bitmap mCurrentBM;
    private Bitmap mPreviousBM;
    private Bitmap mNextBM;

    public HorizontalMoveDraw(PageProperty pageProperty) {
        super(pageProperty);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("HorizontalMoveDraw", event.getX() + "X\n" + event.getY() + "Y");
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    @Override
    public void drawToNext(Bitmap mPreviousBM, Bitmap mCurrentBM, Bitmap mNextBM, Canvas canvas) {

    }

    @Override
    public void onDraw(Canvas canvas) {
    }


}
