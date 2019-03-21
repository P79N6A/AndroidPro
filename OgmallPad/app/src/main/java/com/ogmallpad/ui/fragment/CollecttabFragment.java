package com.ogmallpad.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.listener.TitleBarListener;
import com.junshan.pub.manager.TabManager;
import com.junshan.pub.utils.SoftKeyBoardUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmallpad.R;
import com.ogmallpad.constant.Constants;
import com.ogmallpad.constant.MsgConstant;
import com.ogmallpad.constant.TabConstant;
import com.ogmallpad.contract.CollecttabContract;
import com.ogmallpad.databinding.CollectHeaderLayoutBinding;
import com.ogmallpad.databinding.FgCollecttabLayoutBinding;
import com.ogmallpad.presenter.CollecttabPresenter;
import com.ogmallpad.ui.BaseActivity;
import com.shan.netlibrary.bean.CollectsstylesBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;

/**
 * 收藏
 * Created by chenjunshan on 2018-09-27.
 */

public class CollecttabFragment extends BaseActivity<FgCollecttabLayoutBinding, Object> implements CollecttabContract.View, TitleBarListener {
    private CollecttabPresenter presenter;
    private TabManager tabManager;
    private int position = 0;//底部Tab的位置
    private CollectsstylesBean collectsstylesBean;//风格数据
    private CollectHeaderLayoutBinding headerLayoutBinding;

    @Override
    public int bindLayout() {
        return R.layout.fg_collecttab_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.tvLeft.setText(getString(R.string.str_wdsc));
        headerLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.collect_header_layout, titleBinding.llAdd, false);
        titleBinding.llAdd.addView(headerLayoutBinding.getRoot());
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new CollecttabPresenter(this, this);
        iniTab();
        //获取收藏商品风格列表
        presenter.collectsstyles(null);
    }

    private void iniTab() {
        tabManager = new TabManager(this, mBinding.tabhost, TabConstant.MAIN_FRAGMENT, TabConstant.MAIN_IAMGEVIEW, TabConstant.MAIN_TEXTVIEW, this.getSupportFragmentManager());
        tabManager.initTab(R.id.fl_content);
    }


    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.COLLECTSSTYLES) {
            collectsstylesBean = (CollectsstylesBean) baseBean;
            CollectsstylesBean.DataBean allBean = new CollectsstylesBean.DataBean();
            allBean.setId(Constants.ALLBEAN);
            allBean.setName(getString(R.string.str_qb));
            collectsstylesBean.getData().add(0, allBean);
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
        mBinding.cbStyle.setOnClickListener(this);
        mBinding.cbPrice.setOnClickListener(this);
        headerLayoutBinding.cbEdit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    headerLayoutBinding.cbEdit.setText(getString(R.string.str_wc));
                    mBinding.llBottom.setVisibility(View.VISIBLE);
                    EventBus.getDefault().post(new MessageEvent(MsgConstant.IS_COLOCT_EDIT, true));
                } else {
                    headerLayoutBinding.cbEdit.setText(getString(R.string.str_bj2));
                    mBinding.llBottom.setVisibility(View.GONE);
                    EventBus.getDefault().post(new MessageEvent(MsgConstant.IS_COLOCT_EDIT, false));
                }
            }
        });
        headerLayoutBinding.etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //是否是回车键
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    String searchWord = headerLayoutBinding.etSearch.getText().toString().trim();
                    if (TextUtils.isEmpty(searchWord)) {
                        ToastUtils.toast("请输入搜索内容");
                    } else {
                        EventBus.getDefault().post(new MessageEvent(MsgConstant.SEARCH_COLLECT, searchWord));
                        SoftKeyBoardUtils.closeKeybord(headerLayoutBinding.etSearch, CollecttabFragment.this);
                    }
                }
                return false;
            }
        });
        mBinding.btnAll.setOnClickListener(this);
        mBinding.btnDelete.setOnClickListener(this);
        headerLayoutBinding.etSearch.addTextChangedListener(textWatcher);
        SoftKeyBoardUtils.setListener(this, new SoftKeyBoardUtils.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {

            }

            @Override
            public void keyBoardHide(int height) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.cb_style:
                if (collectsstylesBean == null)
                    return;
                presenter.showStyle(true, collectsstylesBean.getData());
                break;
            case R.id.cb_price:
                if (collectsstylesBean == null)
                    return;
                presenter.showStyle(false, collectsstylesBean.getData());
                break;
            case R.id.btn_all:
                EventBus.getDefault().post(new MessageEvent(MsgConstant.COLLECT_ALL_SELECT));
                break;
            case R.id.btn_delete:
                /*new AlertDialog.Builder(this)
                        .setTitle("删除")
                        .setMessage("确定要删除码？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EventBus.getDefault().post(new MessageEvent(MsgConstant.DELETE_SELECT_PRODUCT));
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create().show();*/
                EventBus.getDefault().post(new MessageEvent(MsgConstant.DELETE_SELECT_PRODUCT));
                //处理显示状态
                headerLayoutBinding.cbEdit.setChecked(false);
                break;
        }
    }

    @Override
    public void setTitleBarTitle(int position) {
        this.position = position;
    }

    @Override
    public void isLogin() {

    }

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
        if (msgEvent.getType() == MsgConstant.SELECT_CHANGE) {
            boolean isAllSelect = (boolean) msgEvent.getBean();
            if (isAllSelect) {
                mBinding.btnAll.setText("全选");
            } else {
                mBinding.btnAll.setText("取消全选");
            }
        } else if (msgEvent.getType() == MsgConstant.IS_HAS_SELECT) {
            boolean isHasSelect = (boolean) msgEvent.getBean();
            if (isHasSelect)
                mBinding.btnDelete.setVisibility(View.VISIBLE);
            else
                mBinding.btnDelete.setVisibility(View.GONE);
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String searchWord = charSequence.toString();
            if (TextUtils.isEmpty(searchWord)) {
                EventBus.getDefault().post(new MessageEvent(MsgConstant.SEARCH_COLLECT, null));
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    public void setStyleName(String styleName) {
        mBinding.cbStyle.setText(styleName);
    }
}
