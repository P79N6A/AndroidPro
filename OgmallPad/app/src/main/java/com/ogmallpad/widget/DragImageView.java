package com.ogmallpad.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.junshan.pub.utils.LogUtils;

/**
 * 可移动的ImageView
 * Created by chenjunshan on 8/22/17.
 */

@SuppressLint("AppCompatCustomView")
public class DragImageView extends ImageView {
    private Context context;
    private int statusBarHeight;
    private int mScreenWidth;
    private int mScreenHeight;

    private int lastX;
    private int lastY;


    private int mLastX;
    private int mLastY;
    private float mTouchX;
    private float mTouchY;
    private int left;
    private int right;
    private int top;
    private int bottom;

    public DragImageView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public DragImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public DragImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    /**
     * 初始化数据
     */
    private void init() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels - (getStatusBarHeight());
        initEvent();
    }

    private boolean isMove = false;//是否移动
    private long startTemp = 0;

    /**
     * 初始化事件
     */
    private void initEvent() {
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(final View v, final MotionEvent event) {
                //获取到手指处的横坐标和纵坐标
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startTemp = System.currentTimeMillis();
                        isMove = false;
/*                        lastX = x;
                        lastY = y;*/
                        // 相对于屏幕的坐标点
                        mLastX = (int) event.getRawX();
                        mLastY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (System.currentTimeMillis() - startTemp > 200) {
                            isMove = true;
                        } else {
                            isMove = false;
                        }
                        refreshPos(v, event);
                        break;
                    case MotionEvent.ACTION_UP:
                        /*ObjectAnimator objectAnimator = AnimatorManager.startAnimotion(DragImageView.this, 0, (-left), 400, AnimatorManager.TRANSLATIONX);
                        objectAnimator.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                refreshViewPos();
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {
                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        });*/
                        up();
                        break;
                }
                LogUtils.d("isMove:" + isMove);
                return isMove;
            }
        });

        /*this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.d("isMove2:"+isMove);
                if (isMove)
                    return;
                //EventBus.getDefault().post(new MessageEvent(MsgConstant.TOYJZJ));
            }
        });*/
    }

    public void up(){}

    /**
     * 刷新View的位置
     *
     * @param v
     * @param event
     */
    private void refreshPos(View v, MotionEvent event) {
        //计算移动的距离
        int offX = (int) (event.getRawX() - mLastX);
        int offY = (int) (event.getRawY() - mLastY);
                   /*     int offX = x - lastX;
                        int offY = y - lastY;*/

        left = v.getLeft() + offX;
        right = v.getRight() + offX;
        top = v.getTop() + offY;
        bottom = v.getBottom() + offY;
        // 下面判断移动是否超出屏幕
        if (left < 0) {
            left = 0;
            right = left + v.getWidth();
        }

        if (right > mScreenWidth) {
            right = mScreenWidth;
            left = right - v.getWidth();
        }

        if (top < 0) {
            top = 0;
            bottom = top + v.getHeight();
        }
        if (bottom > mScreenHeight) {
            bottom = mScreenHeight;
            top = bottom - v.getHeight();
        }
        //调用layout方法来重新放置它的位置
        v.layout(left, top, right, bottom);
        mLastX = (int) event.getRawX();
        mLastY = (int) event.getRawY();
        /*Log.i("mLastX", "onTouch: " + mLastX);
        Log.i("mLastY", "onTouch: " + mLastY);
        Log.i("left", "onTouch: " + left);
        Log.i("top", "onTouch: " + top);
        Log.i("bottom", "onTouch: " + bottom);
        Log.i("right", "onTouch: " + right);*/
    }

    private void refreshViewPos() {
        Log.i("mLastX", "onTouch: " + mLastX);
        Log.i("mLastY", "onTouch: " + mLastY);
        Log.i("left", "onTouch: " + left);
        Log.i("top", "onTouch: " + top);
        Log.i("bottom", "onTouch: " + bottom);
        Log.i("right", "onTouch: " + right);
    }

    /**
     * 获取状态栏的高度
     */
    private int getStatusBarHeight() {

        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object o = c.newInstance();
            int height = Integer.parseInt(c.getField("status_bar_height")
                    .get(o).toString());
            return getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}
