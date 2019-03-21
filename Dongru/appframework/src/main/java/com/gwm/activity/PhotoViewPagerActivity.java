package com.gwm.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gwm.R;
import com.gwm.R2;
import com.gwm.annotation.Layout;
import com.gwm.base.BaseTitleActivity;
import com.gwm.util.ImageUtil;
import com.gwm.view.photoview.PhotoView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/12/22.
 */
public class PhotoViewPagerActivity extends BaseTitleActivity implements ViewPager.OnPageChangeListener {

    private static final String PATH_URL = "pathUrl";
    private static final String CURRENT_ITEM = "currentItem";
    private List<String> paths;

    @BindView(R2.id.vp_img)
    ViewPager pager;
    @BindView(R2.id.ll_ivs)
    LinearLayout ll_ivs;


    @Override
    public void onRequestSuccessData(Object data) {

    }

    @Override
    public void onCreate(FragmentManager manager, Bundle savedInstanceState) {
        setMyTitle("图片详情");
        showBack();
        paths = getIntent().getStringArrayListExtra(PATH_URL);
        int currentItem = getIntent().getIntExtra(CURRENT_ITEM,0);
        pager.setAdapter(new ViewPagerAdapter(paths, this));
        pager.addOnPageChangeListener(this);
        ll_ivs.removeAllViews();
        for (String path : paths){
            View view = new View(this);
            view.setBackgroundResource(R.drawable.shape_coners_transparent);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)getResources().getDimension(R.dimen.viewpager),
                    (int)getResources().getDimension(R.dimen.viewpager));
            params.leftMargin = (int) getResources().getDimension(R.dimen.viewpager_item);
            ll_ivs.addView(view,params);
        }
        ll_ivs.getChildAt(currentItem).setBackgroundResource(R.drawable.shape_coners_gray);
        pager.setCurrentItem(currentItem);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_photo_viewpager);
        ButterKnife.bind(this);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int arg) {
        for (int i = 0 ; i < ll_ivs.getChildCount() ; i++){
            if (i == arg){
                ll_ivs.getChildAt(i).setBackgroundResource(R.drawable.shape_coners_gray);
            }else {
                ll_ivs.getChildAt(i).setBackgroundResource(R.drawable.shape_coners_transparent);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private static class ViewPagerAdapter extends PagerAdapter{

        private List<String> paths;
        private Context mContext;

        public ViewPagerAdapter(List<String> paths, Context context){
            this.paths = paths;
            mContext = context;
        }

        @Override
        public int getCount() {
            return paths.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        public String getItem(int position){
            return paths.get(position);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            PhotoView imageView = new PhotoView(mContext);
            String path = getItem(position);
            ImageUtil.display(path,imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}