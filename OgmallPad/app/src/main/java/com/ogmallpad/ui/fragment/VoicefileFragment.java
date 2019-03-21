package com.ogmallpad.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.TimeUtils;
import com.ogmallpad.R;
import com.ogmallpad.bean.Voice;
import com.ogmallpad.constant.Constants;
import com.ogmallpad.constant.MsgConstant;
import com.ogmallpad.contract.VoicefileContract;
import com.ogmallpad.dao.VoiceUtils;
import com.ogmallpad.databinding.FgVoicefileItemLayoutBinding;
import com.ogmallpad.databinding.VoiceFileHeaderLayoutBinding;
import com.ogmallpad.presenter.VoicefilePresenter;
import com.ogmallpad.ui.BaseFragment;
import com.shan.netlibrary.bean.CentercustomersBean;
import com.shan.netlibrary.net.BaseBean;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 录音文件
 * Created by chenjunshan on 2018-07-04.
 */

public class VoicefileFragment extends BaseFragment<FgVoicefileItemLayoutBinding, Voice> implements VoicefileContract.View {
    private VoicefilePresenter presenter;
    private VoiceFileHeaderLayoutBinding headBinding;
    private CentercustomersBean.DataBean.ListBean bean;

    @Override
    public int bindItemLayout() {
        return R.layout.fg_voicefile_item_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        titleBinding.tvLeft.setText(getString(R.string.str_lywj));
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        lvBinding.xrecyclerview.setLoadingMoreEnabled(false);
        lvBinding.xrecyclerview.setPullRefreshEnabled(false);
        bean = (CentercustomersBean.DataBean.ListBean) getActivity().getIntent().getSerializableExtra(Constants.BEAN);
        presenter = new VoicefilePresenter(getActivity(), this);
        addHeadView();
    }

    /**
     * 增加头部View
     */
    private void addHeadView() {
        headBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.voice_file_header_layout, lvBinding.llHeader, false);
        lvBinding.llHeader.addView(headBinding.getRoot());
    }

    @Override
    public void onSuccess(BaseBean baseBean, long mTag) {

    }

    @Override
    public void onFailure(Throwable e, long mTag) {

    }

    @Override
    protected void getListVewItem(FgVoicefileItemLayoutBinding binding, final Voice item, final int position) {
        super.getListVewItem(binding, item, position);
        String name = item.getPath();
        if (name.contains("/")) {
            String[] names = name.split("/");
            if (names.length > 0) {
                name = names[names.length - 1];
            }
        }
        binding.tvName.setText(name);
        binding.tvTime.setText(TimeUtils.DateTime04(item.getTime()));
        binding.tvSize.setText(TimeUtils.getTimeSize(item.getTimeSize()));
        binding.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new MessageEvent(MsgConstant.STARTPLAY, item.getPath()));
                if (list == null)
                    return;
                for (int i = 0; i < list.size(); i++) {
                    if (i == position) {
                        list.get(i).setIsPlaying(true);
                    } else {
                        list.get(i).setIsPlaying(false);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        if (item.isPlaying()) {
            binding.tvStop.setVisibility(View.VISIBLE);
        } else {
            binding.tvStop.setVisibility(View.INVISIBLE);
        }

        //停止语音播放
        binding.tvStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new MessageEvent(MsgConstant.STOPPLAY));
                refresh();
            }
        });
    }

    @Override
    public void onRefresh() {
        super.onRefresh();

    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().post(new MessageEvent(MsgConstant.STOPPLAY));
        refresh();
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    private List<Voice> list;

    @Override
    protected void initData() {
        super.initData();
        headBinding.tvId.setText(getString(R.string.str_id) + String.valueOf(bean.getCustomerId()));
        headBinding.tvName.setText(getString(R.string.str_xm2) + bean.getCustomerName());
        headBinding.tvMobile.setText(getString(R.string.str_sjh2) + bean.getCustomerMobile());
        list = VoiceUtils.queryCustomerId(bean.getCustomerId());
        setData(list);
    }

    @Override
    public void onMsgEvent(MessageEvent msgEvent) {
        super.onMsgEvent(msgEvent);
        if (msgEvent.getType() == MsgConstant.PLAYCOMPLETE) {
            refresh();
        }
    }

    private void refresh() {
        if (list == null)
            return;
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setIsPlaying(false);
        }
        adapter.notifyDataSetChanged();
    }
}