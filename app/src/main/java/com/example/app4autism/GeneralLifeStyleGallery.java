package com.example.app4autism;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class GeneralLifeStyleGallery extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_life_style_gallery);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#87CEFA"));

        GridView gridView = (GridView)findViewById(R.id.gridView_lifestyle);

        gridView.setAdapter(new ImageAdapterLifeStyle(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), ImageWithSound.class);
                i.putExtra("id", position);
                i.putExtra("Num",5);
                startActivity(i);
            }
        });
    }
}
