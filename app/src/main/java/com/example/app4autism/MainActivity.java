package com.example.app4autism;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set the background colour of the activity to light-blue
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#87CEFA"));
    }//end of onCreate method

    public void onClickPictographs(View view)
    {
        Intent i = new Intent(this,GalleryCategories.class);
        startActivity(i);
    }

    public void onClickDrawingBoard(View view)
    {
        Intent toDrawBoard = new Intent(this,Drawboard.class);
        startActivity(toDrawBoard);
    }

    public void onClickHeartRateMonitor(View view)
    {
        Intent toHeartRateMonitor = new Intent(this,HeartRateMonitor.class);
        startActivity(toHeartRateMonitor);
    }

    public void onClickMusicPlayer(View view)
    {
        Intent toMusicPlayer = new Intent(this,MusicPlayer.class);
        startActivity(toMusicPlayer);
    }

    public void onClickQuiz(View view)
    {
        Intent toQuiz = new Intent(this,QuizCategories.class);
        startActivity(toQuiz);
    }
}
