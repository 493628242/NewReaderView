package com.gray.newreaderview.reader.adapter;

import android.content.Context;

import com.gray.newreaderview.reader.bean.ChaptersBean;

import java.util.List;

/**
 * @author wjy on 2018/6/11.
 */
public class MyReaderAdapter extends ReaderAdapter {


    public MyReaderAdapter(Context context, List<ChaptersBean> chaptersBeans) {
        super(context, chaptersBeans);
    }

    @Override
    public int getCount() {
        return chaptersBeans.size();
    }

    @Override
    public boolean isVipChapter() {
        ChaptersBean bean = chaptersBeans.get(mCurrChapterIndex);
        return bean.getCurrency() != 0 && !bean.isBuy();
    }

}
