package com.ogmamllpadnew.ui;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;

import com.junshan.pub.ui.fragment.LibFragment;
import com.ogmamllpadnew.R;
import com.ogmamllpadnew.constant.Constants;
import com.shan.netlibrary.net.CancelRequestListener;
import com.shan.netlibrary.net.HttpBuilder;

import java.util.Locale;
import java.util.Stack;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;


/**
 * Created by chenjunshan on 16-11-23.
 */

public class BaseFragment<T extends ViewDataBinding, D> extends LibFragment<T, D> implements CancelRequestListener {
    private Stack<Subscription> subscriptions = new Stack<>();
    protected int totalPage;//总页数
    protected int pageNum = 1;//当前页
    protected int pageSize = 18;//页大小
    protected boolean isHasNext = true;//是否有下一页

    @Override
    public void initOnCreate(@Nullable Bundle savedInstanceState) {
        super.initOnCreate(savedInstanceState);
    }

    /**
     * 语言切换
     *
     * @param language
     */
    protected void switchLanguage(String language) {
        //设置应用语言类型
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        if (language.equals(Constants.EN)) {
            config.locale = Locale.ENGLISH;
        } else if (language.equals(Constants.ZH)) {
            config.locale = Locale.SIMPLIFIED_CHINESE;
        } else if (language.equals(Constants.TZH)) {
            config.locale = Locale.TRADITIONAL_CHINESE;
        }
        resources.updateConfiguration(config, dm);
    }

    @Override
    public void cancelRequest() {
        if (subscriptions != null && subscriptions.size() > 0) {
            Subscription subscription = subscriptions.peek();
            if (subscription != null) {
                //取消Http请求
                subscription.unsubscribe();
                subscriptions.pop();//出栈
            }
        }
    }

    @Override
    public void cancelAllRequest() {
        if (subscriptions != null && subscriptions.size() > 0) {
            while (!subscriptions.isEmpty()) {
                subscriptions.peek().unsubscribe();
                subscriptions.pop();
            }
        }
    }

    public <T> void startRequest(Observable observable, Subscriber<T> subscriber) {
        Subscription subscription = HttpBuilder.getInstance().execute(observable, subscriber);
        if (subscription != null) {
            subscriptions.push(subscription);//入栈
        }
    }

    /**
     * 判断可不可以加载
     *
     * @param total
     */
    public void isLoadingMore(int total) {
        totalPage = total / pageSize;
        if (total % pageSize > 0) {
            totalPage++;
        }
        if (pageNum >= totalPage) {
            isHasNext = false;
            lvBinding.xrecyclerview.setLoadingMoreEnabled(false);
        } else {
            isHasNext = true;
            pageNum++;
        }
        closeLoad();
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        if (lvBinding == null)
            return;
        lvBinding.xrecyclerview.setLoadingMoreEnabled(true);
        lvBinding.xrecyclerview.scrollToPosition(0);
        pageNum = 1;
        clearData();
    }

    public void onRefresh(boolean isShow) {
        if (lvBinding == null)
            return;
        lvBinding.xrecyclerview.setLoadingMoreEnabled(true);
        lvBinding.xrecyclerview.scrollToPosition(0);
        pageNum = 1;
        clearData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cancelAllRequest();
    }


    public void setTitleWhiteColor() {
        if (titleBinding != null) {
            titleBinding.statusBar.setBackgroundResource(R.color.white);
            titleBinding.btnLeft.setImageResource(R.mipmap.ic_back_black);
            titleBinding.tvTitle.setTextColor(getResources().getColor(R.color.color_333333));
            titleBinding.tvRight.setTextColor(getResources().getColor(R.color.color_333333));
        }
    }

}
