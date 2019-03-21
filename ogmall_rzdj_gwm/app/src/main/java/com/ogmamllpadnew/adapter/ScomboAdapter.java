package com.ogmamllpadnew.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.ui.activity.CommonActivity;
import com.junshan.pub.utils.ImageCacheUtils;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.bean.ScomboSingle;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.contract.BaginrightContract;
import com.ogmamllpadnew.ui.fragment.ProductdetailsFragment;
import com.shan.netlibrary.bean.SelectComboBean;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2019/2/28.
 */

public class ScomboAdapter extends BaseAdapter {
    private List<SelectComboBean.RoomBean> roomBeans;
    private Context context;

    public ScomboAdapter(Context context, List<SelectComboBean.RoomBean> roomBeans){
        this.context = context;
        this.roomBeans = roomBeans;
    }
    @Override
    public int getCount() {
        if (roomBeans != null && !roomBeans.isEmpty())
            return roomBeans.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return roomBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_qitaqu_layout,null,false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.setData(roomBeans.get(i));
        viewHolder.setListener();
        return view;
    }

    static class ViewHolder{
        TextView tv_name,tv_zhuName,tv_zhu_price,tv_fu_name,tv_fu_price,tv_number;
        LinearLayout ll_fu_info;
        LinearLayout ll_zhu_info;
        CheckBox cb_select,cb_select_all;
        LinearLayout ll_zhu,ll_fu;
        Context context;
        private SelectComboBean.RoomBean roomBean;

        public ViewHolder(View view){
            context = view.getContext();
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_zhuName = (TextView) view.findViewById(R.id.tv_zhuName);
            tv_zhu_price = (TextView) view.findViewById(R.id.tv_zhu_price);
            tv_fu_price = (TextView) view.findViewById(R.id.tv_fu_price);
            tv_fu_name = (TextView) view.findViewById(R.id.tv_fu_name);
            ll_zhu_info = (LinearLayout) view.findViewById(R.id.ll_zhu_info);
            ll_fu_info = (LinearLayout) view.findViewById(R.id.ll_fu_info);
            cb_select = (CheckBox) view.findViewById(R.id.cb_select);
            cb_select_all = (CheckBox) view.findViewById(R.id.cb_select_all);
            ll_zhu = (LinearLayout) view.findViewById(R.id.ll_zhu);
            ll_fu = (LinearLayout) view.findViewById(R.id.ll_fu);
            tv_number = (TextView) view.findViewById(R.id.tv_number);
        }

        public void setData(SelectComboBean.RoomBean roomBean) {
            this.roomBean = roomBean;
            tv_name.setText(roomBean.getRoomName());
            tv_zhuName.setText(roomBean.getRoomName()+"1");
            tv_fu_name.setText(roomBean.getRoomName()+"2");

            List<SelectComboBean.ListBean> mainCombo = roomBean.getMainCombo();
            final List<SelectComboBean.ListBean> viceCombo = roomBean.getViceCombo();
            ll_zhu_info.removeAllViews();
            double zhuprice = 0.0;
            for (int i = 0 ; i < mainCombo.size() ; i++) {
                View child = LayoutInflater.from(context).inflate(R.layout.item_scombo_room_layout, ll_zhu_info, false);
                ChildViewHolder childViewHolder = new ChildViewHolder(child);
                childViewHolder.setData(mainCombo.get(i));
                ll_zhu_info.addView(child);
                zhuprice += mainCombo.get(i).getSellPrice() * mainCombo.get(i).getNumber();
            }
            tv_zhu_price.setText("优惠价:￥"+zhuprice);

            ll_fu_info.removeAllViews();
            double fuprice = 0.0;
            for (int i = 0 ; i < viceCombo.size() ; i++) {
                View child = LayoutInflater.from(context).inflate(R.layout.item_scombo_room_layout, ll_fu_info, false);
                ChildViewHolder childViewHolder = new ChildViewHolder(child);
                childViewHolder.setData(viceCombo.get(i));
                ll_fu_info.addView(child);
                fuprice += viceCombo.get(i).getSellPrice() * viceCombo.get(i).getNumber();
            }
            tv_fu_price.setText("优惠价:￥"+fuprice);
            cb_select.setChecked(true);
            ll_zhu.setBackgroundResource(R.drawable.shape_item_selector);
            initSelector();

        }

        public void setListener() {
            cb_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        roomBean.setMainCombo(true);
                        ll_zhu.setBackgroundResource(R.drawable.shape_item_selector);
                    }else {
                        ll_zhu.setBackgroundResource(R.drawable.shape_fufeng_item_selector);
                        roomBean.setMainCombo(false);
                    }
                    initSelector();
                }


            });
            cb_select_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        roomBean.setViceCombo(true);
                        ll_fu.setBackgroundResource(R.drawable.shape_item_selector);
                    }else {
                        ll_fu.setBackgroundResource(R.drawable.shape_fufeng_item_selector);
                        roomBean.setViceCombo(false);
                    }
                    initSelector();
                }
            });
        }

        private void initSelector() {
            int sum = 0;
            if (cb_select_all.isChecked()){
                sum += ll_zhu_info.getChildCount();
            }
            if (cb_select.isChecked()){
                sum += ll_fu_info.getChildCount();
            }
            tv_number.setText("已选择"+sum+"件商品");
            EventBus.getDefault().post(new MessageEvent(MsgConstant.SHOWSCOMBO_NUM));
        }
    }
    private static class ChildViewHolder implements View.OnClickListener {
        ImageView iv_headImg;
        TextView tv_name;
        TextView tv_number;
        TextView tv_code;
        TextView tv_guige;
        TextView tv_color;
        Context context;
        private SelectComboBean.ListBean listBean;

        public ChildViewHolder(View view){
            iv_headImg = (ImageView) view.findViewById(R.id.iv_headImg);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_number = (TextView) view.findViewById(R.id.tv_number);
            tv_code = (TextView) view.findViewById(R.id.tv_code);
            tv_guige = (TextView) view.findViewById(R.id.tv_guige);
            tv_color = (TextView) view.findViewById(R.id.tv_color);
            context = view.getContext();
            view.setOnClickListener(this);
        }

        public void setData(SelectComboBean.ListBean listBean) {
            this.listBean = listBean;
            ImageCacheUtils.loadImage(context,listBean.getHeadImage(),iv_headImg);
            tv_name.setText(listBean.getProductName());
            tv_number.setText("X"+listBean.getNumber());
            tv_code.setText("欧工编号："+listBean.getId());
            tv_guige.setText("规格："+listBean.getSpec());
            tv_color.setText("颜色："+listBean.getColor());
        }

        @Override
        public void onClick(View view) {
            if (listBean != null){
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.ID, listBean.getProductId());
                startFragment(ProductdetailsFragment.class, bundle);
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
}
