package com.example.app4autism;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class brush_configuration extends AppCompatActivity {

    private String brush_shape;
    private int size_of_brush;
    private SeekBar edit_size;
    private TextView value_of_brushsize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brush_configuration);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#87CEFA"));

        //Bundle to get intent data from MainActivity so that it will obtain default value of brush
        //shape in case user click on confirm button without changing any settings
        Bundle extras = getIntent().getExtras();
        brush_shape = extras.getString("current_brush");

        if(extras == null)
        {
            return;
        }
        //Bundle to get default brush size from MainActivity
        size_of_brush = extras.getInt("brush_size");

        //SeekBar to enable user to select a range of brush size
        edit_size = (SeekBar) findViewById(R.id.brush_size_bar);
        value_of_brushsize = (TextView) findViewById(R.id.current_brushsize);
        //set default value brush size on SeekBar
        edit_size.setProgress(size_of_brush);
        value_of_brushsize.setText("Width of Brush: " + Integer.toString(size_of_brush) + "px ");

        edit_size.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                size_of_brush = progress;
                value_of_brushsize.setText("Width of Brush: " + Integer.toString(size_of_brush) + "px ");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    //Method for when user click on Round button, change variable string brush shape to Round
    public void onClickRoundBtn(View view)
    {
        brush_shape = "ROUND";
        Toast.makeText(this,"Round brush selected",Toast.LENGTH_SHORT).show();
    }


    //Method for when user click on Square button, change variable string brush shape to Square
    public void onClickSquareBtn(View view)
    {
        brush_shape = "SQUARE";
        Toast.makeText(this,"Square brush selected",Toast.LENGTH_SHORT).show();
    }

    //If user click Confirm, the values of current activity is passed to MainActivity
    public void onClickConfirmBtn(View view)
    {
        finish(brush_shape,size_of_brush);
        Toast.makeText(this,"Brush settings changed!",Toast.LENGTH_SHORT).show();
    }

    public void finish(String brush_type, int brush_size)
    {
        Intent data = new Intent();
        data.putExtra("brush_value",brush_type);
        data.putExtra("brush_size",brush_size);
        setResult(RESULT_OK,data);
        super.finish();
    }
}
