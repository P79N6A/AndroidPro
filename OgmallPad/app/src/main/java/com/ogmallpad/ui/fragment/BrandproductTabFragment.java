package com.ogmallpad.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.manager.AnimatorManager;
import com.junshan.pub.utils.SoftKeyBoardUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmallpad.R;
import com.ogmallpad.adapter.PubViewPagerAdapter;
import com.ogmallpad.constant.Constants;
import com.ogmallpad.constant.MsgConstant;
import com.ogmallpad.contract.BrandproductContract;
import com.ogmallpad.databinding.SearchHeaderLayoutBinding;
import com.ogmallpad.databinding.SpzsHeaderLayoutBinding;
import com.ogmallpad.presenter.BrandproductPresenter;
import com.ogmallpad.ui.BaseFragment;
import com.shan.netlibrary.bean.BrandlistBean;
import com.shan.netlibrary.bean.CategorysBean;
import com.shan.netlibrary.bean.HasProductBean;
import com.shan.netlibrary.bean.StylesBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 品牌家具
 * Created by chenjunshan on 2018-07-03.
 */

public class BrandproductTabFragment extends BaseFragment<SpzsHeaderLayoutBinding, Object> implements BrandproductContract.View {
    private BrandproductPresenter presenter;
    private int id;//父id
    private List<BrandproductFragment> fragments;
    private int currentPos = 0;//当前tab
    private String title = "";
    private int parentId;//第一次导航选中的id
    private SearchHeaderLayoutBinding headerBinding;

    private StylesBean styleBean;
    private BrandlistBean brandBean;
    private int category2Id;
    private int category3Id;

