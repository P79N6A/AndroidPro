package com.ogmamllpadnew.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.ScreenUtils;
import com.junshan.pub.utils.SoftKeyBoardUtils;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.contract.ScomboContract;
import com.ogmamllpadnew.databinding.FgScomboLayoutBinding;
import com.ogmamllpadnew.databinding.TitleRightSearchLayoutBinding;
import com.ogmamllpadnew.presenter.ScomboPresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.bean.SelectComboListBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/2/27.
 * 套餐列表
 */

public class ScomboFragment extends BaseFragment<FgScomboLayoutBinding,SelectComboListBean.DataBean> implements ScomboContract.View{

    private TitleRightSearchLayoutBinding searchLayoutBinding;
    private ScomboPresenter presenter;
    private int width;
    private String keywords;

    @Override
    public int bindItemLayout() {
        return R.layout.fg_scombo_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.tvLeft.setText("整屋套餐");
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new ScomboPresenter(getContext(),this);
        addHeaderSearchView();
        width = ScreenUtils.getScreenWidth();
        lvBinding.xrecyclerview.setPadding(294,0,184,20);
        setRecycViewGrid(2);

    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        selectComboList();
    }

    private void addHeaderSearchView() {
        //添加搜索View
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        searchLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.title_right_search_layout, titleBinding.rl, false);
        titleBinding.rl.addView(searchLayoutBinding.getRoot(), params);
        searchLayoutBinding.etSearch.setHint(getString(R.string.str_tcmc));
        searchLayoutBinding.tvAdd.setVisibility(View.GONE);
        searchLayoutBinding.tvScreen.setVisibility(View.GONE);

        searchLayoutBinding.etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER){
                    setKeyWords(searchLayoutBinding.etSearch.getText().toString().trim());
                    SoftKeyBoardUtils.closeKeybord(view,getContext());
                    pageNum = 1;
                    selectComboList();
                }
                return false;
            }
        });
    }

    private void setKeyWords(String keywords) {
        this.keywords = keywords;
    }

    private void selectComboList() {
        Map<String,String> params = new HashMap<>();
        params.put("page",String.valueOf(pageNum));
        params.put("limit",String.valueOf(pageSize));
        params.put("keywords",keywords);
        presenter.selectComboList(params);
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.SELECTCOMBOLIST){
            if (pageNum == 1){
                clearData();
            }
            SelectComboListBean bean = (SelectComboListBean) baseBean;
            addData(bean.getData());
            isLoadingMore(bean.getCount());
        }
    }

    @Override
    protected void getListVewItem(FgScomboLayoutBinding binding, SelectComboListBean.DataBean item, int position) {
        super.getListVewItem(binding, item, position);
        ImageCacheUtils.loadImage(getContext(),item.getHeadImage(),binding.ivHeadImg);
        binding.tvName.setText(item.getComboName());
        binding.tvPrice.setText(item.getPriceScope());
        binding.tvGuige.setText("房间类型："+item.getRoomType());

    }

    @Override
    protected void onItemClick(SelectComboListBean.DataBean bean, int position) {
        super.onItemClick(bean, position);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ID,bean.getId());
        startFragment(ScomboDateilFragment.class,bundle);
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }
}
