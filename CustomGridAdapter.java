package com.alibaba.android.babylon.biz.home.item;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.widget.BaseAdapter;

/**
 * 自定义X宫格适配器
 * 
 * @author jian.qiao
 * @since 2014-4-18
 */
public abstract class CustomGridAdapter<T>  extends BaseAdapter{

    protected List<T> mList;
    protected CustomGridList mView;
    protected Context mContext;

    // 设置每一个Item的高宽
    private int mBlockWidth = 180;
    private int mBlockHeight = 180;

    //设置上下的间距
    private int mWidthSpace = 5;
    private int mHeightSpace = 5;

    //设置列的个数
    private int mCloumnNum = 3;

    public CustomGridAdapter() {
    }

    public CustomGridAdapter(Activity context) {
        mContext = context;
    }
    public T getItem(int position) {
        return mList.get(position);
    }

    public void registerView(CustomGridList observer) {
        mView = observer;
    }

    public void setList(List<T> itemlist){
        this.mList = itemlist;
    }
    public void displayBlocks() {

        if (null == mView) {
            throw new IllegalArgumentException("Apater has not been atatch to any BlockListView");
        }
        mView.onDataListChange();
    }

    public int getCount() {
        return mList.size();
    }

    public void setSpace(int w, int h) {
        mWidthSpace = w;
        mHeightSpace = h;
    }

    public int getHorizontalSpacing() {
        return mWidthSpace;
    }

    public int getVerticalSpacing() {
        return mHeightSpace;
    }

    public void setBlockSize(int w, int h) {
        mBlockWidth = w;
        mBlockHeight = h;
    }

    public int getBlockWidth() {
        return mBlockWidth;
    }

    public int getBlockHeight() {
        return mBlockHeight;
    }

    public void setColumnNum(int num) {
        mCloumnNum = num;
    }

    public int getCloumnNum() {
        return mCloumnNum;
    }
}
