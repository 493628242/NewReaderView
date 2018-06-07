package com.gray.newreaderview.reader.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

import com.gray.newreaderview.reader.draw.Draw;
import com.gray.newreaderview.reader.util.UIUtils;

/**
 * @author wjy on 2018/6/7.
 */
public class ReaderView extends View {
    private Bitmap mCurrentBM;
    private Bitmap mPreviousBM;
    private Bitmap mNextBM;
    private final int DEF_WIDTH = 200;
    private final int DEF_Height = 200;
    private int width = DEF_WIDTH;
    private int height = DEF_Height;
    private int mPaddingBottom;
    private int mPaddingStart;
    private int mPaddingEnd;
    private int mPaddingTop;
    private Scroller mScroller;
    private boolean mShowMenu;//是否显示pop
    private float mMinMove = 100;
    float downX, downY;
    private float mShowPopMinX;
    private float mShowPopMaxX;
    private float mShowPopMinY;
    private float mShowPopMaxY;
    private Draw mDraw;

    public ReaderView(Context context) {
        this(context, null);

    }

    public ReaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mScroller = new Scroller(getContext(), new LinearInterpolator());
        mPaddingTop = getPaddingTop();
        mPaddingStart = getPaddingStart();
        mPaddingEnd = getPaddingEnd();
        mPaddingBottom = getPaddingBottom();
        mShowPopMaxX = UIUtils.getDisplayWidth(getContext()) * 2 / 3;
        mShowPopMinX = UIUtils.getDisplayWidth(getContext()) / 3;
        mShowPopMaxY = UIUtils.getDisplayHeight(getContext()) * 2 / 3;
        mShowPopMinY = UIUtils.getDisplayHeight(getContext()) / 3;
        Log.e("initView", "minX " + mShowPopMinX);
        Log.e("initView", "maxX " + mShowPopMaxX);
        Log.e("initView", "minY " + mShowPopMinY);
        Log.e("initView", "maxY " + mShowPopMaxY);
    }

    public void setDraw(Draw mDraw) {
        this.mDraw = mDraw;
        mDraw.setCurrentBM(mCurrentBM);
        mDraw.setNextBM(mNextBM);
        mDraw.setPreviousBM(mPreviousBM);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = widthMeasureSpec;
        int height = heightMeasureSpec;
        if (MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.EXACTLY) {
            MeasureSpec.makeMeasureSpec(DEF_WIDTH, MeasureSpec.EXACTLY);
        } else {
            width = MeasureSpec.getSize(widthMeasureSpec);
        }
        if (MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY) {
            MeasureSpec.makeMeasureSpec(DEF_Height, MeasureSpec.EXACTLY);
        } else {
            height = MeasureSpec.getSize(heightMeasureSpec);
        }
        super.onMeasure(width, height);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mCurrentBM == null) {
            mCurrentBM = Bitmap.createBitmap(width - getPaddingStart() - getPaddingEnd(),
                    height - getPaddingTop() - getPaddingBottom(), Bitmap.Config.ARGB_8888);
        }
        if (mPreviousBM == null) {
            mPreviousBM = Bitmap.createBitmap(width - getPaddingStart() - getPaddingEnd(),
                    height - getPaddingTop() - getPaddingBottom(), Bitmap.Config.ARGB_8888);
        }
        if (mNextBM == null) {
            mNextBM = Bitmap.createBitmap(width - getPaddingStart() - getPaddingEnd(),
                    height - getPaddingTop() - getPaddingBottom(), Bitmap.Config.ARGB_8888);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDraw != null) {
            mDraw.onDraw(canvas);
        }
    }

    @Override
    public void computeScroll() {
        Log.e("computeScroll", "computeScroll");
        if (mScroller.computeScrollOffset()) {
            float x = mScroller.getCurrX();
            float y = mScroller.getCurrY();
//            if (style.equals(STYLE_TOP_RIGHT)) {
//                setTouchPoint(x, y, STYLE_TOP_RIGHT);
//            } else {
//                setTouchPoint(x, y, STYLE_LOWER_RIGHT);
//            }
            if (mScroller.getFinalX() == x && mScroller.getFinalY() == y) {
//                setDefaultPath();
            }
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (mShowMenu) {
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
//                if (mo != 0 && Math.abs(moveX) > minMove) {
//
//                    if (changeX < 0) {
//                        animController.moveToNext(changeX, mMoveY, mMoveX);
//                    } else {
//                        animController.moveToPrevious(changeX, mMoveY, mMoveX);
//                    }
//                } else {
//                    //当pop显示时无论点击何处都是关闭pop
//                    if (mShowMenu) {
//                        return performClick();
//                    }
//                    int widthPer3 = UIUtils.getDisplayWidth(getContext()) / 3;
//                    if (downX < widthPer3) {
//                        //
//                        return true;
//                    } else if (downX > 2 * widthPer3) {
//                        animController.moveToNext(changeX, mMoveY, mMoveX);
//                        return true;
//                    } else {
//                        currentPage.reset();
//                        return performClick();
//                    }
//                }
                if (downX > mShowPopMinX && downX < mShowPopMaxX
                        && downY > mShowPopMinY && downY < mShowPopMaxY
                        && event.getY() > mShowPopMinY && event.getY() < mShowPopMaxY
                        && event.getX() > mShowPopMinX && event.getX() < mShowPopMaxX) {
                    return performClick();
                }
                break;
        }
        if (mDraw != null) {
            mDraw.onTouchEvent(event);
            postInvalidate();
        }
        return true;
    }
}
