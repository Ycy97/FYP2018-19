package com.example.app4autism;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class QuizCategories extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_categories);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#87CEFA"));
    }
    public void onClickBodyParts(View view)
    {
        Intent quiz1 = new Intent(this,QuizPage.class);
        quiz1.putExtra("quizID",1);
        startActivity(quiz1);
    }

    public void onClickColours(View view)
    {
        Intent quiz2 = new Intent(this, QuizPage.class);
        quiz2.putExtra("quizID",2);
        startActivity(quiz2);
    }

    public void onClickEmotions(View view)
    {
        Intent quiz3 = new Intent(this,QuizPage.class);
        quiz3.putExtra("quizID",3);
        startActivity(quiz3);
    }

    public void onClickFruits(View view)
    {
        Intent quiz4 = new Intent(this,QuizPage.class);
        quiz4.putExtra("quizID",4);
        startActivity(quiz4);
    }

    public void onClickGeneralLifeStyle(View view)
    {
        Intent quiz5 = new Intent(this,QuizPage.class);
        quiz5.putExtra("quizID",5);
        startActivity(quiz5);
    }

    //method to go back to Main Page
    public void onClickBack(View view)
    {
        Intent toMainPage = new Intent(this,MainActivity.class);
        startActivity(toMainPage);
    }
}
