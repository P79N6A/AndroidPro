package com.gwm.base;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gwm.R;
import com.gwm.util.MyLogger;

/**
 * Created by Administrator on 2018/5/8 0008.
 */
public abstract class BaseTitleActivity extends BaseActivity{
    protected MyLogger Log = MyLogger.kLog();

    private View view;

    private ImageView ic_back;
    private TextView mTvTitle;
    private ImageView ivRight;
    private TextView tvRight;

    protected void setContentView(){
        view = common.bindView(this);
        if (view != null){
            View root = LayoutInflater.from(this).inflate(R.layout.activity_base_title,null,false);
            FrameLayout layout = root.findViewById(R.id.flytContent);

            ic_back = root.findViewById(R.id.iv_back);

            mTvTitle = root.findViewById(R.id.tvTitle);

            ivRight = root.findViewById(R.id.ivRight);
            tvRight = root.findViewById(R.id.tvRight);
            layout.addView(view);
            setContentView(root);
        }
    }
    public void setMyTitle(String title) {
        mTvTitle.setText(title);
    }
    public void showBack(){
        ic_back.setVisibility(View.VISIBLE);
        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                finish();
            }
        });
    }

    private void closeKeyboard() {

    }

    public void hideBack(){
        ic_back.setVisibility(View.GONE);
    }

    public void setRight(int resId, View.OnClickListener onClickListener){
        ivRight.setImageResource(resId);
        tvRight.setVisibility(View.GONE);
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setOnClickListener(onClickListener);
    }
    public void setRight(String right, View.OnClickListener onClickListener){
        tvRight.setText(right);
        ivRight.setVisibility(View.GONE);
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setOnClickListener(onClickListener);
    }
    public void hideRight(){
        ivRight.setVisibility(View.GONE);
    }


}
