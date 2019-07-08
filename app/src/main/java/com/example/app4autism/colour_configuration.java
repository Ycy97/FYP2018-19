package com.example.app4autism;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class colour_configuration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colour_configuration);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#87CEFA"));
    }

    //Change brush colour to Red
    public void onClickRedButton (View view)
    {
        int colour_red = 0xFFFF0000;
        finish(colour_red);
        Toast.makeText(this,"Red colour selected",Toast.LENGTH_SHORT).show();
    }

    //Change brush colour to Blue
    public void onClickBlueButton(View view)
    {
        int colour_blue =0xFF0000FF;
        finish(colour_blue);
        Toast.makeText(this,"Blue colour selected",Toast.LENGTH_SHORT).show();
    }

    //Change brush colour to Black
    public void onClickBlackButton(View view)
    {
        int colour_black = 0xFF000000;
        finish(colour_black);
        Toast.makeText(this,"Black colour selected",Toast.LENGTH_SHORT).show();
    }

    //Change brush colour to Cyan
    public void onClickCyanButton(View view)
    {
        int colour_cyan = 0xFF00FFFF;
        finish(colour_cyan);
        Toast.makeText(this,"Cyan colour selected",Toast.LENGTH_SHORT).show();
    }

    //Change brush colour to Gray
    public void onclickGrayButton(View view)
    {
        int colour_gray = 0xFF888888;
        finish(colour_gray);
        Toast.makeText(this,"Gray colour selected",Toast.LENGTH_SHORT).show();
    }

    //Change brush colour to Magenta
    public void onClickMagentaButton(View view)
    {
        int colour_magenta = 0xFFFF00FF;
        finish(colour_magenta);
        Toast.makeText(this,"Magenta colour selected",Toast.LENGTH_SHORT).show();
    }

    //Change brush colour to Yellow
    public void onClickYellowButton(View view)
    {
        int colour_yellow = 0xFFFFFF00;
        finish(colour_yellow);
        Toast.makeText(this,"Yellow colour selected",Toast.LENGTH_SHORT).show();
    }

    //Change brush colour to Green
    public void onClickGreenButton(View view)
    {
        int colour_green = 0xFF00FF59;
        finish(colour_green);
        Toast.makeText(this,"Green colour selected",Toast.LENGTH_SHORT).show();
    }

    //Send colour code integer value to MainActivity
    public void finish(int colour_code)
    {
        Intent data = new Intent();
        data.putExtra("colour_value",colour_code);
        setResult(RESULT_OK,data);
        super.finish();
    }
}
