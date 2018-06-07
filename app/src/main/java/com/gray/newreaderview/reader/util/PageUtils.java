package com.gray.newreaderview.reader.util;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.gray.newreaderview.reader.bean.ChaptersBean;
import com.gray.newreaderview.reader.element.AuthorHeadElement;
import com.gray.newreaderview.reader.element.AuthorLineElement;
import com.gray.newreaderview.reader.element.BigTitleElement;
import com.gray.newreaderview.reader.element.BottomElement;
import com.gray.newreaderview.reader.element.Element;
import com.gray.newreaderview.reader.element.LineElement;
import com.gray.newreaderview.reader.element.SmallTitleElement;

import java.lang.ref.SoftReference;
import java.util.ArrayList;

/**
 * @author wjy on 2018/6/7.
 */
public class PageUtils extends AsyncTask<Void, Void, ArrayList<ArrayList<Element>>> {
    private String word = "";
    private String title = "";
    private String author = "";
    private ArrayList<ArrayList<Element>> elementsList;
    private boolean calOver;
    private String unCal = "";
    private String unCalAuthor = "";
    private boolean authorOver = false;
    private ArrayList<Integer> pageNum;
    private int pageWordNum = 0;
    private int index = -1;
    private Paint mPaint;
    private SoftReference<Context> mSoftContext;
    private PageProperty mPageProperty;
    private onPagingFinishListener listener;

