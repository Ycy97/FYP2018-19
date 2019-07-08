package com.example.app4autism;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class QuizResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#87CEFA"));

        TextView results = (TextView)findViewById(R.id.results_score);

        int score = getIntent().getIntExtra("rightAnswers",0);
        results.setText(score +" total correct answers!");
    }

    //method to go back to Main Page
    public void onClickBack(View view)
    {
        Intent toMainPage = new Intent(this,MainActivity.class);
        startActivity(toMainPage);
    }
}
