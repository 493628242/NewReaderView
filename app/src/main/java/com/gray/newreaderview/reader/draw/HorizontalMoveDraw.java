package com.gray.newreaderview.reader.draw;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.gray.newreaderview.reader.adapter.ReaderAdapter;
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
    private final int minX = 100;

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
                if (mOffsetX > 0) {
                    if (mAdapter.canMoveToPrevious()) {
                        mReaderView.invalidate();
                    } else {
                        Toast.makeText(mReaderView.getContext(), "已到第一页", Toast.LENGTH_SHORT).show();
                    }
                } else if (mOffsetX < 0) {
                    if (mAdapter.canMoveToNext()) {
                        mReaderView.invalidate();
                    } else {
                        Toast.makeText(mReaderView.getContext(), "已到最后一页", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(mOffsetX) > minX) {
                    if (mOffsetX > 0) {
                        //看前一页
                        if (mAdapter.canMoveToPrevious()) {
                            mReaderView.setDrawAction(ReaderView.DRAW_ACTION_TO_PREVIOUS);
                        } else {
                            Toast.makeText(mReaderView.getContext(), "已到第一页", Toast.LENGTH_SHORT).show();
                        }
                    } else if (mOffsetX < 0) {
                        //看后一页
                        if (mAdapter.canMoveToNext()) {
                            mReaderView.setDrawAction(ReaderView.DRAW_ACTION_TO_NEXT);
                        } else {
                            Toast.makeText(mReaderView.getContext(), "已到最后一页", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    int halfWidth = UIUtils.getDisplayWidth(mReaderView.getContext()) >> 1;
                    if (mDownX > halfWidth && event.getX() > halfWidth) {
                        //看后一页
                        if (mAdapter.canMoveToNext()) {
                            mReaderView.setDrawAction(ReaderView.DRAW_ACTION_TO_NEXT);
                        } else {
                            Toast.makeText(mReaderView.getContext(), "已到最后一页", Toast.LENGTH_SHORT).show();
                        }
                    } else if (mDownX < halfWidth && event.getX() < halfWidth) {
                        //看前一页
                        if (mAdapter.canMoveToPrevious()) {
                            mReaderView.setDrawAction(ReaderView.DRAW_ACTION_TO_PREVIOUS);
                        } else {
                            Toast.makeText(mReaderView.getContext(), "已到第一页", Toast.LENGTH_SHORT).show();
                        }
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
        canvas.drawBitmap(mCurrentBM, mOffsetX, 0, null);
        canvas.drawBitmap(mPreviousBM, ~mPreviousBM.getWidth() + mOffsetX, 0, null);
        canvas.drawBitmap(mNextBM, mNextBM.getWidth() + mOffsetX, 0, null);
    }

    @Override
    public void moveToNext(Canvas canvas, Bitmap mCurrentBM, Bitmap mNextBM, int currX, int currY) {
        canvas.drawBitmap(mCurrentBM, currX, 0, null);
        canvas.drawBitmap(mNextBM, mNextBM.getWidth() + currX - 1, 0, null);
    }

    @Override
    public void moveToPrevious(Canvas canvas, Bitmap mPreviousBM, Bitmap mCurrentBM, int currX, int currY) {
        canvas.drawBitmap(mCurrentBM, currX, 0, null);
        canvas.drawBitmap(mPreviousBM, ~mPreviousBM.getWidth() + currX + 1, 0, null);
    }
}
