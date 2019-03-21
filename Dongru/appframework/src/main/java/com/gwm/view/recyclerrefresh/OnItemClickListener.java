package com.gwm.view.recyclerrefresh;

import android.view.View;
import android.view.ViewGroup;

public interface OnItemClickListener<T>{
    void onItemClick(ViewGroup parent, View view, T t, int position, RecyclerView.ViewHolder viewHolder);
    boolean onItemLongClick(ViewGroup parent, View view, T t, int position, RecyclerView.ViewHolder viewHolder);
}
