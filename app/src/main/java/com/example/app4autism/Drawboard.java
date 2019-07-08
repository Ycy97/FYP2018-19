package com.example.app4autism;

import android.Manifest;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Drawboard extends AppCompatActivity {

    //Request code to compare key value to match from other activities
    private static final int request_code_4_colour = 1;
    private static final int request_code_4_brush = 2;

    FingerPainterView brush_change;
    Uri gallery_image_Uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawboard);

        //When the app is launched obtain permission to access the user's device gallery
        ActivityCompat.requestPermissions(Drawboard.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},3);
        brush_change = (FingerPainterView) findViewById(R.id.fingerPainterView2);

        //when create this application give it options to open using downloads(handling a intent)
        handleIntent();
    }

    //Downloads open with this application @ implicit intent
    private void handleIntent()
    {
        Intent intent = getIntent();
        if(Intent.ACTION_VIEW.equals(intent.getAction()))
        {
            gallery_image_Uri = intent.getData();
            brush_change.load(gallery_image_Uri);
        }
    }

    //Method when user click on Color button to change brush colour
    // by proceeding to the colour configuration activity
    public void onClick(View view)
    {
        Intent i = new Intent(this,colour_configuration.class);

        //if request code matches, obtain color from colour configuration page
        startActivityForResult(i,request_code_4_colour);
    }

    //Method when user click on Brush Button to change brush settings
    //by proceeding to the brush configuration activity
    public void onClickBrush(View view)
    {
        Intent toBrushPage = new Intent(this,brush_configuration.class);

        //Variable to obtain default brush shape
        String current_brush = brush_change.getBrush().name();

        //Variable to obtain default brush size
        int brush_size = brush_change.getBrushWidth();

        //Send the default values to brush configuration page
        toBrushPage.putExtra("brush_size",brush_size);
        toBrushPage.putExtra("current_brush",current_brush);

        //if request code matches,obtain values from brush configuration page
        startActivityForResult(toBrushPage,request_code_4_brush);
    }

    //Method to clear the canvas
    public void onClickClear(View view)
    {
        brush_change.resetCanvas();
        Toast.makeText(this,"Canvas cleared",Toast.LENGTH_SHORT).show();
    }

    //Method to save drawings into Gallery
    public void onClickSave(View view){
        brush_change.save();
        Toast.makeText(this,"Image saved to Gallery",Toast.LENGTH_SHORT).show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //change brush colour according to values obtained from colour configuration activity
        if((requestCode == request_code_4_colour) && (resultCode == RESULT_OK))
        {
            int val = data.getExtras().getInt("colour_value");
            brush_change.setColour(val);
        }

        //change brush based on String value and integer value from brush configuration activity
        if((requestCode == request_code_4_brush) && (resultCode == RESULT_OK))
        {
            String brush_state = data.getExtras().getString("brush_value");
            int brush_size = data.getExtras().getInt("brush_size");

            if(brush_state.equals("ROUND"))
            {
                brush_change.setBrush(Paint.Cap.ROUND);
            }
            else if (brush_state.equals("SQUARE"))
            {
                brush_change.setBrush(Paint.Cap.SQUARE);
            }

            brush_change.setBrushWidth(brush_size);
        }
    }

    //Method to save settings of users in order to not call onCreate method again
    @Override
    protected void onSaveInstanceState (Bundle outState)
    {
        outState.putString("current_brush",brush_change.getBrush().toString());
        outState.putInt("brush_size",brush_change.getBrushWidth());
        outState.putInt("colour_value",brush_change.getColour());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        brush_change.setBrushWidth(savedInstanceState.getInt("brush_size"));
        brush_change.setColour(savedInstanceState.getInt("colour_value"));
        String brush_shape = savedInstanceState.getString("current_brush");

        if(brush_shape.equals("ROUND"))
        {
            brush_change.setBrush(Paint.Cap.ROUND);
        }
        else if (brush_shape.equals("SQUARE"))
        {
            brush_change.setBrush(Paint.Cap.SQUARE);
        }
    }
}