    public PageUtils(Context context, PageProperty pageProperty) {
        elementsList = new ArrayList<>();
        pageNum = new ArrayList<>();
        mSoftContext = new SoftReference<>(context);
        mPageProperty = pageProperty;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    public void setData(ChaptersBean bean) {
        title = bean.getName();
        word = BookStringUtils.formateContent(bean.getContent());
        author = BookStringUtils.formateContent(bean.getAuthorContent());
        unCal = word;
        unCalAuthor = author;
        index = bean.getIndex();
        this.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }


    //进行分页操作
    public ArrayList<ArrayList<Element>> paging() {
        elementsList.clear();
        Rect rect = new Rect();
        boolean hasDrawAuthorHead = false;
        while (!calOver
                || !authorOver) {
            pageWordNum = 0;
            ArrayList<Element> elements = new ArrayList<>();
            //已经使用的高度
            int hasUsedHeight = UIUtils.dip2px(mSoftContext.get(), Element.PADDING_TOP);
            //小标题
            SmallTitleElement smallTitleElement = getSmallTitleElement();
            smallTitleElement.setPageProperty(mPageProperty);
            elements.add(smallTitleElement);
            String smallTitleUsefulString = smallTitleElement.getTitle();
            mPaint.getTextBounds(smallTitleElement.getTitle(), 0, smallTitleUsefulString.length(),
                    rect);
            smallTitleElement.setX(UIUtils.dip2px(mSoftContext.get(), Element.PADDING_START));
            smallTitleElement.setY(hasUsedHeight + rect.height());
            hasUsedHeight += rect.height() + UIUtils.dip2px(mSoftContext.get(), 18);
            //大标题
            if (elementsList.isEmpty()) {
                mPaint.setTextSize(mPageProperty.getBigTitleSize());
                BigTitleElement bigTitleElement = new BigTitleElement(mSoftContext.get(),
                        getBigTitleUsefulString(title));
                bigTitleElement.setPageProperty(mPageProperty);
                elements.add(bigTitleElement);
                String bigTitleUsefulString = getBigTitleUsefulString(title);
                String[] split = bigTitleUsefulString.split("\n");
                mPaint.getTextBounds(title, 0, title.length(), rect);
                bigTitleElement.setX(UIUtils.dip2px(mSoftContext.get(), Element.PADDING_START));
                bigTitleElement.setY(UIUtils.dip2px(mSoftContext.get(), 26)
                        + hasUsedHeight + rect.height());
                //已占用高度
                hasUsedHeight += UIUtils.dip2px(mSoftContext.get(), 26) + rect.height() * split.length
                        + mPageProperty.getBigTitleLineSpace()
                        * (split.length - 1) + UIUtils.dip2px(mSoftContext.get(), 40);
            }
            hasUsedHeight = writeContent(rect, elements, hasUsedHeight);

            //如果有作者的话应当添加
            if (unCalAuthor == null || unCalAuthor.isEmpty()) {
                authorOver = true;
            } else {
                int authorCanUseHeight = UIUtils.getDisplayHeight(mSoftContext.get())
                        - hasUsedHeight - mPageProperty.getContentMarginBottom();
                //判断是否已经在前一页已有标题，或者剩下的高度不够画出标题时跳过
                if (!hasDrawAuthorHead && authorCanUseHeight > mPageProperty.getAuthorHeadHeight()) {
                    AuthorHeadElement authorHeadElement =
                            new AuthorHeadElement(mSoftContext.get());
                    authorHeadElement.setPageProperty(mPageProperty);
                    authorHeadElement.setY(hasUsedHeight + mPageProperty.getAuthorContentSpace());
                    elements.add(authorHeadElement);
                    hasDrawAuthorHead = true;
                    hasUsedHeight += mPageProperty.getAuthorHeadHeight();
                }
                //加载作者的话的内容 逻辑应和加载正文逻辑一致
                if (hasDrawAuthorHead) {
                    writeAuthor(rect, elements, hasUsedHeight);
                }
            }
            pageNum.add(pageWordNum);
        }
        //分页完成后统一添加底部
        int count = elementsList.size();

        for (ArrayList<Element> elements : elementsList) {
            BottomElement bottomElement = new BottomElement(mSoftContext.get());
            bottomElement.setPageProperty(mPageProperty);
            bottomElement.setIndex((elementsList.indexOf(elements) + 1) + "/" + count);
            bottomElement.setPower(mPageProperty.getPower());
            elements.add(bottomElement);
        }
        return elementsList;
    }

    private int writeContent(Rect rect, ArrayList<Element> elements, int hasUsedHeight) {
        //正文内容
        if (unCal != null && !unCal.isEmpty()) {
            //在正文逻辑判断是会先加上行间距在计算文字位置，所以第一次先减去一个行间距
            hasUsedHeight -= mPageProperty.getLineSpace();
            //正文已占据空间
            int contentHeight = 0;
            //正文可用空间
            int surplusHeight = UIUtils.getDisplayHeight(mSoftContext.get()) - hasUsedHeight
                    - mPageProperty.getContentMarginBottom();
            mPaint.setTextSize(mPageProperty.getTextSize());
            String[] split = unCal.split("\n");//分段

            ArrayList<LineElement> lineElements = new ArrayList<>();//临时空间用于储存行
            int wordNum = 0;
            for (String aSplit : split) {
                String paragraph = aSplit;
                boolean pageOver = false;
                while (true) {
                    hasUsedHeight += mPageProperty.getLineSpace();
                    contentHeight += mPageProperty.getLineSpace();
                    String lineUsefulString = getLineUsefulString(paragraph);
                    LineElement lineElement = new LineElement(mSoftContext.get(), lineUsefulString);
                    mPaint.getTextBounds(lineUsefulString, 0,
                            lineUsefulString.length(), rect);
                    hasUsedHeight += rect.height();
                    lineElement.setPageProperty(mPageProperty);
                    lineElement.setX(UIUtils.dip2px(mSoftContext.get(), Element.PADDING_START));
                    lineElement.setY(hasUsedHeight);
                    contentHeight += rect.height();
                    if (contentHeight > surplusHeight) {
                        pageOver = true;
                        break;
                    } else {
                        lineElements.add(lineElement);
                        wordNum += lineUsefulString.length();
                    }
                    paragraph = paragraph.substring(lineUsefulString.length(), paragraph.length());
                    if (paragraph.isEmpty()) {
                        break;
                    }
                }
                //在计算完一页后取数以计算的字数
                if (pageOver) {
                    break;
                }
                wordNum++;//加上换行符
            }

            pageWordNum += wordNum;
            unCal = unCal.substring(wordNum, unCal.length());
            elements.addAll(lineElements);
        }
        elementsList.add(elements);
        if (unCal == null || unCal.isEmpty()) {
            calOver = true;
        }
        return hasUsedHeight;
    }

    private int writeAuthor(Rect rect, ArrayList<Element> elements, int hasUsedHeight) {
        //同正文
        if (hasUsedHeight < mPageProperty.getAuthorHeadHeight()) {
            hasUsedHeight = hasUsedHeight - mPageProperty.getLineSpace();
        } else {
            hasUsedHeight = hasUsedHeight + mPageProperty.getAuthorHeadContentSpace()
                    - mPageProperty.getAuthorLineSpace();
        }
        //作者已占据空间
        int contentHeight = 0;
        //作者可用空间
        int surplusHeight = UIUtils.getDisplayHeight(mSoftContext.get()) - hasUsedHeight
                - mPageProperty.getContentMarginBottom();
        mPaint.setTextSize(mPageProperty.getAuthorTextSize());
        String[] split = unCalAuthor.split("\n");//分段
        ArrayList<AuthorLineElement> authorLineElements = new ArrayList<>();//临时空间用于储存行
        int wordNum = 0;
        for (String aSplit : split) {
            String paragraph = aSplit;
            boolean pageOver = false;
            while (true) {
                hasUsedHeight += mPageProperty.getAuthorLineSpace();
                contentHeight += mPageProperty.getAuthorLineSpace();
                String lineUsefulString = getLineUsefulString(paragraph);
                AuthorLineElement authorLineElement =
                        new AuthorLineElement(lineUsefulString);
                mPaint.getTextBounds(lineUsefulString, 0,
                        lineUsefulString.length(), rect);
                hasUsedHeight += rect.height();
                authorLineElement.setPageProperty(mPageProperty);
                authorLineElement.setX(UIUtils.dip2px(mSoftContext.get(), Element.PADDING_START));
                authorLineElement.setY(hasUsedHeight);
                contentHeight += rect.height();
                if (contentHeight > surplusHeight) {
                    pageOver = true;
                    break;
                } else {
                    authorLineElements.add(authorLineElement);
                    wordNum += lineUsefulString.length();
                }
                paragraph = paragraph.substring(lineUsefulString.length(), paragraph.length());
                if (paragraph.isEmpty()) {
                    break;
                }
            }
            //在计算完一页后取数已计算的字数
            if (pageOver) {
                break;
            }
            wordNum++;//加上换行符
        }
        pageWordNum += wordNum;
        unCalAuthor = unCalAuthor.substring(wordNum, unCalAuthor.length());
        elements.addAll(authorLineElements);
        if (unCalAuthor == null || unCalAuthor.isEmpty()) {
            authorOver = true;
        }
        return hasUsedHeight;
    }

    //获取小标题的Element
    @NonNull
    private SmallTitleElement getSmallTitleElement() {
        mPaint.setTextSize(mPageProperty.getOtherTextSize());
        return new SmallTitleElement(mSoftContext.get(), getSmallTitleUsefulString(title));
    }

    public void setListener(onPagingFinishListener listener) {
        this.listener = listener;
    }

    private String getSmallTitleUsefulString(String string) {
        int usefulWidth = UIUtils.getDisplayWidth(mSoftContext.get())
                - UIUtils.dip2px(mSoftContext.get(), Element.PADDING_START)
                - UIUtils.dip2px(mSoftContext.get(), Element.PADDING_END);
        String usefulString;
        int length = string.length();
        if (mPaint.measureText(string) <= usefulWidth) {
            return string;
        }
        while ((mPaint.measureText(string.substring(0, length)
                + mPaint.measureText("...")) > usefulWidth)) {
            --length;
        }
        usefulString = string.substring(0, length) + "...";
        return usefulString;
    }

    private String getBigTitleUsefulString(String string) {
        //获取屏幕可用宽度
        int usefulWidth = UIUtils.getDisplayWidth(mSoftContext.get())
                - UIUtils.dip2px(mSoftContext.get(), Element.PADDING_START)
                - UIUtils.dip2px(mSoftContext.get(), Element.PADDING_END);
        StringBuilder usefulString = new StringBuilder();
        int length;
        if (mPaint.measureText(string) <= usefulWidth) {
            return string;
        }
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i != string.length(); i = length) {
            length = string.length();
            while ((mPaint.measureText(string.substring(i, length)) > usefulWidth)) {
                --length;
            }
            strings.add(string.substring(i, length));
        }
        for (String s : strings) {
            usefulString.append(s).append("\n");
        }
        return usefulString.toString();
    }

    private String getLineUsefulString(String string) {
        int usefulWidth = UIUtils.getDisplayWidth(mSoftContext.get())
                - UIUtils.dip2px(mSoftContext.get(), Element.PADDING_START)
                - UIUtils.dip2px(mSoftContext.get(), Element.PADDING_END);
        String usefulString;
        int length = string.length();
        while ((mPaint.measureText(string.substring(0, length)) > usefulWidth)) {
            --length;
        }
        usefulString = string.substring(0, length);
        return usefulString;
    }

    @Override
    protected ArrayList<ArrayList<Element>> doInBackground(Void... voids) {
        return paging();
    }

    @Override
    protected void onPostExecute(ArrayList<ArrayList<Element>> lists) {
//        Log.e("onPostExecute", "1");
//        chapterMap.put(index, lists);
//        Log.e("onPostExecute", "2");
//        wordMap.put(index, pageNum);
//        Log.e("onPostExecute", "3");
//        loadData();
        if (listener != null) {
            listener.onFinish(lists);
        }
    }

    public interface onPagingFinishListener {
        void onFinish(ArrayList<ArrayList<Element>> lists);
    }
}

