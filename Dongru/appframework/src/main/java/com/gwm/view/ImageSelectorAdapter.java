package com.gwm.view;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.gwm.R;
import com.gwm.R2;
import com.gwm.messagesendreceive.MessageBus;
import com.gwm.messagesendreceive.MessageBusMessage;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageSelectorAdapter extends BaseAdapter {

    private static final int REQUEST_CODE_CHOOSE = 455;
    private int maxCount;

    public ArrayList<String> getPath() {
        return path;
    }

    private ArrayList<String> path;
    private Activity context;

    public ImageSelectorAdapter(ArrayList<String> path, Activity context, int maxCount) {
        this.path = path;
        this.context = context;
        this.maxCount = maxCount;
    }

    @Override
    public int getCount() {
        if (path == null || path.isEmpty()){
            return 1;
        }
        if (path.size() < maxCount){
            return path.size() + 1;
        }
        return maxCount;
    }

    @Override
    public String getItem(int position) {
        try {
            String paa = path.get(position);
            return paa;
        }catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_add_image, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setValue(getItem(position));
        return convertView;
    }

    public void replaceFooterImageList(List<String> photos) {
        if (path == null){
            path = new ArrayList<>();
        }
        path.clear();
        path.addAll(photos);
        notifyDataSetChanged();
    }

    public void removeData() {
        if (path != null){
            path.clear();
        }
        notifyDataSetChanged();
    }

    class ViewHolder{
        @BindView(R2.id.iv_imgContent)
        ImageView iv_imgContent;
        @BindView(R2.id.iv_add_img)
        ImageView iv_add_img;
        @BindView(R2.id.iv_del)
        ImageView iv_del;
        private String itemValue;

        public ViewHolder(View convertView) {
            ButterKnife.bind(this,convertView);
        }

        public void setValue(String item) {
            itemValue = item;
            if (item != null){
                iv_imgContent.setVisibility(View.VISIBLE);
                iv_imgContent.setImageURI(Uri.fromFile(new File(item)));
                iv_add_img.setVisibility(View.GONE);
                iv_del.setVisibility(View.VISIBLE);
            }else {
                iv_add_img.setVisibility(View.VISIBLE);
                iv_imgContent.setVisibility(View.GONE);
                iv_del.setVisibility(View.GONE);
            }
        }

        @OnClick({R2.id.iv_add_img, R2.id.iv_del})
        public void onClick(View view){
            switch (view.getId()){
                case R2.id.iv_add_img:  //打开相册
                    Matisse.from(context)
                            .choose(MimeType.ofImage())
                            .countable(true)
                            .maxSelectable(6)
                            .gridExpectedSize(context.getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                            .thumbnailScale(0.85f)
                            .imageEngine(new GlideEngine())
                            .forResult(REQUEST_CODE_CHOOSE);
                    break;
                case R2.id.iv_del:   //删除一张图片
                    path.remove(itemValue);
                    notifyDataSetChanged();

                    break;
            }
        }

        private String getLast(List<String> path) {
            if (path == null || path.isEmpty()){
                return "";
            }
            return path.get(path.size() - 1);
        }
    }
}
