package com.gwm.view.recyclerrefresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gwm.R;
import com.gwm.R2;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class RefreshAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 2;
    protected Context mContext;
    LayoutInflater mInflater;
    List<T> mDatas;
    public static final int TYPE_ITEM   = 0;
    public static final int TYPE_FOOTER = 1;
    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE     = 1;
    //没有加载更多 隐藏
    public static final int NO_LOAD_MORE     = 2;

    //上拉加载更多状态-默认为0
    private int mLoadMoreStatus = 0;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }


    public RefreshAdapter(Context context, List<T> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    }
    protected int getPosition(RecyclerView.ViewHolder viewHolder) {
        viewHolder.getLayoutPosition();
        return viewHolder.getAdapterPosition();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View itemView = conViewItem();
            ItemViewHolder viewHolder = getViewHolder(itemView);
            setListener(parent,viewHolder);
            return viewHolder;
        } else if (viewType == TYPE_FOOTER) {
            View itemView = mInflater.inflate(R.layout.load_more_footview_layout, parent, false);
            return new FooterViewHolder(itemView);
        }else if (viewType == TYPE_HEADER){
            View headView = conViewItemHeader();
            HeaderViewHolder headerViewHolder = getHeaderViewHolder(headView);
            return headerViewHolder;
        }
        return null;
    }

    protected abstract HeaderViewHolder getHeaderViewHolder(View headView);

    protected abstract View conViewItemHeader();

    private void setListener(final ViewGroup parent, final ItemViewHolder viewHolder) {
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = getPosition(viewHolder);
                    if (isItemHeader()){
                        position--;
                    }
                    if (position != -1){
                        if (isItemFooter() && position == getItemCount()){
                        }else {
                            mOnItemClickListener.onItemClick(parent,viewHolder.getConvertView(),mDatas.get(position),position,viewHolder);
                        }
                    }
                }
            }
        });


        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = getPosition(viewHolder);
                    if (isItemHeader()){
                        position--;
                    }
                    if (position != -1){
                        if (isItemFooter() && position == getItemCount()){
                        }else {
                            mOnItemClickListener.onItemLongClick(parent,viewHolder.getConvertView(),mDatas.get(position),position,viewHolder);
                        }
                    }
                }
                return false;
            }
        });
    }

    /**
     * 重写该方法返回ViewHolder对象
     * @param itemView
     * @return
     */
    protected abstract ItemViewHolder getViewHolder(View itemView);

    /**
     * 重写该方法返回item的View
     * @return
     */
    protected abstract View conViewItem();

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ItemViewHolder) {

            ItemViewHolder<T> itemViewHolder = (ItemViewHolder<T>) holder;
            T itemData = null;
            if (isItemHeader()){
                itemData = mDatas.get(position - 1);
            }else {
                itemData = mDatas.get(position);
            }

            itemViewHolder.setValue(itemData);

        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;

            switch (mLoadMoreStatus) {
                case PULLUP_LOAD_MORE:
                    footerViewHolder.mTvLoadText.setText("上拉加载更多...");
                    break;
                case LOADING_MORE:
                    footerViewHolder.mTvLoadText.setText("正加载更多...");
                    break;
                case NO_LOAD_MORE:
                    //隐藏加载更多
                    footerViewHolder.mLoadLayout.setVisibility(View.GONE);
                    break;

            }
        }else if (holder instanceof HeaderViewHolder){
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.initData();
        }

    }

    @Override
    public int getItemCount() {
        //RecyclerView的count设置为数据总条数+ 1（footerView）
        int count = mDatas.size();
        if (isItemHeader()){
            count++;
        }
        if (isItemFooter()){
            count++;
        }
        return count;
    }



    @Override
    public int getItemViewType(int position) {
        if (isItemHeader() && isItemFooter()){   //有头有尾
            if (position == 0){
                return TYPE_HEADER;
            }
            if (position == getItemCount()){
                return TYPE_FOOTER;
            }
            return TYPE_ITEM;
        }else if (isItemHeader() && !isItemFooter()){  //有头没尾
            if (position == 0){
                return TYPE_HEADER;
            }
            return TYPE_ITEM;
        }else if (!isItemHeader() && !isItemFooter()){  //没头没尾
            return TYPE_ITEM;
        }else if (!isItemHeader() && isItemFooter()){  //没头有尾
            if (position == getItemCount()){
                return TYPE_FOOTER;
            }
            return TYPE_ITEM;
        }
        return -1;
    }

    public class FooterViewHolder<T> extends RecyclerView.ViewHolder {
        @BindView(R2.id.pbLoad)
        ProgressBar mPbLoad;
        @BindView(R2.id.tvLoadText)
        TextView mTvLoadText;
        @BindView(R2.id.loadLayout)
        LinearLayout mLoadLayout;
        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    public void addHeaderItem(List<T> items) {
        mDatas.addAll(0, items);
        notifyDataSetChanged();
    }

    public void addFooterItem(List<T> items) {
        mDatas.addAll(items);
        notifyDataSetChanged();
    }
    public void removeAllData(){
        mDatas.clear();
        notifyDataSetChanged();
    }

    /**
     * 更新加载更多状态
     * @param status
     */
    public void changeMoreStatus(int status){
        mLoadMoreStatus=status;
        notifyDataSetChanged();
    }

    protected View inflate(ViewGroup parent,int resId){
        return LayoutInflater.from(mContext).inflate(resId,parent,false);
    }

    public T getItem(int position){
        return mDatas.get(position);
    }

    public abstract boolean isItemHeader();
    public abstract boolean isItemFooter();

    public static abstract class ItemViewHolder<T> extends RecyclerView.ViewHolder {

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public abstract void setValue(T itemData);

        public View getConvertView() {
            return itemView;
        }
    }
    public static abstract class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public abstract void initData();
    }
}