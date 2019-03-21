package com.ogmamllpadnew.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.junshan.pub.bean.MessageEvent;
import com.junshan.pub.utils.ImageCacheUtils;
import com.junshan.pub.utils.ScreenUtils;
import com.junshan.pub.utils.TimeUtils;
import com.ogmamllpadnew.MyApp;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.bean.Voice;
import com.ogmamllpadnew.constant.Constants;
import com.ogmamllpadnew.constant.MsgConstant;
import com.ogmamllpadnew.contract.VoicefileContract;
import com.ogmamllpadnew.dao.VoiceUtils;
import com.ogmamllpadnew.databinding.DialogTextVoiceBinding;
import com.ogmamllpadnew.databinding.FgVoiceFileHeaderLayoutBinding;
import com.ogmamllpadnew.databinding.FgVoicefileItemLayoutBinding;
import com.ogmamllpadnew.presenter.VoicefilePresenter;
import com.ogmamllpadnew.ui.BaseFragment;
import com.shan.netlibrary.bean.UserselectCustomerBean;
import com.shan.netlibrary.net.BaseBean;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 录音文件
 * Created by 陈俊山 on 2018-11-12.
 */

public class VoicefileFragment extends BaseFragment<FgVoicefileItemLayoutBinding, Voice> implements VoicefileContract.View {
    private VoicefilePresenter presenter;
    private int width;
    private UserselectCustomerBean.DataBean bean;
    @Override
    public int bindItemLayout() {
        return R.layout.fg_voicefile_item_layout;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        setLeftText(R.string.str_lyypwj);
    }

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
        presenter = new VoicefilePresenter(getActivity(), this);
        bean = (UserselectCustomerBean.DataBean) getActivity().getIntent().getSerializableExtra(Constants.BEAN);
        width = ScreenUtils.getScreenWidth() / 30;
        lvBinding.llParent.setPadding(width, width / 2, width, 0);
        addHeadView();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    private FgVoiceFileHeaderLayoutBinding mHeaderBinding;

    /**
     * 增加头部View
     */
    private void addHeadView() {
        mHeaderBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fg_voice_file_header_layout, lvBinding.llHeader, false);
        lvBinding.llHeader.addView(mHeaderBinding.getRoot());
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
        binding.tvTime.setText(TimeUtils.DateTime03(item.getTime()));
        binding.tvSize.setText(TimeUtils.getTimeSize(item.getTimeSize()));
        binding.llPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                voiceToText(item.getPath());
                if (item.isPlaying()) {
                    //停止语音播放
                    item.setPlaying(false);
                    EventBus.getDefault().post(new MessageEvent(MsgConstant.STOPPLAY));
                } else {
                    //开始语音播放
                    EventBus.getDefault().post(new MessageEvent(MsgConstant.STARTPLAY, item.getPath()));
                    if (list == null)
                        return;
                    for (int i = 0; i < list.size(); i++) {
                        if (i == position) {
                            list.get(i).setPlaying(true);
                        } else {
                            list.get(i).setPlaying(false);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        if (item.isPlaying()) {
            binding.playStatus.setChecked(true);
        } else {
            binding.playStatus.setChecked(false);
        }
    }
    private DialogTextVoiceBinding dialogTextVoiceBinding;


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
        refresh();
        EventBus.getDefault().post(new MessageEvent(MsgConstant.STOPPLAY));
        super.onDestroy();
        presenter.cancelAllRequest();
    }

    private List<Voice> list;

    @Override
    protected void initData() {
        super.initData();
        ImageCacheUtils.loadImage(getActivity(), MyApp.getInstance().getInfoBean().getData().getPicture(), mHeaderBinding.ivHead);
        mHeaderBinding.tvName.setText(MyApp.getInstance().getInfoBean().getData().getContactName());
        mHeaderBinding.tvMobile.setText(MyApp.getInstance().getInfoBean().getData().getContactPhone());
        mHeaderBinding.tvType.setText(MyApp.getInstance().getInfoBean().getData().getTypeName());
        list = VoiceUtils.queryCustomerId(bean.getId());
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
            list.get(i).setPlaying(false);
        }
        adapter.notifyDataSetChanged();
    }
}