package com.ogmamllpadnew.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.junshan.pub.adapter.CommonAdapter;
import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.SPUtils;
import com.junshan.pub.utils.ToastUtils;
import com.junshan.pub.widget.CommonDialog;
import com.ogmamllpadnew.MyApp;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.bean.AddressCallbackBean;
import com.ogmamllpadnew.bean.MineBean;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.constant.SpConstant;
import com.ogmamllpadnew.contract.MineContract;
import com.ogmamllpadnew.databinding.DialogQueryLayoutBinding;
import com.ogmamllpadnew.databinding.FgMineLayoutBinding;
import com.ogmamllpadnew.databinding.MineItemLayoutBinding;
import com.ogmamllpadnew.presenter.MinePresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.ogmamllpadnew.ui.activity.LoginActivity;
import com.ogmamllpadnew.widget.PhotoSelectDialog;
import com.shan.netlibrary.bean.AppaccountInfoBean;
import com.shan.netlibrary.bean.AppuploadImgBean;
import com.shan.netlibrary.bean.UseraddCustomerBean;
import com.shan.netlibrary.bean.UserselectCustomerBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpBuilder;
import com.shan.netlibrary.net.HttpCallback;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 我的
 * Created by 陈俊山 on 2018-11-02.
 */

public class MineFragment extends BaseFragment<FgMineLayoutBinding, Object> implements MineContract.View {
    private MinePresenter presenter;
    private AppaccountInfoBean infoBean;

    @Override
    public int bindLayout() {
        return R.layout.fg_mine_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        setLeftText(R.string.str_wd);
        titleBinding.btnLeft.setImageResource(R.mipmap.ic_mine_on);
    }

