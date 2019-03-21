package com.ogmallpad.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.SoftKeyBoardUtils;
import com.ogmallpad.R;
import com.ogmallpad.constant.Constants;
import com.ogmallpad.constant.SpConstant;
import com.ogmallpad.contract.PlanContract;
import com.ogmallpad.databinding.FgPlanLayoutBinding;
import com.ogmallpad.presenter.PlanPresenter;
import com.ogmallpad.ui.BaseFragment;
import com.shan.netlibrary.bean.DesigndetailsBean;
import com.shan.netlibrary.bean.DesignlistBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 方案
 * Created by chenjunshan on 2018-07-02.
 */

public class PlanFragment extends BaseFragment<FgPlanLayoutBinding, DesignlistBean.DataBean.ListBean> implements PlanContract.View {
    private PlanPresenter presenter;
    private String id;

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
        this.searchWord = searchWord;
        id = String.valueOf(SpConstant.STYLEID);
        onRefresh(false);
    }

    public void setId(String id) {
        this.id = id;
        getData();
    }

    /**
     * 获取数据
     */
    private void getData() {
        if (lvBinding != null && !TextUtils.isEmpty(id)) {
            designlist(false);
        }
    }

    @Override
    public int bindItemLayout() {
        return R.layout.fg_plan_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        setRecycViewGrid(2);
        lvBinding.llParent.setBackgroundResource(R.color.white);
        presenter = new PlanPresenter(getActivity(), this);
//        addScrollTopView();
    }

    private DesignlistBean designlistBean;

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.DESIGNLIST) {
            //根据风格Id所有审核通过的方案
            designlistBean = (DesignlistBean) baseBean;
            isLoadingMore(designlistBean.getData().getPageResult().getTotalPage());
            List<DesignlistBean.DataBean.ListBean> list = designlistBean.getData().getList();
            addData(list);
        } else if (mTag == HttpPresenter.DESIGNDETAILS) {
            DesigndetailsBean bean = (DesigndetailsBean) baseBean;
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.BEAN, bean);
            startFragment(PlandetailsFragment.class, bundle);
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    @Override
    protected void initEvent() {
        super.initEvent();

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

        }
    }

    @Override
    protected void getListVewItem(FgPlanLayoutBinding binding, DesignlistBean.DataBean.ListBean item, int position) {
        super.getListVewItem(binding, item, position);
        ImageCacheUtils.loadImage(getActivity(), item.getHeadImage(), binding.ivMenu);
        binding.tvName.setText(item.getTitle());
    }

    @Override
    protected void onItemClick(DesignlistBean.DataBean.ListBean bean, int position) {
        super.onItemClick(bean, position);
        SoftKeyBoardUtils.closeKeybord(lvBinding.getRoot(), getActivity());
        designDetails(bean);
    }

    private void designlist(boolean isShow) {
        //根据风格Id所有审核通过的方案
        Map<String, String> map = new HashMap<>();
        map.put("pageNo", String.valueOf(pageNum));
        map.put("pageSize", String.valueOf(pageSize));
        map.put("searchWord", searchWord);
        if (!id.equals(String.valueOf(SpConstant.STYLEID))) {
            map.put("styleId", String.valueOf(id));
        }
//        if (loginBean != null) {
//            if (loginBean.getData().getUserType().equals("Designer")) {
//                map.put("userId", String.valueOf(loginBean.getData().getUserId()));
//            }
//        }
        if (presenter == null){
            presenter = new PlanPresenter(getContext(),this);
        }
        presenter.designlist(map, isShow);
    }

    /**
     * 获取方案的详细信息
     *
     * @param bean
     */
    private void designDetails(DesignlistBean.DataBean.ListBean bean) {
        Map<String, String> map = new HashMap<>();
        map.put("designId", String.valueOf(bean.getId()));
        presenter.designdetails(map);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        designlist(true);
    }

    @Override
    public void onRefresh(boolean isShow) {
        super.onRefresh(isShow);
        designlist(isShow);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (isHasNext) {
            designlist(true);
        }
    }

    /**
     * 增加底部VIew
     */
    public void addScrollTopView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.scroll_top_layout, null);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        params.height = 150;
        params.width = 150;
        params.rightMargin = 30;
        params.bottomMargin = 100;
        lvBinding.fl.addView(view, params);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smoothMoveToPosition(0);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (designlistBean == null) {
            getData();
        }
    }
}