    private final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x55) {
                if (category3Id != 0) {
                    for (int i = 0; i < fragments.size(); i++) {
                        if (fragments.get(i).id == category3Id) {
                            mBinding.brandProductViewPager.setCurrentItem(i);
                            break;
                        }
                    }
                    category3Id = 0;
                }
            }else if (msg.what == 0x59){
                if (category2Id != 0) {
                    for (int i = 0; i < categorysBean.getData().size(); i++) {
                        if (category2Id == categorysBean.getData().get(i).getId()) {
                            radioButtons.get(i).setChecked(true);
                            parentId = category2Id;
                            hasProduct(category2Id);
                            refreshTabStatus(i);
                        } else {
                            radioButtons.get(i).setChecked(false);
                        }
                    }
                    category2Id = 0;
                }
            }
        }
    };


    @Override
    public int bindLayout() {
        return R.layout.spzs_header_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.VISIBLE);
        titleBinding.tvLeft.setText(title);
    }

    @Override
    public void onLeftClick() {
        SoftKeyBoardUtils.closeKeybord(mBinding.etSearch, getActivity());
        super.onLeftClick();
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        id = getActivity().getIntent().getIntExtra(Constants.ID, 0);
        category2Id = getActivity().getIntent().getIntExtra(Constants.CATEGORY2ID,0);
        category3Id = getActivity().getIntent().getIntExtra(Constants.CATEGORY3ID,0);
        presenter = new BrandproductPresenter(getActivity(), this);
        getData1(id);
        mBinding.etSearch.setHint(R.string.str_spm);
        title = getActivity().getIntent().getStringExtra(Constants.TITLE);
        addHeadView();
        styles(id);
        brand(id);
    }

    public void addHeadView(){
        headerBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.search_header_layout, titleBinding.frameRight, false);
        titleBinding.frameRight.addView(headerBinding.getRoot());
        headerBinding.etSearch.setHint("商品名称");
        headerBinding.etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    search();
                    SoftKeyBoardUtils.closeKeybord(view, getActivity());
                    return true;
                }
                return false;
            }
        });
        headerBinding.tvSpSx.setOnClickListener(this);
    }

    private boolean isLoadHeaderData = true;
    private List<RadioButton> radioButtons;

    /**
     * 刷新顶部tab状态
     */
    private void refreshTabStatus(int checkPos) {
        if (radioButtons != null && categorysBean != null) {
            for (int i = 0; i < radioButtons.size(); i++) {
                if (i == checkPos) {
                    radioButtons.get(i).setChecked(true);
                } else {
                    radioButtons.get(i).setChecked(false);
                }
            }
        }
    }

    private CategorysBean categorysBean;

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.CATEGORYS) {
            //顶部Tab
            categorysBean = (CategorysBean) baseBean;
            radioButtons = new ArrayList<>();
            for (int i = 0; i < categorysBean.getData().size(); i++) {
                final CategorysBean.DataBean dataBean = categorysBean.getData().get(i);
                final int j = i;
                RadioButton childView = (RadioButton) LayoutInflater.from(getActivity()).inflate(R.layout.spzs_header_top_item_layout, mBinding.llTop, false);
                childView.setText(dataBean.getName());
                radioButtons.add(childView);
                if (i == 0) {
                    childView.setChecked(true);
                } else {
                    childView.setChecked(false);
                }
                mBinding.llTop.addView(childView);
                childView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        parentId = dataBean.getId();
                        hasProduct(dataBean.getId());
                        refreshTabStatus(j);
                    }
                });
            }
            if (category2Id != 0){
                handler.sendEmptyMessageDelayed(0x59,400);
            }else {
                if (categorysBean.getData().size() > 0) {
                    parentId = categorysBean.getData().get(0).getId();
                    hasProduct(categorysBean.getData().get(0).getId());
                    refreshTabStatus(0);
                }
            }
        } else if (mTag == HttpPresenter.HASPRODUCTBEAN) {
            HasProductBean bean = (HasProductBean) baseBean;
            //底部Tab
            HasProductBean.DataBean allBean = new HasProductBean.DataBean();
            allBean.setId(Constants.ALLBEAN);
            allBean.setName(getString(R.string.qb));
            bean.getData().add(0, allBean);
            fragments = new ArrayList<>();
            List<String> titles = new ArrayList<>();
            for (int i = 0; i < bean.getData().size(); i++) {
                BrandproductFragment brandproductFragment = new BrandproductFragment();
                brandproductFragment.setId(bean.getData().get(i).getId());
                brandproductFragment.setParentId(parentId);
                fragments.add(brandproductFragment);
                titles.add(bean.getData().get(i).getName());
            }
            mBinding.brandProductViewPager.setAdapter(new PubViewPagerAdapter<BrandproductFragment>(getActivity().getSupportFragmentManager(), fragments, titles));
            mBinding.brandProductTabs.setViewPager(mBinding.brandProductViewPager);
            mBinding.brandProductTabs.setTextSize(12);


            if (category3Id != 0){
                handler.sendEmptyMessageDelayed(0x55, 1000);
            }
        }else if (mTag == HttpPresenter.STYLES){
            styleBean = (StylesBean) baseBean;
        }else if (mTag == HttpPresenter.BRANDLIST){
            brandBean = (BrandlistBean) baseBean;
//            if (presenter != null && presenter.dialogSx != null && presenter.dialogSx.isShowing()){
//                presenter.bindPPData(brandBean);
//            }
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    public void onDestroy() {
        presenter.cancelAllRequest();
        super.onDestroy();
    }

    //获取产品分类列表 categoryId 不传时，获取所有的一级分类
    private void getData1(int id) {
        //获取产品分类列表 categoryId 不传时，获取所有的一级分类
        Map<String, String> map = new HashMap<>();
        map.put("categoryId", String.valueOf(id));
        presenter.categorys(map);
    }

    //获取产品分类列表 categoryId 不传时，获取所有的一级分类
    private void hasProduct(int id) {
        //获取产品分类列表 categoryId 不传时，获取所有的一级分类
        Map<String, String> map = new HashMap<>();
        map.put("categoryId", String.valueOf(id));
        presenter.hasProduct(map);
    }

    private boolean isSearch = false;

    @Override
    protected void initEvent() {
        super.initEvent();
        //搜索
        mBinding.ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSearch) {
                    search();
                } else {
                    mBinding.etSearch.setFocusable(true);
                    mBinding.etSearch.setFocusableInTouchMode(true);
                    mBinding.etSearch.requestFocus();
                    AnimatorManager.startAnimotion(mBinding.llSearch, 0, 240, 300, AnimatorManager.TRANSLATIONX);
                    SoftKeyBoardUtils.openKeybord(mBinding.etSearch, getActivity());
                }
            }
        });
        //键盘监听
        SoftKeyBoardUtils.setListener(getActivity(), new SoftKeyBoardUtils.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                isSearch = true;
            }

            @Override
            public void keyBoardHide(int height) {
                isSearch = false;
                AnimatorManager.startAnimotion(mBinding.llSearch, 240, 0, 300, AnimatorManager.TRANSLATIONX);
            }
        });
        //viewpager滑动监听
        mBinding.brandProductTabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPos = position;
                //清空搜索关键词
                fragments.get(position).clearSearchWord();
                mBinding.etSearch.setText("");

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void search() {
        String text = headerBinding.etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            ToastUtils.toast("请输入商品名称");
            return;
        }
        //隐藏键盘
        SoftKeyBoardUtils.closeKeybord(mBinding.etSearch, getActivity());
        fragments.get(currentPos).startSearch(text);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
//        if (msgEvent.getType() == MsgConstant.ISHIDEPRODUCTTAB) {
//            boolean isHide = (boolean) msgEvent.getBean();
//            int height = mBinding.llHeader.getHeight();
//            if (isHide) {
//                AnimatorManager.startAnimotion(mBinding.llHeader, 0, -height, 500, AnimatorManager.TRANSLATIONY);
//            } else {
//                AnimatorManager.startAnimotion(mBinding.llHeader, -height, 0, 500, AnimatorManager.TRANSLATIONY);
//            }
//        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_sp_sx:
                presenter.showSxDialog(styleBean,brandBean);
                break;
        }
    }

    public void styles(int id){
        Map<String, String> map = new HashMap<>();
        map.put("type", "1");//1品牌，2方案
        if (id != Constants.ALLBEAN) {
            map.put("cId", String.valueOf(id));
        }
        presenter.styles(map);
    }

    public void brand(int id){
        Map<String, String> map2 = new HashMap<>();
        map2.put("pageNo", "1");
        map2.put("pageSize", "5000");
        map2.put("categoryId", String.valueOf(id));
        presenter.brandlist(map2, false);
    }


}