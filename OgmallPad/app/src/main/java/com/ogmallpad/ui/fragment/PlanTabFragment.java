package com.ogmallpad.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListAdapter;

import com.junshan.pub.adapter.CommonAdapter;
import com.junshan.pub.manager.AnimatorManager;
import com.junshan.pub.utils.SoftKeyBoardUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmallpad.R;
import com.ogmallpad.constant.SpConstant;
import com.ogmallpad.contract.PlanContract;
import com.ogmallpad.databinding.PlanLeftChildItemLayoutBinding;
import com.ogmallpad.databinding.PlanTabLayoutBinding;
import com.ogmallpad.presenter.PlanPresenter;
import com.ogmallpad.ui.BaseFragment;
import com.ogmallpad.ui.activity.HomeActivity;
import com.shan.netlibrary.bean.StylesBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 方案展示
 * Created by chenjunshan on 18-7-5.
 */

public class PlanTabFragment extends BaseFragment<PlanTabLayoutBinding, Object> implements PlanContract.View {
    private PlanPresenter presenter;
    private int currentPos = 0;//当前tab

    @Override
    public int bindLayout() {
        return R.layout.plan_tab_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);


    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new PlanPresenter(getActivity(), this);
        mBinding.etSearch.setHint(R.string.str_fam);
        mBinding.tvPt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBinding.recyVPt.getVisibility() == View.GONE){
                    mBinding.recyVPt.setVisibility(View.VISIBLE);
                }else {
                    mBinding.recyVPt.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (bean == null) {
            Map<String, String> map = new HashMap<>();
            map.put("type", "2");//1品牌，2方案
            presenter.styles(map);
        }
        ((HomeActivity)getActivity()).getBinding().icLogo.setVisibility(View.GONE);
        ((HomeActivity)getActivity()).getBinding().icTitleIcon.setVisibility(View.VISIBLE);
        ((HomeActivity)getActivity()).getBinding().tvTitle.setVisibility(View.VISIBLE);
        ((HomeActivity)getActivity()).getBinding().icTitleIcon.setImageResource(R.mipmap.ic_plan_on);
        ((HomeActivity)getActivity()).getBinding().tvTitle.setText("高端方案");
        ((HomeActivity)getActivity()).getBinding().llSearch.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    private StylesBean bean;
    private List<PlanFragment> fragments;

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.STYLES) {
            bean = (StylesBean) baseBean;
            //增加全部
            StylesBean.DataBean allBean = new StylesBean.DataBean();
            allBean.setName("全部");
            allBean.setId(SpConstant.STYLEID);
            bean.getData().add(0, allBean);
            if (bean.getData().size() > 0) {
                fragments = new ArrayList<>();
                List<String> titles = new ArrayList<>();
                for (int i = 0; i < bean.getData().size(); i++) {
                    PlanFragment planFragment = new PlanFragment();
                    planFragment.setId(String.valueOf(bean.getData().get(i).getId()));
                    fragments.add(planFragment);
                    titles.add(bean.getData().get(i).getName());
                }
                initRecyclerView(titles);
//                mBinding.planViewPager.setAdapter(new PubViewPagerAdapter<>(getActivity().getSupportFragmentManager(), fragments, titles));
//                mBinding.planTabs.setViewPager(mBinding.planViewPager);
//                mBinding.planTabs.setTextSize(14);
            }
        }
    }

    private void initRecyclerView(List<String> titles) {
        mBinding.recyVPt.setAdapter(new CommonAdapter<PlanLeftChildItemLayoutBinding,String>(getContext(), R.layout.plan_left_child_item_layout, titles) {
            @Override
            protected void getItem(PlanLeftChildItemLayoutBinding binding, String bean, int position) {
                binding.cb.setText(bean);
                if (currentPos == position){
                    binding.cb.setTextColor(getResources().getColor(R.color.color_E70052));
                    binding.cb.setBackgroundResource(R.color.white);
                }else {
                    binding.cb.setBackgroundResource(R.color.transparent);
                    binding.cb.setTextColor(Color.parseColor("#787878"));
                }
            }

            @Override
            protected void itemOnclick(PlanLeftChildItemLayoutBinding binding, String bean, int position) {
                currentPos = position;
                PlanFragment fragment = fragments.get(position);
                jumpFragment(fragment);
                notifyDataSetChanged();
            }
        });
        currentPos = 0;
        PlanFragment fragment = fragments.get(currentPos);
        jumpFragment(fragment);
    }

    private void jumpFragment(PlanFragment fragment) {
        if (isVisible()) {
            FragmentManager fm = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fl_content, fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    private boolean isSearch = false;

    @Override
    protected void initEvent() {
        super.initEvent();
//        mBinding.ivToFirst.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (fragments != null && fragments.size() > 0) {
//                    mBinding.planViewPager.setCurrentItem(0);
//                }
//            }
//        });
//        //搜索
//        mBinding.ivSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (isSearch) {
//                    search();
//                } else {
//                    mBinding.etSearch.setFocusable(true);
//                    mBinding.etSearch.setFocusableInTouchMode(true);
//                    mBinding.etSearch.requestFocus();
//                    AnimatorManager.startAnimotion(mBinding.llSearch, 0, 240, 300, AnimatorManager.TRANSLATIONX);
//                    SoftKeyBoardUtils.openKeybord(mBinding.etSearch, getActivity());
//                }
//            }
//        });
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
//        mBinding.planTabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                currentPos = position;
//                //清空搜索关键词
//                fragments.get(position).clearSearchWord();
//                mBinding.etSearch.setText("");
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
        //监听搜索
        mBinding.etSearch.addTextChangedListener(textWatcher);
        mBinding.etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //是否是回车键
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    currentPos = 0;
                    PlanFragment fragment = fragments.get(currentPos);
                    jumpFragment(fragment);
                    CommonAdapter adapter = (CommonAdapter) mBinding.recyVPt.getAdapter();
                    adapter.notifyDataSetChanged();
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
//            if (fragments != null) {
//                String text = editable.toString();
//                if (TextUtils.isEmpty(text)) {
//                    fragments.get(currentPos).startSearch(text);
//                }
//            }
        }
    };

    private void search() {

        String text = mBinding.etSearch.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            ToastUtils.toast("请输入方案名称");
            return;
        }
        //隐藏键盘
        SoftKeyBoardUtils.closeKeybord(mBinding.etSearch, getActivity());
        fragments.get(currentPos).startSearch(text);
    }
}
