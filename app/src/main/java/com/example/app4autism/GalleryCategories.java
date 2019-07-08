package com.example.app4autism;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GalleryCategories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_categories);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#87CEFA"));
    }

    public void onClickBodyParts(View view)
    {
        Intent toBodyGallery = new Intent(this,BodyPartsGallery.class);
        startActivity(toBodyGallery);
    }

    public void onClickColours(View view)
    {
        Intent toColourGallery = new Intent(this, ColourGallery.class);
        startActivity(toColourGallery);
    }

    public void onClickEmotions(View view)
    {
        Intent toEmotionsGallery = new Intent(this,EmotionsGallery.class);
        startActivity(toEmotionsGallery);
    }

    public void onClickFruits(View view)
    {
        Intent toFruitsGallery = new Intent(this,FruitsGallery.class);
        startActivity(toFruitsGallery);
    }

    public void onClickGeneralLifeStyle(View view)
    {
        Intent toGLifestlye = new Intent(this,GeneralLifeStyleGallery.class);
        startActivity(toGLifestlye);
    }

    //method to go back to Main Page
    public void onClickBack(View view)
    {
        Intent toMainPage = new Intent(this,MainActivity.class);
        startActivity(toMainPage);
    }
}
