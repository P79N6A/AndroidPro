package com.ogmallpad.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.listener.RecyclerViewScrollDetector;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.SoftKeyBoardUtils;
import com.ogmallpad.R;
import com.ogmallpad.constant.Constants;
import com.ogmallpad.constant.MsgConstant;
import com.ogmallpad.contract.BrandlookContract;
import com.ogmallpad.databinding.FgBrandlookItemLayoutBinding;
import com.ogmallpad.presenter.BrandlookPresenter;
import com.ogmallpad.ui.BaseFragment;
import com.shan.netlibrary.bean.BranddetailsBean;
import com.shan.netlibrary.bean.BrandlistBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 品牌展示
 * Created by chenjunshan on 2018-07-03.
 */

public class BrandlookFragment extends BaseFragment<FgBrandlookItemLayoutBinding, BrandlistBean.DataBean.ListBean> implements BrandlookContract.View {
    private BrandlookPresenter presenter;
    private String id;//风格id
    private String categoryId;//第一级分类id
    private String searchWord;

    /**
     * 清除搜索关键词
     */
    public void clearSearchWord() {
        if (!TextUtils.isEmpty(searchWord)) {
            this.searchWord = null;
            onRefresh();
        }
    }

    //搜索
    public void startSearch(String searchWord) {
        if (TextUtils.isEmpty(this.searchWord) && TextUtils.isEmpty(searchWord)) {
            return;
        }
        this.searchWord = searchWord;
        onRefresh();
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setId(String id) {
        this.id = id;
        getData();
    }

    private void getData() {
        if (lvBinding != null && !TextUtils.isEmpty(id)) {
            brandlist(false);
        }
    }

    @Override
    public int bindItemLayout() {
        return R.layout.fg_brandlook_item_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        setRecycViewGrid(3);
        presenter = new BrandlookPresenter(getActivity(), this);
//        addScrollTopView();
        //增加头部
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.tab_header_layout, lvBinding.xrecyclerview, false);
        lvBinding.xrecyclerview.addHeaderView(view);
    }

    private BrandlistBean designlistBean;

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.BRANDLIST) {
            designlistBean = (BrandlistBean) baseBean;
            isLoadingMore(designlistBean.getData().getPageResult().getTotalPage());
            List<BrandlistBean.DataBean.ListBean> list = designlistBean.getData().getList();
            if (pageNum == 1){
                clearData();
            }
            addData(list);
        } else if (mTag == HttpPresenter.BRANDDETAILS) {
            BranddetailsBean bean = (BranddetailsBean) baseBean;
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.BEAN, bean);
            startFragment(BranddetailsFragment.class, bundle);
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    protected void onItemClick(BrandlistBean.DataBean.ListBean bean, int position) {
        super.onItemClick(bean, position);
        SoftKeyBoardUtils.closeKeybord(lvBinding.getRoot(), getActivity());
        branddetails(bean);
    }

    /**
     * 获取品牌的详细信息
     *
     * @param bean
     */
    private void branddetails(BrandlistBean.DataBean.ListBean bean) {
        Map<String, String> map = new HashMap<>();
        map.put("brandId", String.valueOf(bean.getId()));
        presenter.branddetails(map);
    }

    @Override
    protected void getListVewItem(FgBrandlookItemLayoutBinding binding, BrandlistBean.DataBean.ListBean item, int position) {
        super.getListVewItem(binding, item, position);
        ImageCacheUtils.loadImage(getActivity(), item.getHeadImage(), binding.iv);
        ImageCacheUtils.loadImage(getActivity(), item.getImage(), binding.iv2);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        brandlist(false);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (isHasNext) {
            brandlist(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    /**
     * 根据风格id返回品牌列表 当风格id为空时查询所有的品牌信息
     */
    private void brandlist(boolean isShow) {
        Map<String, String> map = new HashMap<>();
        map.put("pageNo", String.valueOf(pageNum));
        map.put("pageSize", String.valueOf(pageSize));
        if (!categoryId.equals(String.valueOf(Constants.ALLBEAN))) {
            map.put("categoryId", categoryId);
        }
        map.put("searchWord", searchWord);//搜索关键词
        if (!id.equals(String.valueOf(Constants.ALLBEAN))) {
            map.put("styleId", id);
        }
        presenter.brandlist(map, isShow);
    }

//    /**
//     * 增加底部VIew
//     */
//    public void addScrollTopView() {
//        View view = LayoutInflater.from(getActivity()).inflate(R.layout.scroll_top_layout, null);
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.gravity = Gravity.BOTTOM | Gravity.RIGHT;
//        params.height = 150;
//        params.width = 150;
//        params.rightMargin = 30;
//        params.bottomMargin = 100;
//        lvBinding.fl.addView(view, params);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                smoothMoveToPosition(0);
//            }
//        });
//    }

    @Override
    public void onResume() {
        super.onResume();
        if (designlistBean == null) {
            getData();
        }
    }

    private boolean isup = false;

    @Override
    protected void initEvent() {
        super.initEvent();
        lvBinding.xrecyclerview.addOnScrollListener(new RecyclerViewScrollDetector() {
            @Override
            protected void onScrollUp() {
                Log.i(TAG, "onScrollUp: ");
                if (!isup) {
                    isup = true;
//                    EventBus.getDefault().post(new MessageEvent(MsgConstant.ISHIDEBRANDTAB, true));
                }
            }

            @Override
            protected void onScrollDown() {
                Log.i(TAG, "onScrollDown: ");
                if (isup) {
                    isup = false;
//                    EventBus.getDefault().post(new MessageEvent(MsgConstant.ISHIDEBRANDTAB, false));
                }
            }
        });
    }

}