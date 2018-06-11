package com.gray.newreaderview.reader.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.gray.newreaderview.reader.bean.ChaptersBean;
import com.gray.newreaderview.reader.element.Element;
import com.gray.newreaderview.reader.util.LruCacheMap;
import com.gray.newreaderview.reader.util.PageProperty;
import com.gray.newreaderview.reader.util.PageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wjy on 2018/6/11.
 */
public abstract class ReaderAdapter {

    protected PageUtils pageUtils;

    protected LruCacheMap<Integer, List<ArrayList<Element>>> cacheChapterMap;

    protected int mChapterIndex; //章节位置

    protected int mPageIndex; //页面位置
    protected List<ChaptersBean> chaptersBeans; //所有章节
    private int mTempChapterIndex = -1;
    private int mTempPageIndex = -1;
    public static final int MOVE_TO_NEXT = 0;
    public static final int MOVE_TO_PREVIOUS = 1;

    public ReaderAdapter(Context context, List<ChaptersBean> chaptersBeans) {
        this.pageUtils = new PageUtils(context, PageProperty.getInstance(context));
        cacheChapterMap = new LruCacheMap<>();
        this.chaptersBeans = chaptersBeans;
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
     * mChapterIndex 当前页面数
     */
    public List<Element> getCurrPage() {
        return getPage(mChapterIndex, mPageIndex, false);
    }

    /**
     * 获取下一页面的数据
     * <p>
     * mChapterIndex 当前页面数
     */

    public List<Element> getNextPage() {
        if (mChapterIndex + 1 < chaptersBeans.size()) {
            mTempChapterIndex = -1;
            mTempPageIndex = -1;
            List<Element> page = getPage(mChapterIndex + 1, mPageIndex, false);
            if (mTempChapterIndex != -1) {
                mChapterIndex = mTempChapterIndex;
            }
            if (mTempPageIndex != -1) {
                mPageIndex = mTempPageIndex;
            }
            return page;
        } else {
            return null;
        }
    }

    /**
     * 获取前一页面的数据
     * <p>
     * mChapterIndex 当前的页面数 会使mChapterIndex 无效
     */
    public List<Element> getPreviousPage() {
        if (mPageIndex - 1 < 0) {
            if (mChapterIndex - 1 < 0) {
                mChapterIndex = 0;
                mPageIndex = 0;
                return null;
            } else {
                List<Element> page = getPage(mChapterIndex - 1, 0, true);
                if (mTempChapterIndex != -1) {
                    mChapterIndex = mTempChapterIndex;
                }
                if (mTempPageIndex != -1) {
                    mPageIndex = mTempPageIndex;
                }
                return page;
            }
        } else {
            List<Element> page = getPage(mChapterIndex, mPageIndex - 1, false);
            if (mTempChapterIndex != -1) {
                mChapterIndex = mTempChapterIndex;
            }
            if (mTempPageIndex != -1) {
                mPageIndex = mTempPageIndex;
            }
            return page;
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
                mTempChapterIndex = needChapterIndex;
                mTempPageIndex = needPageIndex;
                return lists.get(needPageIndex);
            } else {
                //当前章节已无页面
                if (needPageIndex > 0) {
                    mTempChapterIndex = needChapterIndex + 1;
                    mTempPageIndex = 0;
                    return getPage(needChapterIndex + 1,
                            0, false);
                } else {
                    mTempChapterIndex = needChapterIndex - 1;
                    mTempPageIndex = 0;
                    return getPage(needChapterIndex - 1,
                            0, true);
                }
            }
        } else {
            //无所需章节
            if (needChapterIndex < getCount() && needChapterIndex >= 0) {
                //还存在有未加载、分页的页面
                List<ArrayList<Element>> elementsList
                        = pageUtils.setData(chaptersBeans.get(needChapterIndex));
                cacheChapterMap.put(needChapterIndex, elementsList);
                mTempChapterIndex = needChapterIndex;
                mTempPageIndex = elementsList.isEmpty() ? -1 : fromEnd ?
                        elementsList.size() - 1 : needPageIndex;
                return elementsList.isEmpty() ? null : elementsList.get(fromEnd ?
                        elementsList.size() - 1 : needPageIndex);
            } else {
                //目录的所有章节都已加载完
                mTempChapterIndex = -1;
                mTempPageIndex = -1;
                return null;
            }
        }

    }

    /**
     * 是否是vip章节
     * <p>
     * mPageIndex 当前章节序号
     */
    public abstract boolean isVipChapter();

    public void setStatus(int status) {
        switch (status) {
            case MOVE_TO_NEXT:

                break;
            case MOVE_TO_PREVIOUS:
                break;
        }
    }
}
