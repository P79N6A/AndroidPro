package com.ogmamllpadnew.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.ui.activity.CommonActivity;
import com.junshan.pub.utils.ImageCacheUtils;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.adapter.HorizontalPagerAdapter;
import com.ogmamllpadnew.adapter.ScomboAdapter;
import com.ogmamllpadnew.bean.ScomboSingle;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.contract.ScomboDateilContract;
import com.ogmamllpadnew.databinding.FgScomboDetailLayoutBinding;
import com.ogmamllpadnew.presenter.ScomboDateilPresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.ogmamllpadnew.utils.Utils;
import com.shan.netlibrary.bean.SelectComboBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/2/27.
 */

public class ScomboDateilFragment extends BaseFragment<FgScomboDetailLayoutBinding,Object> implements ScomboDateilContract.View{
    private ScomboDateilPresenter presenter;
    @Override
    public int bindLayout() {
        return R.layout.fg_scombo_detail_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.tvLeft.setText("套餐详情");
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new ScomboDateilPresenter(getContext(),this);
        int id = getActivity().getIntent().getIntExtra(Constants.ID, 0);
        mBinding.scll.smoothScrollTo(0,0);
        getScomboDetail(id);
    }

    private void getScomboDetail(int id) {
        Map<String,String> params = new HashMap<>();
        params.put("id",String.valueOf(id));
        presenter.selectCombo(params);
    }
    SelectComboBean bean;
    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.SELECTCOMBO){

            bean = (SelectComboBean) baseBean;
            bindData(bean);
        }
    }

    private void bindData(SelectComboBean bean) {
        bindScomboInfo(bean.getData());  //绑定套餐头部的描述信息
        bindrich(bean.getData());        //绑定丰富区数据
        bindQitaRoom(bean.getData());

        List<SelectComboBean.RoomBean> room = bean.getData().getRoom();
        for (int i = 0 ; i < room.size() ; i++){
            room.get(i).setMainCombo(true);
        }
        bean.getData().getRich().get(0).setChecked(true);
        onMsgEvent(new MessageEvent(MsgConstant.SHOWSCOMBO_NUM));
    }

    private void bindQitaRoom(SelectComboBean.DataBean data) {
        Log.e("TAG","--------------"+data.getRoom().size());
        if (data.getRoom() == null || data.getRoom().isEmpty()){
            mBinding.lvQita.setVisibility(View.GONE);
            return;
        }else {
            mBinding.lvQita.setVisibility(View.VISIBLE);
        }
        mBinding.lvQita.setAdapter(new ScomboAdapter(getActivity(),data.getRoom()));
    }

    private void bindScomboInfo(final SelectComboBean.DataBean bean) {
        List<Utils.LibraryObject> data = new ArrayList<>();
        for (int i = 0; i < bean.getDetailsImage().size(); i++) {
            data.add(new Utils.LibraryObject(bean.getDetailsImage().get(i), ""));
        }
        HorizontalPagerAdapter adapter = new HorizontalPagerAdapter(getContext(),data){
            @Override
            public void showImage(List<Utils.LibraryObject> libraries, int position) {
                super.showImage(libraries, position);
                Bundle bundle = new Bundle();
                bundle.putBoolean(CommonActivity.ISFULLSCREEN, true);
                bundle.putBoolean(CommonActivity.ISSLIDINGCLOSE, false);
                bundle.putStringArrayList(Constants.LSIT, (ArrayList<String>) bean.getDetailsImage());
                startFragment(PicturelookFragment.class, bundle);
            }
        };
        mBinding.hicvp.setAdapter(adapter);
        mBinding.tvScomboName.setText(bean.getComboName());
        mBinding.tvScomboInfo.setText(bean.getIntroduce());
        mBinding.tvScomboSize.setText(bean.getComboArea()+"m²");
    }
    private void bindrich(final SelectComboBean.DataBean bean) {
        final List<SelectComboBean.ListBean> rich = bean.getRich();
        if (rich == null || rich.isEmpty()){
            mBinding.llFengfu.setVisibility(View.GONE);
            mBinding.hlFengfu.setVisibility(View.GONE);
            mBinding.viewFf.setVisibility(View.GONE);
            return;
        }else {
            mBinding.llFengfu.setVisibility(View.VISIBLE);
            mBinding.hlFengfu.setVisibility(View.VISIBLE);
            mBinding.viewFf.setVisibility(View.VISIBLE);
        }
        mBinding.llFengfu.removeAllViews();
        for (int i = 0 ; i < rich.size(); i++){
            final View fufengView = LayoutInflater.from(getActivity()).inflate(R.layout.item_fengfu_layout,mBinding.llFengfu,false);
//            TextView tv_name = (TextView) fufengView.findViewById(R.id.tv_name);
//            tv_name.setText(rich.get(i).getProductName());
            ViewHolder holder = new ViewHolder(fufengView);

            holder.setData(rich.get(i));
            mBinding.llFengfu.addView(fufengView);
            CheckBox cb = (CheckBox) fufengView.findViewById(R.id.cb_select);
            if (i == 0){
                cb.setChecked(true);
            }else {
                cb.setChecked(false);
            }
            final int finalI = i;
            if (i == 0){
                fufengView.findViewById(R.id.ll_fengfu).setBackgroundResource(R.drawable.shape_item_selector);
                rich.get(finalI).setChecked(true);
            }else {
                fufengView.findViewById(R.id.ll_fengfu).setBackgroundResource(R.drawable.shape_fufeng_item_selector);
                rich.get(finalI).setChecked(false);
            }
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        fufengView.findViewById(R.id.ll_fengfu).setBackgroundResource(R.drawable.shape_item_selector);

                    }else {
                        fufengView.findViewById(R.id.ll_fengfu).setBackgroundResource(R.drawable.shape_fufeng_item_selector);
                    }
                    rich.get(finalI).setChecked(b);
                    initRichSelector(bean);
                    onMsgEvent(new MessageEvent(MsgConstant.SHOWSCOMBO_NUM));
                }
            });
        }
        initRichSelector(bean);
    }

    private void initRichSelector(SelectComboBean.DataBean bean) {
        final List<SelectComboBean.ListBean> rich = bean.getRich();
        int sum = 0;
        for (int i = 0 ; i < rich.size() ; i++){
            if (rich.get(i).isChecked()){
                sum++;
            }
        }
        mBinding.tvNumber.setText("已选择"+sum+"件商品");
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    private static class ViewHolder implements View.OnClickListener {
        TextView tv_name,tv_number,tv_price,tv_code,tv_guige,tv_color;
        ImageView iv_headImg;
        Context context;
        private SelectComboBean.ListBean listbean;

        public ViewHolder(View view){
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_number = (TextView) view.findViewById(R.id.tv_number);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            tv_code = (TextView) view.findViewById(R.id.tv_code);
            tv_guige = (TextView) view.findViewById(R.id.tv_guige);
            tv_color = (TextView) view.findViewById(R.id.tv_color);
            iv_headImg = (ImageView) view.findViewById(R.id.iv_headImg);
            context = view.getContext();
            view.setOnClickListener(this);
        }

        public void setData(SelectComboBean.ListBean listBean) {
            this.listbean = listBean;
            Log.e("TAG----",listBean.toString());
            tv_name.setText(listBean.getProductName());
            ImageCacheUtils.loadImage(context,listBean.getHeadImage(),iv_headImg);
            tv_code.setText("欧工编号："+listBean.getId());
            tv_number.setText("X"+listBean.getNumber());
            tv_price.setText("￥"+listBean.getSellPrice());
            tv_guige.setText("规格："+listBean.getSpec());
            tv_color.setText("颜色："+listBean.getColor());
        }

        @Override
        public void onClick(View view) {
            if (listbean != null){
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.ID,listbean.getProductId());
                startFragment(ProductdetailsFragment.class,bundle);
            }
        }
        public void startFragment(Class<?> cls, Bundle bundle) {
            Intent intent = new Intent(context, CommonActivity.class);
            intent.putExtra(CommonActivity.FRAGMENT_CLASS, cls);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            context.startActivity(intent);
        }
    }

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
        if (msgEvent.getType() == MsgConstant.SHOWSCOMBO_NUM){
            int sum = 0;
            double price = 0;
            if (bean != null){
                //叠加丰富区
                List<SelectComboBean.ListBean> rich = bean.getData().getRich();
                for (int i = 0 ; i < rich.size() ; i++){
                    SelectComboBean.ListBean listBean = rich.get(i);
                    if (listBean.isChecked()){
                        sum += listBean.getNumber();
                        price += listBean.getSellPrice() * listBean.getNumber();
                    }
                }

                //叠加其他区数量
                List<SelectComboBean.RoomBean> room = bean.getData().getRoom();
                for (int i = 0 ; i < room.size() ; i++){
                    SelectComboBean.RoomBean roomBean = room.get(i);
                    if (roomBean.isMainCombo()){
                        for (int j = 0 ; j < roomBean.getMainCombo().size() ; j++){
                            SelectComboBean.ListBean listBean = roomBean.getMainCombo().get(j);
                            sum += listBean.getNumber();
                            price += listBean.getSellPrice() * listBean.getNumber();
                        }
                    }
                    if (roomBean.isViceCombo()){
                        for (int j = 0 ; j < roomBean.getViceCombo().size() ; j++){
                            SelectComboBean.ListBean listBean = roomBean.getViceCombo().get(j);
                            sum += listBean.getNumber();
                            price += listBean.getSellPrice() * listBean.getNumber();
                        }
                    }
                }
                addFontSpan(sum,price);
            }
        }
    }

    private void addFontSpan(int sum,double price) {
        SpannableString spanString = new SpannableString("已选"+sum+"件");
        AbsoluteSizeSpan span = new AbsoluteSizeSpan(50);
        spanString.setSpan(span, 2, 2 + String.valueOf(sum).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBinding.tvSumNumber.setText(spanString);

        SpannableString string = new SpannableString("套餐优惠价：￥"+price);
        string.setSpan(span, 6, ("套餐优惠价：￥"+price).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBinding.tvSumPrice.setText(string);
    }
}
