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
    private int mWidth = DEF_WIDTH;
    private int mHeight = DEF_Height;
    private Scroller mScroller;
    private boolean mShowMenu;//是否显示pop
    private float mMinMove = 100;
    float mDownX, mDownY, mOffsetX, mOffsetY;
    private float mShowPopMinX;
    private float mShowPopMaxX;
    private float mShowPopMinY;
    private float mShowPopMaxY;
    private Draw mDraw;
    private int mDrawAction = DRAW_ACTION_NONE;
    public static final int DRAW_ACTION_NONE = 0; //不执行
    public static final int DRAW_ACTION_TO_NEXT = 1;//执行下翻页
    public static final int DRAW_ACTION_TO_PREVIOUS = 2;//执行前翻页
    public static final int DRAW_ACTION_RESET = 3;//执行复位
    public static final int DRAW_ACTION_SCROLLING = 4;//执行中
    private boolean mScrolling = false;

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
        mShowPopMaxX = UIUtils.getDisplayWidth(getContext()) * 2 / 3;
        mShowPopMinX = UIUtils.getDisplayWidth(getContext()) / 3;
        mShowPopMaxY = UIUtils.getDisplayHeight(getContext()) * 2 / 3;
        mShowPopMinY = UIUtils.getDisplayHeight(getContext()) / 3;
    }

    public void setDraw(Draw mDraw) {
        this.mDraw = mDraw;

    }

    public void setDrawAction(int drawAction) {
        mDrawAction = drawAction;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = UIUtils.getDisplayWidth(getContext());
        int height = UIUtils.getDisplayHeight(getContext());
        int specW = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        int specH = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        if (mCurrentBM == null) {
            mCurrentBM = Bitmap.createBitmap(width,
                    height, Bitmap.Config.RGB_565);
        }
        if (mPreviousBM == null) {
            mPreviousBM = Bitmap.createBitmap(width,
                    height, Bitmap.Config.RGB_565);
        }
        if (mNextBM == null) {
            mNextBM = Bitmap.createBitmap(width,
                    height, Bitmap.Config.RGB_565);
        }
        super.onMeasure(specW, specH);
        mDraw.drawNext(mCurrentBM, mNextBM);
        mDraw.drawPrevious(mPreviousBM, mCurrentBM);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (mDraw == null) {
            return;
        }
        switch (mDrawAction) {
            case DRAW_ACTION_NONE:
                Log.e("onDraw", "onDraw");
                mDraw.onDraw(canvas, mPreviousBM, mCurrentBM, mNextBM);
                break;
            case DRAW_ACTION_RESET:

                mDrawAction = DRAW_ACTION_SCROLLING;
                break;
            case DRAW_ACTION_TO_NEXT:

                mDrawAction = DRAW_ACTION_SCROLLING;
                break;
            case DRAW_ACTION_TO_PREVIOUS:

                mDrawAction = DRAW_ACTION_SCROLLING;
                break;
        }
    }

    @Override
    public void computeScroll() {
        if (mDrawAction != DRAW_ACTION_NONE) {
            if (!mScroller.computeScrollOffset()) {
                //如果需要滚动，也没有正在计算的滚动，则开始滚动计算
                mScroller.startScroll((int) mOffsetX, (int) mOffsetY,
                        (int) (mWidth - Math.abs(mOffsetX)),
                        (int) (mHeight - Math.abs(mOffsetY)));
            }
        }
        if (mScroller.computeScrollOffset()) {
            //滚动计算未结束，则继续刷新页面
            invalidate();
        } else {
            //结束则调用结束方法
            scrollOver();
            mDrawAction = DRAW_ACTION_NONE;
        }
    }

    /**
     * 滚动结束
     */
    private void scrollOver() {
        //交换BitMap标记
        if (mDrawAction == DRAW_ACTION_TO_NEXT) {
            //看下一页
            Bitmap temp = mCurrentBM;

        } else if (mDrawAction == DRAW_ACTION_TO_PREVIOUS) {
            //看前一页
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                mScroller.startScroll((int) mDownX, (int) mDownY, 0, 0);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mShowMenu) {
                    return true;
                }
                mOffsetX = event.getX() - mDownX;
                mOffsetY = event.getY() - mDownY;
//                mScroller.setFinalX((int) (event.getX()));
//                mScroller.setFinalY((int) (event.getY()));
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
//                    if (mDownX < widthPer3) {
//                        //
//                        return true;
//                    } else if (mDownX > 2 * widthPer3) {
//                        animController.moveToNext(changeX, mMoveY, mMoveX);
//                        return true;
//                    } else {
//                        currentPage.reset();
//                        return performClick();
//                    }
//                }
                if (mDownX > mShowPopMinX && mDownX < mShowPopMaxX
                        && mDownY > mShowPopMinY && mDownY < mShowPopMaxY
                        && event.getY() > mShowPopMinY && event.getY() < mShowPopMaxY
                        && event.getX() > mShowPopMinX && event.getX() < mShowPopMaxX) {
                    return performClick();
                }
                break;
        }
        if (mDraw != null) {
            mDraw.onTouchEvent(event);
        }
        return true;
    }

    public Bitmap getmCurrentBM() {
        return mCurrentBM;
    }

    public Bitmap getmPreviousBM() {
        return mPreviousBM;
    }

    public Bitmap getmNextBM() {
        return mNextBM;
    }
}
