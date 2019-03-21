package com.gwm.base;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gwm.android.ACache;
import com.gwm.android.Handler;
import com.gwm.messagesendreceive.MessageBus;
import com.gwm.util.MyLogger;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment implements Handler.HandlerListener,ViewModelCallback{
	protected FragmentManager manager;
    protected View view;
    protected MyLogger Log = MyLogger.kLog();
    private BaseCommon common;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		manager = getChildFragmentManager();
        common = BaseCommon.getCommon(getActivity());
		MessageBus.getBus().register(this);
		common.bindCacheParam(this);
	}
	@Override
	public final View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		onCreateView(inflater,savedInstanceState);
        return view;
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		MessageBus.getBus().unregister(this);
	}
    protected View setContentView(){
		return common.bindView(this);
    }

	public void onCreateView(LayoutInflater inflater,Bundle savedInstanceState){
        view = setContentView();
        ButterKnife.bind(this,view);
    }

	public Context getContext(){
		return BaseAppcation.getInstance().getApplicationContext();
	}
   @Override
    public void handleMessage(Message msg) {

    }
	public ACache getCache(){
		return BaseAppcation.getInstance().getCache();
	}
}
