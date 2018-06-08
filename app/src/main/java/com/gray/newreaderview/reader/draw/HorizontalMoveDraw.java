package com.gray.newreaderview.reader.draw;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.gray.newreaderview.reader.util.PageProperty;
import com.gray.newreaderview.reader.util.UIUtils;
import com.gray.newreaderview.reader.view.ReaderView;

/**
 * @author wjy on 2018/6/7.
 */
public class HorizontalMoveDraw extends Draw {
    private float mDownX;
    private float mDownY;

    private float mOffsetX;
    private float mOffsetY;
    private final int minX = 50;

    public HorizontalMoveDraw(PageProperty pageProperty, ReaderView view) {
        super(pageProperty, view);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(50);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mOffsetX = event.getX() - mDownX;
                mOffsetY = event.getY() - mDownY;
                Log.e("mOffsetX", mOffsetX + "X");
                mReaderView.invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(mOffsetX) > minX) {
                    if (mOffsetX > 0) {
                        //向后翻
                        mReaderView.setDrawAction(ReaderView.DRAW_ACTION_TO_NEXT);
                    } else if (mOffsetX < 0) {
                        //向前翻
                        mReaderView.setDrawAction(ReaderView.DRAW_ACTION_TO_PREVIOUS);
                    }
                } else {
                    if (mDownX > UIUtils.getDisplayWidth(mReaderView.getContext()) >> 1) {
                        //向后翻
                        mReaderView.setDrawAction(ReaderView.DRAW_ACTION_TO_NEXT);
                    } else {
                        //向前翻
                        mReaderView.setDrawAction(ReaderView.DRAW_ACTION_TO_PREVIOUS);
                    }
                }
                mReaderView.postInvalidate();
                mOffsetX = 0;
                mOffsetY = 0;
                break;
        }
        return true;
    }

    @Override
    public void drawNext(Bitmap mCurrentBM, Bitmap mNextBM) {
        mCanvas.setBitmap(mCurrentBM);
        mCanvas.drawColor(0xFFf9efd8);
        mCanvas.drawText("mCurrentBM", mOffsetX, 200, mPaint);
        mCanvas.save(Canvas.ALL_SAVE_FLAG);
        mCanvas.setBitmap(mNextBM);
        mCanvas.drawColor(Color.BLUE);
        mCanvas.drawText("mNextBM", mOffsetX, 200, mPaint);

    }

    @Override
    public void drawPrevious(Bitmap mPreviousBM, Bitmap mCurrentBM) {
        mCanvas.setBitmap(mCurrentBM);
        mCanvas.drawColor(0xFFf9efd8);
        mCanvas.drawText("mCurrentBM", mOffsetX, 200, mPaint);
        mCanvas.setBitmap(mPreviousBM);
        mCanvas.drawColor(Color.GREEN);
        mCanvas.drawText("mPreviousBM", mOffsetX, 200, mPaint);
    }

    @Override
    public void onDraw(Canvas canvas, Bitmap mPreviousBM, Bitmap mCurrentBM, Bitmap mNextBM) {

        Log.e("WH", "onDraw: " + ~mPreviousBM.getWidth() + "\n" + mPreviousBM.getHeight());
        Log.e("onDraw", "onDraw: 移动");
        canvas.drawBitmap(mCurrentBM, mOffsetX, 0, null);
        canvas.drawBitmap(mPreviousBM, ~mPreviousBM.getWidth() + mOffsetX + 1, 0, null);
        canvas.drawBitmap(mNextBM, mNextBM.getWidth() + mOffsetX - 1, 0, null);

    }
}
