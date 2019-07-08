package com.example.app4autism;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapterFruits extends BaseAdapter {

    private Context context;

    public Integer[] images = {
            R.drawable._apple, R.drawable._banana,
            R.drawable._dragonfruit, R.drawable._durian,
            R.drawable._kiwi, R.drawable._mango,
            R.drawable._mangosteen, R.drawable._orangefruit,
            R.drawable._peach, R.drawable._pear,
            R.drawable._pineapple, R.drawable._rambutan
    };

    public String[] name = {"Apple","Banana","Dragon Fruit","Durian","Kiwi","Mango",
    "Mangosteen","Orange","Peach","Pear","Pineapple","Rambutan"};

    //Constructor
    public ImageAdapterFruits(Context c)
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
