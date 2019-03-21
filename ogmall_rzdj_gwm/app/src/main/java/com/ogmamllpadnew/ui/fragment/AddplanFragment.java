package com.ogmamllpadnew.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.junshan.pub.adapter.CommonAdapter;
import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.JitterUtils;
import com.junshan.pub.utils.SoftKeyBoardUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.bean.SelectBean;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.contract.AddplanContract;
import com.ogmamllpadnew.databinding.AddPlanPictureLayoutBinding;
import com.ogmamllpadnew.databinding.FgAddplanLayoutBinding;
import com.ogmamllpadnew.presenter.AddplanPresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.ogmamllpadnew.widget.PhotoSelectDialog;
import com.shan.netlibrary.bean.AppuploadImgBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpBuilder;
import com.shan.netlibrary.net.HttpCallback;
import com.shan.netlibrary.net.HttpPresenter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 添加方案
 * Created by 陈俊山 on 2018-11-12.
 */

public class AddplanFragment extends BaseFragment<FgAddplanLayoutBinding, Object> implements AddplanContract.View {
    private AddplanPresenter presenter;
    private boolean isSelectCoverPicture;//是否为封面照片

    @Override
    public int bindLayout() {
        return R.layout.fg_addplan_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        setLeftText(R.string.str_tjfa);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new AddplanPresenter(getActivity(), this);
        //设置默认添加照片
        if (images == null) {
            images = new ArrayList<>();
            images.add(new AppuploadImgBean());
        }
        bindDetailsPicture();
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.PLANADDPLAN) {
            ToastUtils.toast(getString(R.string.str_bccg));
            getActivity().finish();
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
        mBinding.btnCancel.setOnClickListener(this);
        mBinding.btnOk.setOnClickListener(this);
        mBinding.ivFmzp.setOnClickListener(this);
        mBinding.tvSelectHx.setOnClickListener(this);
        mBinding.tvSlelectStyle.setOnClickListener(this);
        //键盘监听
        SoftKeyBoardUtils.setListener(getActivity(), new SoftKeyBoardUtils.OnSoftKeyBoardChangeListener() {
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
        Bundle bundle = null;
        switch (view.getId()) {
            case R.id.btn_cancel:
                getActivity().finish();
                break;
            case R.id.btn_ok:
                addPlan();
                break;
            case R.id.iv_fmzp:
                isSelectCoverPicture = true;
                PhotoSelectDialog.open(this);
                break;
            case R.id.tv_select_hx:
                bundle = new Bundle();
                bundle.putInt(Constants.TYPE, 1);
                startActivity(SelectActivity.class, bundle);
                break;
            case R.id.tv_slelect_style:
                bundle = new Bundle();
                bundle.putInt(Constants.TYPE, 2);
                startActivity(SelectActivity.class, bundle);
                break;
        }
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
        appuploadImg(path);
    }

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
                if (isSelectCoverPicture) {
                    //封面照片
                    headImage = baseBean.getMessage();
                    ImageCacheUtils.loadImage(getActivity(), baseBean.getData().getPath(), mBinding.ivFmzp);
                } else {
                    images.add(images.size() - 1, baseBean);
                    bindDetailsPicture();
                }

            }

            @Override
            protected void onFailure(Throwable e) {
            }
        };
        HttpBuilder.getInstance().execute(HttpBuilder.httpService.appuploadImg(requestFile), callback);
    }

    private List<AppuploadImgBean> images;
    private CommonAdapter adapter;

    //详情照片
    private void bindDetailsPicture() {
        if (adapter == null) {
            adapter = new CommonAdapter<AddPlanPictureLayoutBinding, AppuploadImgBean>(getActivity(), R.layout.add_plan_picture_layout, images) {
                @Override
                protected void getItem(AddPlanPictureLayoutBinding binding, AppuploadImgBean bean, final int position) {
                    if (position == images.size() - 1) {
                        binding.tvHint.setVisibility(View.VISIBLE);
                        binding.ivDelete.setVisibility(View.INVISIBLE);
                        Glide.with(getActivity()).load(R.mipmap.ic_upload_image).into(binding.image);
                    } else {
                        binding.tvHint.setVisibility(View.INVISIBLE);
                        binding.ivDelete.setVisibility(View.VISIBLE);
                        ImageCacheUtils.loadImage(getActivity(), bean.getData().getPath(), binding.image);
                    }
                    if (position == 8) {
                        binding.getRoot().setVisibility(View.GONE);
                    } else {
                        binding.getRoot().setVisibility(View.VISIBLE);
                    }
                    /**
                     * 取消选中
                     */
                    binding.ivDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            images.remove(position);
                            adapter.updata(images);
                        }
                    });
                }

                @Override
                protected void itemOnclick(AddPlanPictureLayoutBinding binding, AppuploadImgBean bean, int position) {
                    if (binding.getRoot().getVisibility() == View.VISIBLE && position == images.size() - 1) {
                        isSelectCoverPicture = false;
                        PhotoSelectDialog.open(AddplanFragment.this);
                    }
                }
            };
            mBinding.gvXqzp.setAdapter(adapter);
        } else {
            adapter.updata(images);
        }
    }

    private String headImage;//封面照片
    private String houseTypeId;//户型ID
    private String houseStyleId;//风格ID
    private String detailsImages = "";//详情图片(格式：picName,picName,picName...)

    /**
     * 添加方案
     */
    private void addPlan() {
        String planName = mBinding.etPlanTitle.getText().toString();//方案名称
        String roomType = mBinding.etHxType.getText().toString();//房间类型，例如，客厅，厨房，主人卧室...
        String area = mBinding.etArea.getText().toString();//面积
        String vrurl = mBinding.etUrl.getText().toString();//VR的url
        if (TextUtils.isEmpty(planName)) {
            JitterUtils.start(mBinding.etPlanTitle);
            return;
        }
        if (TextUtils.isEmpty(headImage)) {
            ToastUtils.toast(getString(R.string.str_qxzfmzp));
            return;
        }
        if (TextUtils.isEmpty(houseTypeId)) {
            ToastUtils.toast(getString(R.string.str_qxzhx));
            return;
        }
        if (TextUtils.isEmpty(houseStyleId)) {
            ToastUtils.toast(getString(R.string.str_qxzfg));
            return;
        }
        if (images != null && images.size() > 1) {
            detailsImages = "";
            if (images.size() == 2) {
                detailsImages = images.get(0).getMessage();
            } else {
                for (int i = 0; i < images.size() - 1; i++) {
                    if (i == images.size() - 2) {
                        detailsImages = detailsImages + images.get(i).getMessage();
                    } else {
                        detailsImages = detailsImages + images.get(i).getMessage() + ",";
                    }
                }
            }
        }
        Map<String, String> map = new HashMap<>();
        map.put("planName", planName);
        map.put("headImage", headImage);
        map.put("houseTypeId", houseTypeId);
        map.put("houseStyleId", houseStyleId);
        map.put("roomType", roomType);
        map.put("area", area);
        map.put("vrurl", vrurl);
        map.put("detailsImages", detailsImages);
        presenter.planaddPlan(map);
    }

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
        if (msgEvent.getType() == MsgConstant.SELELCT) {
            SelectBean selectBean = (SelectBean) msgEvent.getBean();
            if (selectBean != null) {
                if (selectBean.getType() == 1) {
                    //户型
                    houseTypeId = selectBean.getId();
                    mBinding.tvSelectHx.setText(selectBean.getName());
                } else if (selectBean.getType() == 2) {
                    //风格
                    houseStyleId = selectBean.getId();
                    mBinding.tvSlelectStyle.setText(selectBean.getName());
                }
            }
        }
    }
}
