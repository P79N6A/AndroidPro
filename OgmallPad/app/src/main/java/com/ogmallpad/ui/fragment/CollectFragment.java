package com.ogmallpad.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.ui.activity.CommonActivity;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.SoftKeyBoardUtils;
import com.junshan.pub.utils.ToastUtils;
import com.ogmallpad.R;
import com.ogmallpad.bean.ProductsBean;
import com.ogmallpad.constant.Constants;
import com.ogmallpad.constant.MsgConstant;
import com.ogmallpad.contract.CollectContract;
import com.ogmallpad.databinding.FgCollectItemLayoutBinding;
import com.ogmallpad.presenter.CollectPresenter;
import com.ogmallpad.ui.BaseFragment;
import com.shan.netlibrary.bean.CentergetcollectsBean;
import com.shan.netlibrary.net.BaseBean;
import com.shan.netlibrary.net.HttpPresenter;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

/**
 * 商品收藏
 * Created by chenjunshan on 2018-09-27.
 */

public class CollectFragment extends BaseFragment<FgCollectItemLayoutBinding, CentergetcollectsBean.DataBean.ListBean> implements CollectContract.View {
    private CollectPresenter presenter;
    private boolean isShowEdit;
    private String searchWord;//搜索关键词
    private String minPrice;//最低价
    private String maxPrice;//最高价
    private String styleId;

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
        onRefresh(true);
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
        onRefresh(true);
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
        onRefresh(true);
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
        onRefresh(true);
    }

    /**
     * 是否显示编辑
     *
     * @param isShow
     */
    public void isShowEdit(boolean isShow) {
        this.isShowEdit = isShow;
        adapter.notifyDataSetChanged();
    }

    @Override
    public int bindItemLayout() {
        return R.layout.fg_collect_item_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.getRoot().setVisibility(View.VISIBLE);
        titleBinding.tvLeft.setText("我的收藏");
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new CollectPresenter(getActivity(), this);
        setRecycViewGrid(2);
        //获取收藏的商品
        //centergetCollects(false);
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {
        if (mTag == HttpPresenter.CENTERGETCOLLECTS) {
            //商定商品数据
            CentergetcollectsBean collectsBean = (CentergetcollectsBean) baseBean;
            isLoadingMore(collectsBean.getData().getPageResult().getTotalPage());
            List<CentergetcollectsBean.DataBean.ListBean> list = collectsBean.getData().getList();
            addData(list);
        } else if (mTag == HttpPresenter.CENTERDELETECOLLECTS) {
            ToastUtils.toast(getString(R.string.str_sccg2));
            onRefresh();
        }
    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    protected void getListVewItem(final FgCollectItemLayoutBinding binding, final CentergetcollectsBean.DataBean.ListBean item, final int position) {
        super.getListVewItem(binding, item, position);
        ImageCacheUtils.loadImage(getActivity(), item.getImage(), binding.iv);
        binding.tvTitle.setText(item.getName());
        binding.tvPrice.setText("¥" + item.getPrice());
        binding.rbEdit.setChecked(item.isCheckDelete());
        if (isShowEdit) {
            binding.rbEdit.setVisibility(View.VISIBLE);
        } else {
            binding.rbEdit.setVisibility(View.INVISIBLE);
        }
        binding.rbEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setCheckDelete(!item.isCheckDelete());
                adapter.notifyDataSetChanged();
                if (isAllSelect()) {
                    EventBus.getDefault().post(new MessageEvent(MsgConstant.SELECT_CHANGE, false));
                } else {
                    EventBus.getDefault().post(new MessageEvent(MsgConstant.SELECT_CHANGE, true));
                }

                if (isHasSelect()) {
                    EventBus.getDefault().post(new MessageEvent(MsgConstant.IS_HAS_SELECT, true));
                } else {
                    EventBus.getDefault().post(new MessageEvent(MsgConstant.IS_HAS_SELECT, false));
                }
            }
        });
        binding.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //toProductsFragment(position);
                Bundle bundle = new Bundle();
                bundle.putString(Constants.ID, String.valueOf(item.getId()));
                startFragment(ProductdetailsFragment.class, bundle);
            }
        });
    }

    /**
     * 跳转到商品预览
     *
     * @param position
     */
    private void toProductsFragment(int position) {
        SoftKeyBoardUtils.closeKeybord(lvBinding.getRoot(), getActivity());
        //presenter.lookImage(bean.getImage());
        ProductsBean productsBean = new ProductsBean();
        for (int i = 0; i < adapter.getDatas().size(); i++) {
            CentergetcollectsBean.DataBean.ListBean listBean = adapter.getDatas().get(i);
            ProductsBean.DataBean dataBean = new ProductsBean.DataBean();
            dataBean.setId(String.valueOf(listBean.getId()));
            dataBean.setImage(listBean.getImage());
            dataBean.setPrice(listBean.getPrice());
            dataBean.setSpecification(listBean.getSpec_size());
            dataBean.setTitle(listBean.getName());
            dataBean.setCollect(listBean.isIsCollect());
            productsBean.getData().add(dataBean);
        }
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.POSITION, position);
        bundle.putSerializable(Constants.BEAN, productsBean);
        bundle.putBoolean(CommonActivity.ISFULLSCREEN, true);
        startFragment(ProductsFragment.class, bundle);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        centergetCollects(false);
    }

    @Override
    public void onRefresh(boolean isShow) {
        super.onRefresh(isShow);
        centergetCollects(isShow);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (isHasNext) {
            centergetCollects(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    /**
     * 获取收藏的商品
     */
    private void centergetCollects(boolean isShow) {
        /**
         * searchWord关键词
         styleId风格id
         minPrice最小价格
         maxPrice最大价格
         pageNo最大价格
         pageSize最大价格
         */
        Map<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(styleId) && !styleId.equals(String.valueOf(Constants.ALLBEAN))) {
            map.put("styleId", styleId);//1 brand 2 design 3 room
        }
        map.put("minPrice", minPrice);
        map.put("maxPrice", maxPrice);
        map.put("pageNo", String.valueOf(pageNum));
        map.put("pageSize", "1000");
        if (!TextUtils.isEmpty(searchWord)) {
            map.put("searchWord", searchWord);
        }
        presenter.centergetcollects(map, isShow);
    }

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
        if (msgEvent.getType() == MsgConstant.IS_COLOCT_EDIT) {
            //编辑操作
            boolean isEdit = (boolean) msgEvent.getBean();
            isShowEdit(isEdit);
        } else if (msgEvent.getType() == MsgConstant.COLLECT_ALL_SELECT) {
            //全选操作
            boolean isAllSelect = !isAllSelect();//是否全选
            List<CentergetcollectsBean.DataBean.ListBean> list = adapter.getDatas();
            if (isAllSelect) {
                //全选
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setCheckDelete(true);
                }
                adapter.notifyDataSetChanged();
                EventBus.getDefault().post(new MessageEvent(MsgConstant.IS_HAS_SELECT, true));
                EventBus.getDefault().post(new MessageEvent(MsgConstant.SELECT_CHANGE, false));
            } else {
                //取消全选
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setCheckDelete(false);
                }
                adapter.notifyDataSetChanged();
                EventBus.getDefault().post(new MessageEvent(MsgConstant.IS_HAS_SELECT, false));
                EventBus.getDefault().post(new MessageEvent(MsgConstant.SELECT_CHANGE, true));
            }
        } else if (msgEvent.getType() == MsgConstant.DELETE_SELECT_PRODUCT) {
            JSONArray array = new JSONArray();
            //删除操作
            List<CentergetcollectsBean.DataBean.ListBean> list = adapter.getDatas();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isCheckDelete()) {
                    array.put(list.get(i).getId());
                }
            }
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), array.toString());
            presenter.centerdeletecollects(body);
        } else if (msgEvent.getType() == MsgConstant.SEARCH_COLLECT) {
            //搜索
            String searchWord = msgEvent.getMsg();
            setSearchWord(searchWord);
        } else if (msgEvent.getType() == MsgConstant.COLLECT_MINPRICE) {
            //最低价
            String minPrice = msgEvent.getMsg();
            setMinPrice(minPrice);
        } else if (msgEvent.getType() == MsgConstant.COLLECT_MAXPRICE) {
            //最高价
            String maxPrice = msgEvent.getMsg();
            setMaxPrice(maxPrice);
        } else if (msgEvent.getType() == MsgConstant.COLLECT_STYLEID) {
            //最高价
            String id = msgEvent.getMsg();
            setStyleId(id);
        }
    }

    /**
     * 是否全选
     *
     * @return
     */
    private boolean isAllSelect() {
        boolean isAllSelect = true;
        List<CentergetcollectsBean.DataBean.ListBean> list = adapter.getDatas();
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).isCheckDelete()) {
                isAllSelect = false;
                break;
            }
        }
        return isAllSelect;
    }

    /**
     * 是否有选中
     */
    private boolean isHasSelect() {
        boolean isAllSelect = false;
        List<CentergetcollectsBean.DataBean.ListBean> list = adapter.getDatas();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isCheckDelete()) {
                isAllSelect = true;
                break;
            }
        }
        return isAllSelect;
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }
}