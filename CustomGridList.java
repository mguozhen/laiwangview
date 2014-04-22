package com.alibaba.android.babylon.biz.home.item;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
/**
 * 自定义X宫格实现，在无翻页需求下，GridView的替代容器
 * 使用方法同GridView，触发创建和刷新通过 onDataListChage()
 * 
 * @author jian.qiao
 * @since 2014-4-18
 */
public class CustomGridList extends RelativeLayout {

    private static final int INDEX_TAG = 0x04 << 24;

    private CustomGridAdapter<?> mGridListAdapter;

    private LayoutInflater mLayoutInflater;

    private OnItemClickListener mOnItemClickListener;

    public CustomGridList(Context context) {
        this(context, null, 0);
    }

    public CustomGridList(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomGridList(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setmLayoutInflater(LayoutInflater.from(context));
    }

    public void setAdapter(CustomGridAdapter<?> adapter) {
        if (adapter == null) {
            throw new IllegalArgumentException("adapter should not be null");
        }
        mGridListAdapter = adapter;
        adapter.registerView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (null != mGridListAdapter) {
            mGridListAdapter.registerView(null);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (null != mGridListAdapter) {
            mGridListAdapter.registerView(this);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    OnClickListener mOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            int index = (Integer) v.getTag(INDEX_TAG);
            if (null != mOnItemClickListener) {
                mOnItemClickListener.onItemClick(null, v, index, index);
            }
        }
    };

    public void onDataListChange() {

        removeAllViews();

        int len = mGridListAdapter.getCount();
        int w = mGridListAdapter.getBlockWidth();
        int h = mGridListAdapter.getBlockHeight();
        int cloumnNum = mGridListAdapter.getCloumnNum();

        int horizontalSpacing = mGridListAdapter.getHorizontalSpacing();
        int verticalSpacing = mGridListAdapter.getVerticalSpacing();

        boolean blockDescendant = getDescendantFocusability() == ViewGroup.FOCUS_BLOCK_DESCENDANTS;

        for (int i = 0; i < len; i++) {

            RelativeLayout.LayoutParams lyp = new RelativeLayout.LayoutParams(w, h);
            int row = i / cloumnNum;
            int clo = i % cloumnNum;
            int left = 0;
            int top = 0;

            if (clo > 0) {
                left = (horizontalSpacing + w) * clo;
            }
            if (row > 0) {
                top = (verticalSpacing + h) * row;
            }
            lyp.setMargins(left, top, 0, 0);
            View view = mGridListAdapter.getView(i,null,null);
            if (!blockDescendant) {
                view.setOnClickListener(mOnClickListener);
            }
            view.setTag(INDEX_TAG, i);
            addView(view, lyp);
        }
    }

    public BaseAdapter getAdapter() {
        // TODO Auto-generated method stub
        return mGridListAdapter;
    }

    public void setNumColumns(int column) {
        // TODO Auto-generated method stub
        mGridListAdapter.setColumnNum(column);
    }

    public LayoutInflater getmLayoutInflater() {
        return mLayoutInflater;
    }

    public void setmLayoutInflater(LayoutInflater mLayoutInflater) {
        this.mLayoutInflater = mLayoutInflater;
    }
}
