package com.ogmall.indexscreen;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

/**
 * Created by Administrator on 2019/3/11.
 */

public class StoreyInfoActivity extends AppCompatActivity {
    public static final String STORE_INDEX = "STORE_INDEX";
    private int storeIndex;

    private ImageView iv_storeyIcon;
    private ImageView iv_storeyImg;
    private ImageView iv_anim,iv_anim1,iv_anim2,iv_anim3;
    private ImageView iv_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storey_info);
        storeIndex = getIntent().getIntExtra(STORE_INDEX,0);
        iv_storeyIcon = findViewById(R.id.iv_storeyIcon);
        iv_storeyImg = findViewById(R.id.iv_storeyImg);
        iv_anim = findViewById(R.id.iv_anim);
        iv_anim1 = findViewById(R.id.iv_anim1);
        iv_anim2 = findViewById(R.id.iv_anim2);
        iv_anim3 = findViewById(R.id.iv_anim3);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (storeIndex == 1){
            iv_storeyImg.setImageResource(R.mipmap.info_one);
            iv_storeyIcon.setImageResource(R.mipmap.icon_one_info);
        }else if (storeIndex == 2){
            iv_storeyImg.setImageResource(R.mipmap.info_two);
            iv_storeyIcon.setImageResource(R.mipmap.icon_two_info);
        }else if (storeIndex == 3){
            iv_storeyImg.setImageResource(R.mipmap.info_one);
            iv_storeyIcon.setImageResource(R.mipmap.icon_three_info);
        }else if (storeIndex == 4){
            iv_storeyImg.setImageResource(R.mipmap.info_one);
            iv_storeyIcon.setImageResource(R.mipmap.icon_four_info);
        }else if (storeIndex == 5){
            iv_storeyImg.setImageResource(R.mipmap.info_five);
            iv_storeyIcon.setImageResource(R.mipmap.icon_five_info);
        }else if (storeIndex == 6){
            iv_storeyImg.setImageResource(R.mipmap.info_sex);
            iv_storeyIcon.setImageResource(R.mipmap.icon_sex_info);
        }

        startAnim();

    }

    private void startAnim() {
        int[] screen = getScreen();
        final TranslateAnimation topAnim = new TranslateAnimation(-15,screen[0]-dp2px(getApplicationContext(),80),0,0);
        topAnim.setDuration(1000);
        topAnim.setRepeatCount(-1);
        iv_anim.startAnimation(topAnim);

        final TranslateAnimation rightAnim = new TranslateAnimation(0,0,0,dp2px(getApplicationContext(),280));
        rightAnim.setDuration(1000);
        rightAnim.setRepeatCount(-1);
        iv_anim1.startAnimation(rightAnim);
        final TranslateAnimation bottomAnim = new TranslateAnimation(0,-dp2px(getApplicationContext(),280),0,0);
        bottomAnim.setDuration(1000);
        bottomAnim.setRepeatCount(-1);
        iv_anim2.startAnimation(bottomAnim);
        final TranslateAnimation leftAnim = new TranslateAnimation(0,0,0,-dp2px(getApplicationContext(),280));
        leftAnim.setDuration(1000);
        leftAnim.setRepeatCount(-1);
        iv_anim3.startAnimation(leftAnim);
        startAD();
//        topAnim.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                iv_anim.startAnimation(rightAnim);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//        rightAnim.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                iv_anim.startAnimation(bottomAnim);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//        bottomAnim.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                iv_anim.startAnimation(leftAnim);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
//        leftAnim.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                iv_anim.startAnimation(topAnim);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
    }

    public int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public int[] getScreen(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        return new int[]{dm.widthPixels,dm.heightPixels};
    }


    private Handler handler = new Handler();
    private long time=1000*15;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            finish();
            handler.removeCallbacks(runnable);
        }
    };
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handler.removeCallbacks(runnable);
                break;
            case MotionEvent.ACTION_UP:
                startAD();
                break;
        }
        return super.onTouchEvent(event);
    }
    public void startAD() {
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, time);
    }
}
