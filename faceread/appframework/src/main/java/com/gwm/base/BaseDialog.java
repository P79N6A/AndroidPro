package com.gwm.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gwm.android.ACache;

import butterknife.ButterKnife;

public abstract class BaseDialog extends DialogFragment {
    private BaseCommon common;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        common = BaseCommon.getCommon(getActivity());
        common.bindCacheParam(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = common.bindView(this);
        ButterKnife.bind(this,view);
        return view;
    }

    public void show(){
        if (getActivity() instanceof BaseActivity){
            ((BaseActivity) getActivity()).showDialog(this);
        }
    }

    public ACache getCache(){
        return BaseAppcation.getInstance().getCache();
    }
}
