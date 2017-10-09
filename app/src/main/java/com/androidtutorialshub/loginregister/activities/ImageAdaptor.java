package com.androidtutorialshub.loginregister.activities;
import android.content.Context;

import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.androidtutorialshub.loginregister.R;


public class ImageAdaptor extends BaseAdapter {
    private Context mContext;

    // Constructor
    public ImageAdaptor(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(800, 800));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.facebook, R.drawable.facebook,
            R.drawable.facebook, R.drawable.facebook,
            R.drawable.facebook, R.drawable.facebook,
            R.drawable.arjun, R.drawable.arjun2,
            R.drawable.instagram, R.drawable.instagram,
            R.drawable.instagram, R.drawable.instagram,
            R.drawable.facebook, R.drawable.facebook,
            R.drawable.facebook, R.drawable.facebook,
            R.drawable.facebook, R.drawable.facebook,
            R.drawable.instagram, R.drawable.instagram,
            R.drawable.instagram, R.drawable.instagram
    };
}