package com.ogmamllpadnew.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.ScreenUtils;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.contract.BrandContract;
import com.ogmamllpadnew.databinding.BrandHeaderLayoutBinding;
import com.ogmamllpadnew.databinding.FgBrandLayoutBinding;
import com.ogmamllpadnew.presenter.BrandPresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.bean.BrandselectBrandByCategoryAndOldHouseStyleBean;
import com.shan.netlibrary.bean.BrandselectBrandCategory1Bean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 品牌
 * Created by 陈俊山 on 2018-11-02.
 */

public class BrandFragment extends BaseFragment<FgBrandLayoutBinding, BrandselectBrandByCategoryAndOldHouseStyleBean.DataBean> implements BrandContract.View {
    private BrandPresenter presenter;
    private int width;
    private int category;//一级分类ID（category1=null||category1=0查询全部，category1=-1查主推品牌，category1=381查品牌家具...）
    private int oldHouseStyleId;//老系统风格ID
    private String word;
    private boolean isSearch = false;

    public void setWord(String word) {
        this.word = word;

    }

    private void search(String word) {
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(pageNum));//第几页
        map.put("limit", String.valueOf(pageSize));//每页显示多少行
        map.put("keywords", word);//关键词搜索
        presenter.brandselectBrandByCategoryAndOldHouseStyle(map);
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setOldHouseStyleId(int oldHouseStyleId) {
        this.oldHouseStyleId = oldHouseStyleId;
    }

    @Override
    public int bindItemLayout() {
        return R.layout.fg_brand_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.GONE);
    }

    @Override
    public void onLeftClick() {
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new BrandPresenter(getActivity(), this);
        width = ScreenUtils.getScreenWidth() / 12;
        lvBinding.xrecyclerview.setPadding(width, 0, width, 0);
        setRecycViewGrid(3);
        //addHeadView();

            if (isSearch){
                clearData();
                pageNum = 1;
                search(word);
                isSearch = false;
            }else {
                brandselectBrandByCategoryAndOldHouseStyle();
            }
    }

    private BrandHeaderLayoutBinding mHeaderBinding;

    /**
     * 增加头部View
     */
    private void addHeadView() {
        mHeaderBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.brand_header_layout, lvBinding.llHeader, false);
        lvBinding.llHeader.addView(mHeaderBinding.getRoot());
    }

    private BrandselectBrandCategory1Bean category1Bean;

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.BRANDSELECTBRANDBYCATEGORYANDOLDHOUSESTYLE) {
            BrandselectBrandByCategoryAndOldHouseStyleBean bean = (BrandselectBrandByCategoryAndOldHouseStyleBean) baseBean;
            addData(bean.getData());
            isLoadingMore(bean.getCount());

        }
    }

    private List<CheckBox> checkBoxes;
    private List<BrandselectBrandCategory1Bean.DataBean> dataBeans;

    /**
     * 初始化二级分类数据
     */
    private void initTowTitle() {
        dataBeans = category1Bean.getData();
        checkBoxes = new ArrayList<>();
        for (int i = 0; i < dataBeans.size(); i++) {
            final int j = i;
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.brand_title_item_layout, mHeaderBinding.llTitle, false);
            CheckBox cb = (CheckBox) view.findViewById(R.id.rb);
            cb.setText(dataBeans.get(i).getName());
            checkBoxes.add(cb);
            if (dataBeans.get(i).isCheck()) {
                cb.setChecked(true);
            } else {
                cb.setChecked(false);
            }
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    refreshOneTileStatus(j);
                }
            });
            mHeaderBinding.llTitle.addView(view);
        }
    }

    /**
     * 刷新顶部title状态
     */
    private void refreshTitleStatus() {
        if (checkBoxes != null) {
            for (int i = 0; i < dataBeans.size(); i++) {
                checkBoxes.get(i).setChecked(dataBeans.get(i).isCheck());
            }
        }
    }

    /**
     * 刷新一级title的状态
     *
     * @param i
     */
    private void refreshOneTileStatus(int i) {
        for (int j = 0; j < checkBoxes.size(); j++) {
            if (j == i) {
                checkBoxes.get(j).setChecked(true);
            } else {
                checkBoxes.get(j).setChecked(false);
            }
        }
        category = dataBeans.get(i).getCategory1();
        onRefresh();
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
    protected void getListVewItem(FgBrandLayoutBinding binding, BrandselectBrandByCategoryAndOldHouseStyleBean.DataBean item, int position) {
        super.getListVewItem(binding, item, position);
        ImageCacheUtils.loadImage(getActivity(), item.getHeadimage(), binding.imageview);
        ImageCacheUtils.loadImage(getActivity(), item.getImage(), binding.imageview2);
    }

    @Override
    protected void onItemClick(BrandselectBrandByCategoryAndOldHouseStyleBean.DataBean bean, int position) {
        super.onItemClick(bean, position);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ID, bean.getId());
        startFragment(BranddetailsFragment.class, bundle);
    }

    /**
     * 根据分类查询商品或联合关键字查询（白名单接口）
     */
    private void brandselectBrandByCategoryAndOldHouseStyle() {
        Map<String, String> map = new HashMap<>();
        map.put("page", String.valueOf(pageNum));//第几页
        map.put("limit", String.valueOf(pageSize));//每页显示多少行
        map.put("keywords", word);//关键词搜索
        if (category != Constants.AllBean) {
            map.put("category", String.valueOf(category));
        }
        if (oldHouseStyleId != Constants.AllBean) {
            map.put("oldHouseStyleId", String.valueOf(oldHouseStyleId));
        }
        presenter.brandselectBrandByCategoryAndOldHouseStyle(map);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        brandselectBrandByCategoryAndOldHouseStyle();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (isHasNext) {
            brandselectBrandByCategoryAndOldHouseStyle();
        }
    }

    public void setIsSearch(boolean isSearch) {
        this.isSearch = isSearch;
    }
}