    @Override
    public void onLeftClick() {
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new MinePresenter(getActivity(), this);
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.APPACCOUNTINFO) {
            infoBean = (AppaccountInfoBean) baseBean;
            bindData();
            MyApp.getInstance().setInfoBean(infoBean);
            mBinding.llParent.setVisibility(View.VISIBLE);
        } else if (mTag == HttpPresenter.APPLOGOUT) {
            ToastUtils.toast(getString(R.string.str_ytcdl));
            SPUtils.put(SpConstant.TOKEN, "");
            SPUtils.put(SpConstant.PASSWORD, "");
            MyApp.getInstance().setLoginBean(null);
            MyApp.getInstance().setInfoBean(null);
            //跳转到首页
            //EventBus.getDefault().post(new MessageEvent(MsgConstant.TABHOST, 0));
            startActivity(LoginActivity.class, null);
            getActivity().finish();
        } else if (mTag == HttpPresenter.APPUPDATEPASSWORD) {
            ToastUtils.toast(getString(R.string.str_xgcg) + "，" + getString(R.string.str_qcxdl));
            presenter.updatePasswordSuccess();
            //跳转到登录页面
            SPUtils.put(SpConstant.PASSWORD, "");
            MyApp.getInstance().setLoginBean(null);
            MyApp.getInstance().setInfoBean(null);
            startActivity(LoginActivity.class, null);
            getActivity().finish();
        } else if (mTag == HttpPresenter.USERADDCUSTOMER) {
            //添加客户
            ToastUtils.toast(getString(R.string.str_tjcg));
            UseraddCustomerBean customerBean = (UseraddCustomerBean) baseBean;
            presenter.getDialogAddUser().dismiss();
            presenter.clearUserInfo();
            UserselectCustomerBean.DataBean dataBean = new UserselectCustomerBean.DataBean(customerBean.getData());
            MyApp.getInstance().setCurrentUser(dataBean);
            MyApp.getInstance().setCreateTime(System.currentTimeMillis());
            EventBus.getDefault().post(new MessageEvent(MsgConstant.SHOWUSER));
            //开始录音
            EventBus.getDefault().post(new MessageEvent(MsgConstant.STARTRECORD));
        } else if (mTag == HttpPresenter.APPEDITPERSONALDATA) {
            //修改详细资料
            if (isDismissEditDialog) {
                ToastUtils.toast(getString(R.string.str_xgcg));
                presenter.getDialogChangeDetails().dismiss();
            }
            //获取平板登录信息
            presenter.appaccountInfo(null, true);
        }
    }

    private boolean isDismissEditDialog;

    @Override
    public void setDismissEditDialog(boolean dismissEditDialog) {
        isDismissEditDialog = dismissEditDialog;
    }

    /**
     * 绑定数据
     */
    private void bindData() {
        ImageCacheUtils.loadImage(getActivity(), infoBean.getData().getPicture(), mBinding.ivHead, R.drawable.ic_defualt_head);
        mBinding.tvMobile.setText(infoBean.getData().getContactPhone());
        mBinding.tvTypeName.setText(infoBean.getData().getTypeName());

        List<MineBean> datas = new ArrayList<>();
        datas.add(new MineBean("我的收藏", "查看我的收藏", "", R.mipmap.ic_collect));
        //根据不同用户类型选择不同的功能模块
        if (infoBean.getData().getType() == 30) {
            //市代理
            datas.add(new MineBean("我的客户", "查看我的客户", "", R.mipmap.ic_user));
            datas.add(new MineBean("我的方案", "查看我的方案", "", R.mipmap.ic_plan));
            datas.add(new MineBean("我的店铺商", "查看我的店铺商", "", R.mipmap.ic_shop));
        } else if (infoBean.getData().getType() == 20) {
            //店铺商
            datas.add(new MineBean("我的商品", "查看我的商品", "", R.mipmap.ic_mine_product));
            datas.add(new MineBean("我的客户", "查看我的客户", "", R.mipmap.ic_user));
            datas.add(new MineBean("我的方案", "查看我的方案", "", R.mipmap.ic_plan));
            datas.add(new MineBean("我的设计师", "查看我的设计师", "", R.mipmap.ic_mine_design));
        } else if (infoBean.getData().getType() == 10) {
            //设计师
            datas.add(new MineBean("我的客户", "查看我的客户", "", R.mipmap.ic_user));
            datas.add(new MineBean("我的方案", "查看我的方案", "", R.mipmap.ic_plan));
        }
        mBinding.gv.setAdapter(new CommonAdapter<MineItemLayoutBinding, MineBean>(getActivity(), R.layout.mine_item_layout, datas) {
            @Override
            protected void getItem(MineItemLayoutBinding binding, MineBean bean, int position) {
                ViewGroup.LayoutParams params = binding.getRoot().getLayoutParams();
                params.height = mBinding.llRight.getHeight() / 2;
                binding.getRoot().setLayoutParams(params);
                binding.tvCollect.setText(bean.getName());
                binding.tvCollectLook.setText(bean.getSubName());
                binding.image.setImageResource(bean.getImage());
            }

            @Override
            protected void itemOnclick(MineItemLayoutBinding binding, MineBean bean, int position) {
                if (bean.getName().equals("我的收藏")) {
                    startFragment(MycollectFragment.class, null);
                } else if (bean.getName().equals("我的商品")) {
                    startFragment(ShopproductlistFragment.class, null);
                }else if (bean.getName().equals("我的客户")) {
                    startFragment(MyuserFragment.class, null);
                }else if (bean.getName().equals("我的方案")) {
                    startFragment(MyPlanFragment.class, null);
                }else if (bean.getName().equals("我的设计师")) {
                    startFragment(MydesignFragment.class, null);
                }else if (bean.getName().equals("我的店铺商")) {
                    startFragment(MyshopFragment.class, null);
                }
            }
        });
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    public void onDestroy() {
        if (presenter != null) {
            presenter.cancelAllRequest();
        }
        super.onDestroy();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.tvAddUser.setOnClickListener(this);
        mBinding.tvChangePasswd.setOnClickListener(this);
        mBinding.tvChangeDetails.setOnClickListener(this);
        mBinding.tvLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            /*case R.id.iv1:
            case R.id.tv_collect:
            case R.id.tv_collect_look:
                //startFragment(MycollectFragment.class, null);
                startFragment(ShopproductlistFragment.class, null);
                break;
            case R.id.iv2:
            case R.id.tv_plan:
            case R.id.tv_plan_look:
                startFragment(MyPlanFragment.class, null);
                break;
            case R.id.iv3:
            case R.id.tv_user:
            case R.id.tv_user_look:
                startFragment(MyuserFragment.class, null);
                break;
            case R.id.iv4:
            case R.id.tv_shop:
            case R.id.tv_shop_look:
                //根据不同用户类型选择不同的功能模块
                if (infoBean.getData().getType() == 30) {
                    //市代理
                    startFragment(MyshopFragment.class, null);
                } else if (infoBean.getData().getType() == 20) {
                    //店铺
                    startFragment(MydesignFragment.class, null);
                }

                break;*/
            case R.id.tv_add_user:
                if (MyApp.getInstance().isHasUser()) {
                    ToastUtils.toast(getString(R.string.str_hint));
                } else {
                    presenter.addUser();
                }
                break;
            case R.id.tv_change_passwd:
                presenter.changePasswd();
                break;
            case R.id.tv_change_details:
                presenter.changeDetails();
                break;
            case R.id.tv_logout:
                if (MyApp.getInstance().getCurrentUser() != null) {
                    ToastUtils.toast(getString(R.string.str_hint4));
                    return;
                }
                showLogout();
                break;
        }
    }

    /**
     * 重置密码
     */
    private CommonDialog dialogReset;
    private DialogQueryLayoutBinding queryLayoutBinding;

    private void showLogout() {
        if (dialogReset == null) {
            dialogReset = new CommonDialog.Builder(getActivity())
                    .setWidth(0.3f)
                    .setHeight(0.3f)
                    .setResId(R.layout.dialog_query_layout)
                    .build();
            queryLayoutBinding = (DialogQueryLayoutBinding) dialogReset.getBinding();
            queryLayoutBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogReset.dismiss();
                }
            });
            queryLayoutBinding.btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logout();
                }
            });
            queryLayoutBinding.tvHint.setText(getString(R.string.str_tcdl));
            queryLayoutBinding.tvMsg.setText(getString(R.string.str_hint5));
        }
        dialogReset.show();
    }


    /**
     * 退出登录
     */
    private void logout() {
        presenter.applogout(null);
    }

    /**
     * 选择照片
     */
    @Override
    public void selectPicture() {
        PhotoSelectDialog.open(this);
    }

    @Override
    public void photoResult(String path) {
        super.photoResult(path);
        if (TextUtils.isEmpty(path)) {
            ToastUtils.toast(getString(R.string.str_scsb));
            return;
        } else if (path.contains(".gif")) {
            ToastUtils.toast(getString(R.string.str_bnscdt));
            return;
        }
        /*CompressAndCrop compressAndCrop = new CompressAndCrop(getActivity()) {
            @Override
            public void compressOncuccess(File file) {
                super.compressOncuccess(file);
                upFilePath = file.getAbsolutePath();
                upLoadFile();
            }

            @Override
            public void compressOnError(Throwable e) {
                super.compressOnError(e);
            }
        };
        compressAndCrop.conpress(path);*/
        appuploadImg(path);
    }

    private String fileName;

    /**
     * 上传图片
     *
     * @param filePath
     */
    public void appuploadImg(String filePath) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("application/otcet-stream"), new File(filePath));
        HttpCallback callback = new HttpCallback<AppuploadImgBean>(getActivity(), this, false) {
            @Override
            protected void onSuccess(AppuploadImgBean baseBean) {
                fileName = baseBean.getMessage();
                presenter.refreshHeadImage(baseBean.getData().getPath());
                appeditPersonalData();
            }

            @Override
            protected void onFailure(Throwable e) {
            }
        };
        HttpBuilder.getInstance().execute(HttpBuilder.httpService.appuploadImg(requestFile), callback);
    }

    private void appeditPersonalData() {
        Map<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(fileName)) {
            map.put("picture", fileName);
        }
        presenter.appeditPersonalData(map);
    }

    @Override
    public void toAddress(int type, String id) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.TYPE, type);
        bundle.putString(Constants.ID, id);
        startActivity(AddressActivity.class, bundle);
    }

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
        if (msgEvent.getType() == MsgConstant.ADDRESS) {
            AddressCallbackBean addressBean = (AddressCallbackBean) msgEvent.getBean();
            presenter.setAddressBean(addressBean);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (infoBean == null) {
            //获取平板登录信息
            presenter.appaccountInfo(null, false);
        } else {
            bindData();
        }
    }
}
