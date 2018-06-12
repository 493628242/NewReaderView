package com.gray.newreaderview.reader.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.gray.newreaderview.reader.bean.ChaptersBean;
import com.gray.newreaderview.reader.element.Element;
import com.gray.newreaderview.reader.util.LruCacheMap;
import com.gray.newreaderview.reader.util.PageProperty;
import com.gray.newreaderview.reader.util.PageUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author wjy on 2018/6/11.
 */
public abstract class ReaderAdapter {

    protected PageUtils pageUtils;
    protected LinkedHashMap<Integer, List<Integer>> wordMap;
    protected LinkedHashMap<Integer, List<ArrayList<Element>>> cacheChapterMap;

    protected int mCurrChapterIndex; //当前章节位置
    protected int mCurrPageIndex; //当前页面位置

    protected int mPreviousChapterIndex; //前一章节位置
    protected int mPreviousPageIndex; //前一页面位置

    protected int mNextChapterIndex; //后一章节位置
    protected int mNextPageIndex; //后一页面位置

    protected List<ChaptersBean> chaptersBeans; //所有章节
    public static final int MOVE_TO_NEXT = 0;
    public static final int MOVE_TO_PREVIOUS = 1;

    public ReaderAdapter(Context context, List<ChaptersBean> chaptersBeans) {
        this.pageUtils = new PageUtils(context, PageProperty.getInstance(context));
        cacheChapterMap = new LruCacheMap<>();
        this.chaptersBeans = chaptersBeans;
        wordMap = new LinkedHashMap<>();
    }

    /**
     * 获取章节的数量
     *
     * @return
     */
    public abstract int getCount();

    /**
     * 获取当前页面的数据
     * <p>
     * mCurrChapterIndex 当前页面数
     */
    public List<Element> getCurrPage() {
        return getPage(mCurrChapterIndex, mCurrPageIndex, false);
    }

    /**
     * 获取下一页面的数据
     * <p>
     * mCurrChapterIndex 当前页面数
     */

    public List<Element> getNextPage() {
        if (mCurrPageIndex + 1 < chaptersBeans.size()) {
            return getPage(mCurrChapterIndex, mCurrPageIndex + 1, false);
        } else {

            return null;
        }
    }

    /**
     * 获取前一页面的数据
     * <p>
     * mCurrChapterIndex 当前的页面数 会使mChapterIndex 无效
     */
    public List<Element> getPreviousPage() {
        if (mCurrPageIndex - 1 < 0) {
            if (mCurrChapterIndex - 1 < 0) {
                return null;
            } else {
                return getPage(mCurrChapterIndex - 1, 0, true);
            }
        } else {
            return getPage(mCurrChapterIndex, mCurrPageIndex - 1, false);
        }
    }

    /**
     * 获取分页
     *
     * @param needChapterIndex 章节index 必须>=0
     * @param needPageIndex
     */
    @Nullable
    private List<Element> getPage(int needChapterIndex, int needPageIndex, boolean fromEnd) {
        List<ArrayList<Element>> lists = cacheChapterMap.get(needChapterIndex);
        if (lists != null) {
            //缓存的Map中有所需章节
            if (needPageIndex < lists.size() && needPageIndex >= 0) {
                //当前章节还有页面
                return lists.get(fromEnd ? lists.size() - 1 : needPageIndex);
            } else {
                //当前章节已无页面
                if (needPageIndex > 0) {
                    return getPage(needChapterIndex + 1,
                            0, fromEnd);
                } else {
                    return getPage(needChapterIndex - 1,
                            0, fromEnd);
                }
            }
        } else {
            //无所需章节
            if (needChapterIndex < getCount() && needChapterIndex >= 0) {
                //还存在有未加载、分页的页面
                List<ArrayList<Element>> elementsList
                        = pageUtils.setData(chaptersBeans.get(needChapterIndex));
                ArrayList<Integer> pageNum = pageUtils.getPageNum();
                if (pageNum != null && !pageNum.isEmpty()) {
                    wordMap.put(needChapterIndex, pageNum);
                }
                cacheChapterMap.put(needChapterIndex, elementsList);
                return elementsList.isEmpty() ? null : elementsList.get(fromEnd ?
                        elementsList.size() - 1 : needPageIndex);
            } else {
                //目录的所有章节都已加载完
                return null;
            }
        }
    }

    /**
     * 是否是vip章节
     * <p>
     * mCurrPageIndex 当前章节序号
     */
    public abstract boolean isVipChapter();

    public void setStatus(int status) {
        //更新当前页面的index
        switch (status) {
            case MOVE_TO_NEXT:

                break;
            case MOVE_TO_PREVIOUS:
                break;
        }
        Log.e("setStatus", mCurrChapterIndex + "------" + mCurrPageIndex);
        Log.e("setStatus1", mPreviousChapterIndex + "------" + mPreviousPageIndex);
        Log.e("setStatus2", mNextChapterIndex + "------" + mNextPageIndex);
    }

    public boolean canMoveToPrevious() {
        return mCurrPageIndex > 0 || mCurrChapterIndex > 0;
    }


    public boolean canMoveToNext() {
        List<ArrayList<Element>> arrayLists = cacheChapterMap.get(mCurrChapterIndex);
        if (arrayLists != null) {
            if (arrayLists.size() - 1 > mCurrPageIndex) {//当前章节还有页面
                return true;
            } else {//当前章节已无页面
                if (mCurrChapterIndex < chaptersBeans.size() - 1) {//还有未分页章节
                    return true;
                } else {//所有章节都以分页完成
                    return false;
                }
            }
        } else {
            return false;
        }
    }
}
