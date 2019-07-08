package com.example.app4autism;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapterColour extends BaseAdapter {

    private Context context;

    public Integer[] images = {
            R.drawable._black, R.drawable._blue,
            R.drawable._brown, R.drawable._green,
            R.drawable._grey, R.drawable._orange,
            R.drawable._purple, R.drawable._red,
            R.drawable._white, R.drawable._yellow
    };

    public String[] name = {"Black","Blue","Brown","Green","Grey","Orange",
    "Purple","Red","White","Yellow"};

    //Constructor
    public ImageAdapterColour(Context c)
    {
        context = c;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(images[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setLayoutParams(new GridView.LayoutParams(240, 240));
        return imageView;
    }

}
