package com.ogmallpad.utils;

import android.view.View;
import android.widget.ImageView;

import com.junshan.pub.utils.ImageCacheUtils;
import com.ogmallpad.R;


/**
 * Created by GIGAMOLE on 8/18/16.
 */
public class Utils {

    public static void setupItem(final View view, final LibraryObject libraryObject) {
        final ImageView img = (ImageView) view.findViewById(R.id.img_item);
        ImageCacheUtils.loadImage(view.getContext(),libraryObject.getRes(),img);

    }

    public static class LibraryObject {

        private String mTitle;
        private String mRes;

        public LibraryObject(final String res, final String title) {
            mRes = res;
            mTitle = title;
        }

        public String getTitle() {
            return mTitle;
        }

        public void setTitle(final String title) {
            mTitle = title;
        }

        public String getRes() {
            return mRes;
        }

        public void setRes(final String res) {
            mRes = res;
        }
    }
}
