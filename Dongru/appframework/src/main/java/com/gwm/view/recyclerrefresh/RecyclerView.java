package com.gwm.view.recyclerrefresh;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 *  配合SwipeRefreshLayout 一起使用的  带有下拉加载的RecyclerView   需要配合RefreshAdapter
 */

public class RecyclerView extends android.support.v7.widget.RecyclerView{

    private OnLoadMoreListener mOnLoadMoreListener;

    private OnScrollListener onScrollListener = new OnScrollListener() {
        public int lastVisibleItem;
        @Override
        public void onScrollStateChanged(android.support.v7.widget.RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            //判断RecyclerView的状态 是空闲时，同时，是最后一个可见的ITEM时才加载
            RefreshAdapter adapter = (RefreshAdapter) getAdapter();
            if(newState== android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE&&lastVisibleItem+1==adapter.getItemCount()){
                if (mOnLoadMoreListener != null){
                    mOnLoadMoreListener.onLoadMore();
                    adapter.changeMoreStatus(RefreshAdapter.PULLUP_LOAD_MORE);
                }else {
                    adapter.changeMoreStatus(RefreshAdapter.NO_LOAD_MORE);
                }
            }
        }

        @Override
        public void onScrolled(android.support.v7.widget.RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            //最后一个可见的ITEM
            lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        }
    };

    public RecyclerView(Context context) {
        super(context);
        initLoadMoreListener();
    }
    public RecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initLoadMoreListener();
    }

    public RecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initLoadMoreListener();
    }

    /**
     * 设置加载更多监听
     * @param mOnLoadMoreListener
     */
    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }


    /**
     * 当已经加载完所有数据时需要调用该方法通知RecyclerView 已经到最底部了
     */
    public void noloadmore(){
        RefreshAdapter adapter = (RefreshAdapter) getAdapter();
        adapter.changeMoreStatus(RefreshAdapter.NO_LOAD_MORE);
    }

    private void initLoadMoreListener() {
        addOnScrollListener(onScrollListener);
    }
    public interface OnLoadMoreListener{
        void onLoadMore();
    }
}
