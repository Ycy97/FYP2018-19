package com.example.app4autism;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapterBodyParts extends BaseAdapter {

    private Context context;

    public Integer[]images ={
            R.drawable._chest, R.drawable._ear,
            R.drawable._eye, R.drawable._eyebrow,
            R.drawable._foot, R.drawable._hair,
            R.drawable._hand, R.drawable._leg,
            R.drawable._mouth, R.drawable._nose,
            R.drawable._teeth, R.drawable._tongue
    };

    public String[] name = {"Chest","Ear","Eye","Eyebrow","Foot","Hair","Hand","Leg","Mouth",
            "Nose","Teeth","Tongue"};

    //Constructor
    public ImageAdapterBodyParts(Context c)
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
