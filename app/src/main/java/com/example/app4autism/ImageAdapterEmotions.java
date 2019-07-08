package com.example.app4autism;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapterEmotions extends BaseAdapter {

    private Context context;

    public Integer[] images = {
            R.drawable._angry, R.drawable._annoyed,
            R.drawable._embarressed, R.drawable._excited,
            R.drawable._happy, R.drawable._hurt,
            R.drawable._nervous, R.drawable._proud,
            R.drawable._sad, R.drawable._scared,
            R.drawable._surprised, R.drawable._worried
    };

    public String[] name = {"Angry","Annoyed","Embarrassed","Excited","Happy","Hurt",
    "Nervous","Proud","Sad","Scared","Surprised","Worried"};

    //Constructor
    public ImageAdapterEmotions(Context c)
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
