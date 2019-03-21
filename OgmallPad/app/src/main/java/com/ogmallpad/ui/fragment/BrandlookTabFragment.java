package com.ogmallpad.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.ogmallpad.contract.BrandlookContract;
import com.ogmallpad.databinding.BrandLookTabLayoutBinding;
import com.ogmallpad.presenter.BrandlookPresenter;
import com.ogmallpad.ui.BaseFragment;
import com.ogmallpad.ui.activity.HomeActivity;
import com.shan.netlibrary.bean.CategorysofNameBean;
import com.shan.netlibrary.bean.StylesBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 品牌展示
 * Created by chenjunshan on 18-7-5.
 */

public class BrandlookTabFragment extends BaseFragment<BrandLookTabLayoutBinding, Object> implements BrandlookContract.View {
    private BrandlookPresenter presenter;

    @Override
    public int bindLayout() {
        return R.layout.brand_look_tab_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);

    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new BrandlookPresenter(getActivity(), this);
    }


    private StylesBean bean;
    private List<BrandlookFragment> fragments;
    private List<RadioButton> radioButtons;
    private CategorysofNameBean categorysBean;
    private int currentPos = 0;//当前tab

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

    private int categoryId;

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.CATEGORYSOFNAME) {
            categorysBean = (CategorysofNameBean) baseBean;
            if (categorysBean.getData().size() > 0) {
                //添加全部分类
                CategorysofNameBean.DataBean allBean = new CategorysofNameBean.DataBean();
                allBean.setName(getString(R.string.str_qb));
                allBean.setId(Constants.ALLBEAN);
                categorysBean.getData().add(0, allBean);
                //顶部Tab
                radioButtons = new ArrayList<>();
                for (int i = 0; i < categorysBean.getData().size(); i++) {
                    final CategorysofNameBean.DataBean dataBean = categorysBean.getData().get(i);
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
                            categoryId = categorysBean.getData().get(j).getId();
                            Map<String, String> map = new HashMap<>();
                            map.put("type", "1");//1品牌，2方案
                            if (categorysBean.getData().get(j).getId() != Constants.ALLBEAN) {
                                map.put("cId", String.valueOf(categorysBean.getData().get(j).getId()));
                            }
                            presenter.styles(map);
                            refreshTabStatus(j);
                        }
                    });
                    //设置tab状态
                    refreshTabStatus(0);
                }
                //获取第一个tab的数据
                if (categorysBean.getData().size() > 0) {
                    categoryId = categorysBean.getData().get(0).getId();
                    //getData1(categorysBean.getData().get(0).getId());
                    Map<String, String> map = new HashMap<>();
                    map.put("type", "1");//1品牌，2方案
                    //map.put("cId", String.valueOf(categorysBean.getData().get(0).getId()));
                    presenter.styles(map);
                }
            }
        } else if (mTag == HttpPresenter.STYLES) {
            bean = (StylesBean) baseBean;
            StylesBean.DataBean allBean = new StylesBean.DataBean();
            allBean.setId(Constants.ALLBEAN);
            allBean.setName("全部");
            bean.getData().add(0, allBean);
            if (bean.getData().size() > 0) {
                fragments = new ArrayList<>();
                List<String> titles = new ArrayList<>();
                for (int i = 0; i < bean.getData().size(); i++) {
                    BrandlookFragment brandlookFragment = new BrandlookFragment();
                    brandlookFragment.setCategoryId(String.valueOf(categoryId));
                    brandlookFragment.setId(String.valueOf(bean.getData().get(i).getId()));
                    fragments.add(brandlookFragment);
                    titles.add(bean.getData().get(i).getName());
                }
                mBinding.viewPager.setAdapter(new PubViewPagerAdapter<>(getActivity().getSupportFragmentManager(), fragments, titles));
                mBinding.tabs.setViewPager(mBinding.viewPager);
                mBinding.tabs.setTextSize(12);
            }
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (categorysBean == null) {
            categorysOfName();
        }
        ((HomeActivity)getActivity()).getBinding().icLogo.setVisibility(View.GONE);
        ((HomeActivity)getActivity()).getBinding().icTitleIcon.setVisibility(View.VISIBLE);
        ((HomeActivity)getActivity()).getBinding().tvTitle.setVisibility(View.VISIBLE);
        ((HomeActivity)getActivity()).getBinding().icTitleIcon.setImageResource(R.mipmap.ic_brand_on);
        ((HomeActivity)getActivity()).getBinding().tvTitle.setText("品牌展示");
        ((HomeActivity)getActivity()).getBinding().llSearch.setVisibility(View.VISIBLE);
        ((HomeActivity)getActivity()).getBinding().etSearch.setHint("品牌名称");
        ((HomeActivity)getActivity()).getBinding().etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER){
                    search();
                    SoftKeyBoardUtils.closeKeybord(view,getContext());
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.btn_brand:
//                presenter.showSelectDialog(fgData,ppData);
//                break;
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    private boolean isSearch = false;

    @Override
    protected void initEvent() {
        super.initEvent();
        /*mBinding.ivToFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list != null && list.size() > 0) {
                    mBinding.viewPager.setCurrentItem(0);
                }
            }
        });*/
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
        mBinding.tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        //监听搜索
        mBinding.etSearch.addTextChangedListener(textWatcher);
        mBinding.etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //是否是回车键
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    //隐藏键盘
                    SoftKeyBoardUtils.closeKeybord(v, getActivity());
                    search();
                }
                return false;
            }
        });
    }

    //监听搜索
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            if (TextUtils.isEmpty(text)) {
                fragments.get(currentPos).startSearch(text);
            }
        }
    };

    private void search() {
        String text = ((HomeActivity)getActivity()).getBinding().etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            ToastUtils.toast("请输入品牌名称");
            return;
        }
        //隐藏键盘
        SoftKeyBoardUtils.closeKeybord(((HomeActivity)getActivity()).getBinding().etSearch, getActivity());
        fragments.get(currentPos).startSearch(text);
    }

    /**
     * 根据分类名称搜索分类信息
     */
    private void categorysOfName() {
        Map<String, String> map = new HashMap<>();
        //map.put("name", "爆款");
        presenter.categorysofName(map);
    }

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
//        if (msgEvent.getType() == MsgConstant.ISHIDEBRANDTAB) {
//            boolean isHide = (boolean) msgEvent.getBean();
//            int height = mBinding.llHeader.getHeight();
//            if (isHide) {
//                AnimatorManager.startAnimotion(mBinding.llHeader, 0, -height, 500, AnimatorManager.TRANSLATIONY);
//            } else {
//                AnimatorManager.startAnimotion(mBinding.llHeader, -height, 0, 500, AnimatorManager.TRANSLATIONY);
//            }
//        }
    }
}
