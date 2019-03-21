package com.ogmallpad.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.google.gson.Gson;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.SPUtils;
import com.ogmallpad.R;
import com.ogmallpad.constant.Constants;
import com.ogmallpad.constant.SpConstant;
import com.ogmallpad.contract.Home1Contract;
import com.ogmallpad.contract.HomeContract;
import com.ogmallpad.databinding.Home1LayoutBinding;
import com.ogmallpad.presenter.HomePresenter;
import com.ogmallpad.ui.BaseFragment;
import com.shan.netlibrary.bean.HasProductBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/2/14.
 */

public class Home1Fragment extends BaseFragment<Home1LayoutBinding,Object> implements HomeContract.View{
    private HomePresenter presenter;

    @Override
    public int bindLayout() {
        return R.layout.home1_layout;
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        List<String> urls = new ArrayList<>();
        presenter = new HomePresenter(getContext(),this);
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550123316516&di=982a842589a3fcbb9fbe80d9e5c05361&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F7cee9a66be9f42019b05064a0c48dc73c037c85a.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550123316516&di=982a842589a3fcbb9fbe80d9e5c05361&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F7cee9a66be9f42019b05064a0c48dc73c037c85a.jpg");
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550123316516&di=982a842589a3fcbb9fbe80d9e5c05361&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F7cee9a66be9f42019b05064a0c48dc73c037c85a.jpg");
        mBinding.convenientBanner.setPages(new CBViewHolderCreator<ViewPagerAdapter>() {
            @Override
            public ViewPagerAdapter createHolder() {
                return new ViewPagerAdapter();
            }
        },urls)
                .setPageIndicator(new int[]{R.mipmap.ic_point_nomal, R.mipmap.ic_point_focused})
                .startTurning(3000);

//        hasProduct();
        initBrandClip();
    }

    private void hasProduct() {
        Map<String,String> params = new HashMap<>();
        params.put("categoryId","382");
        presenter.hasProduct(params);
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {

    }

    private void initBrandClip() {
        final List<HasProductBean.DataBean> data = getData();
        for (int i = 0 ; i < data.size() ; i++){
            final int j = i;
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.clpl_layout, mBinding.llClpl, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv);
            TextView textView = (TextView) view.findViewById(R.id.tv);
            imageView.setImageResource((Integer) data.get(i).getImage());
            textView.setText(data.get(i).getName());
            mBinding.llClpl.addView(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //跳转到商品列表
                    toProductList(data.get(j));
                }
            });
        }
    }

    private List<HasProductBean.DataBean> getData() {
        List<HasProductBean.DataBean> data = new ArrayList<>();
        HasProductBean.DataBean bean = new HasProductBean.DataBean();
        bean.setImage(R.mipmap.bed);
        bean.setId(383);
        bean.parentId = 382;
        bean.setName("床");
        data.add(bean);

        HasProductBean.DataBean bean1 = new HasProductBean.DataBean();
        bean1.setImage(R.mipmap.tv_cabinet);
        bean1.setId(397);
        bean1.parentId = 393;
        bean1.setName("电视柜");
        data.add(bean1);

        HasProductBean.DataBean bean2 = new HasProductBean.DataBean();
        bean2.setImage(R.mipmap.sofa);
        bean2.setId(395);
        bean2.parentId = 393;
        bean2.setName("沙发");
        data.add(bean2);

        HasProductBean.DataBean bean3 = new HasProductBean.DataBean();
        bean3.setImage(R.mipmap.dougui);
        bean3.setId(391);
        bean3.parentId = 393;
        bean3.setName("斗柜");
        data.add(bean3);

        HasProductBean.DataBean bean4 = new HasProductBean.DataBean();
        bean4.setImage(R.mipmap.shuzhuo);
        bean4.parentId = 409;
        bean4.setId(410);
        bean4.setName("书桌");
        data.add(bean4);

        HasProductBean.DataBean bean5 = new HasProductBean.DataBean();
        bean5.setImage(R.mipmap.shugui);
        bean5.parentId = 409;
        bean5.setId(412);
        bean5.setName("书柜");
        data.add(bean5);

        HasProductBean.DataBean bean6 = new HasProductBean.DataBean();
        bean6.setImage(R.mipmap.shuzhangtai);
        bean6.setId(390);
        bean6.parentId = 382;
        bean6.setName("梳妆台");
        data.add(bean6);
        //爆款专区
        HasProductBean.DataBean bean7 = new HasProductBean.DataBean();
        bean7.setImage(R.mipmap.taideng);
        bean7.setId(449);
        bean7.parentId = 444;
        bean7.setName("台灯");
        data.add(bean7);

        HasProductBean.DataBean bean8 = new HasProductBean.DataBean();
        bean8.setImage(R.mipmap.yizi);
        bean8.setId(388);
        bean8.parentId = 382;
        bean8.setName("椅子");
        data.add(bean8);

        HasProductBean.DataBean bean9 = new HasProductBean.DataBean();
        bean9.setImage(R.mipmap.yigui);
        bean9.setId(385);
        bean9.parentId = 382;
        bean9.setName("衣柜");
        data.add(bean9);
        return data;
    }

    private void toProductList(HasProductBean.DataBean bean) {
        if (bean.getId() == 449){
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.ID, 443);
            bundle.putInt(Constants.CATEGORY2ID, bean.parentId);
            bundle.putInt(Constants.CATEGORY3ID, bean.getId());
            bundle.putString(Constants.TITLE, "爆款专区");
            startFragment(BrandproductTabFragment.class, bundle);
            return;
        }
        //跳转到商品列表
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ID, 381);
        bundle.putInt(Constants.CATEGORY2ID, bean.parentId);
        bundle.putInt(Constants.CATEGORY3ID, bean.getId());
        bundle.putString(Constants.TITLE, "品牌家具");
        startFragment(BrandproductTabFragment.class, bundle);

    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    static class ViewPagerAdapter implements Holder<String> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.home_viewpager_item_layout, null);
            mImageView = (ImageView) view.findViewById(R.id.imageView);
            return view;
        }
        @Override
        public void UpdateUI(Context context, int position, String data) {
            ImageCacheUtils.loadImage(context, data, mImageView);
        }
    }
}
