package com.example.app4autism;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapterLifeStyle extends BaseAdapter {

    private Context context;

    public Integer[] images ={
            R.drawable._brush, R.drawable._cleaning,
            R.drawable._drawing, R.drawable._eating,
            R.drawable._jumping, R.drawable._playing,
            R.drawable._reading, R.drawable._running,
            R.drawable._sleeping, R.drawable._swimming,
            R.drawable._toilet, R.drawable._waving
    };

    public String[] name = {"Brushing", "Cleaning", "Drawing", "Eating", "Jumping",
    "Playing", "Reading", "Running", "Sleeping", "Swimming", "Toilet", "Waving"};

    //Constructor
    public ImageAdapterLifeStyle(Context c)
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
