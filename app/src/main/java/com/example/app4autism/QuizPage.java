//take in an int value to determine which categories

package com.example.app4autism;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizPage extends AppCompatActivity {

    Button b_ans1, b_ans2, b_ans3, b_ans4;
    ImageView iv_quiz;

    List<GalleryModel> list;

    Random r;

    int turn = 1;

    int right_answers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_page);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#87CEFA"));

        Intent intent = getIntent();
        int ID = intent.getExtras().getInt("quizID");

        r = new Random();

        iv_quiz =(ImageView) findViewById(R.id.quiz_view);

        b_ans1 = (Button)findViewById(R.id.answer1);
        b_ans2 = (Button)findViewById(R.id.answer2);
        b_ans3 = (Button)findViewById(R.id.answer3);
        b_ans4 = (Button)findViewById(R.id.answer4);

        list = new ArrayList<>();

        if(ID == 1)
        {
            for(int i =0; i<new ImageAdapterBodyParts(this).name.length;i++)
            {
                list.add(new GalleryModel(new ImageAdapterBodyParts(this).name[i], new ImageAdapterBodyParts(this).images[i]));
            }
        }
        else if(ID == 2)
        {
            for(int i =0; i<new ImageAdapterColour(this).name.length;i++)
            {
                list.add(new GalleryModel(new ImageAdapterColour(this).name[i], new ImageAdapterColour(this).images[i]));
            }
        }
        else if(ID == 3)
        {
            for(int i =0; i<new ImageAdapterEmotions(this).name.length;i++)
            {
                list.add(new GalleryModel(new ImageAdapterEmotions(this).name[i], new ImageAdapterEmotions(this).images[i]));
            }
        }
        else if(ID == 4)
        {
            for(int i =0; i<new ImageAdapterFruits(this).name.length;i++)
            {
                list.add(new GalleryModel(new ImageAdapterFruits(this).name[i], new ImageAdapterFruits(this).images[i]));
            }
        }
        else if(ID == 5)
        {
            for(int i =0; i<new ImageAdapterLifeStyle(this).name.length;i++)
            {
                list.add(new GalleryModel(new ImageAdapterLifeStyle(this).name[i], new ImageAdapterLifeStyle(this).images[i]));
            }
        }

        //randomize or shuffle the data
        Collections.shuffle(list);

        newQuestion(turn);

        b_ans1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if the answer is correct
                if(b_ans1.getText().toString().equalsIgnoreCase(list.get(turn-1).getName()))
                {
                    right_answers = right_answers +1;

                    //check if the last question
                    if(turn < list.size())
                    {
                        turn++;
                        newQuestion(turn);
                    }
                    else
                    {
                        getResult();
                    }
                }
                else
                {
                    //check if the last question
                    if(turn < list.size())
                    {
                        turn++;
                        newQuestion(turn);
                    }
                    else
                    {
                        getResult();
                    }
                }
            }
        });

        b_ans2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if the answer is correct
                if(b_ans2.getText().toString().equalsIgnoreCase(list.get(turn-1).getName()))
                {
                    right_answers = right_answers +1;

                    //check if the last question
                    if(turn < list.size())
                    {
                        turn++;
                        newQuestion(turn);
                    }
                    else
                    {
                        getResult();
                    }
                }
                else
                {
                    //check if the last question
                    if(turn < list.size())
                    {
                        turn++;
                        newQuestion(turn);
                    }
                    else
                    {
                        getResult();
                    }
                }

            }
        });

        b_ans3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if the answer is correct
                if(b_ans3.getText().toString().equalsIgnoreCase(list.get(turn-1).getName()))
                {
                    right_answers = right_answers +1;

                    //check if the last question
                    if(turn < list.size())
                    {
                        turn++;
                        newQuestion(turn);
                    }
                    else
                    {
                        getResult();
                    }
                }
                else
                {
                    //check if the last question
                    if(turn < list.size())
                    {
                        turn++;
                        newQuestion(turn);
                    }
                    else
                    {
                        getResult();
                    }
                }

            }
        });

        b_ans4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if the answer is correct
                if(b_ans4.getText().toString().equalsIgnoreCase(list.get(turn-1).getName()))
                {
                    right_answers = right_answers +1;
                    //check if the last question
                    if(turn < list.size())
                    {
                        turn++;
                        newQuestion(turn);
                    }
                    else
                    {
                        getResult();
                    }
                }
                else
                {
                    //check if the last question
                    if(turn < list.size())
                    {
                        turn++;
                        newQuestion(turn);
                    }
                    else
                    {
                        getResult();
                    }
                }

            }
        });
    }

    private void newQuestion(int number)
    {
        //set new pokemon image
        iv_quiz.setImageResource(list.get(number - 1).getImage());

        //decide on which button to place the correct answer
        int correct_answer = r.nextInt(4) + 1;

        int firstButton = number-1;
        int secondButton;
        int thirdButton;
        int fourthButton;

        switch(correct_answer)
        {
            case 1:
                b_ans1.setText(list.get(firstButton).getName());

                do{
                    secondButton = r.nextInt(list.size());
                } while(secondButton == firstButton);
                do{
                    thirdButton = r.nextInt(list.size());
                } while(thirdButton == firstButton || thirdButton == secondButton);
                do{
                    fourthButton = r.nextInt(list.size());
                }while(fourthButton == firstButton || fourthButton == secondButton || fourthButton ==thirdButton);

                b_ans2.setText(list.get(secondButton).getName());
                b_ans3.setText(list.get(thirdButton).getName());
                b_ans4.setText(list.get(fourthButton).getName());

                break;

            case 2:
                b_ans2.setText(list.get(firstButton).getName());

                do{
                    secondButton = r.nextInt(list.size());
                } while(secondButton == firstButton);
                do{
                    thirdButton = r.nextInt(list.size());
                } while(thirdButton == firstButton || thirdButton == secondButton);
                do{
                    fourthButton = r.nextInt(list.size());
                }while(fourthButton == firstButton || fourthButton == secondButton || fourthButton ==thirdButton);

                b_ans1.setText(list.get(secondButton).getName());
                b_ans3.setText(list.get(thirdButton).getName());
                b_ans4.setText(list.get(fourthButton).getName());
                break;
            case 3:
                b_ans3.setText(list.get(firstButton).getName());

                do{
                    secondButton = r.nextInt(list.size());
                } while(secondButton == firstButton);
                do{
                    thirdButton = r.nextInt(list.size());
                } while(thirdButton == firstButton || thirdButton == secondButton);
                do{
                    fourthButton = r.nextInt(list.size());
                }while(fourthButton == firstButton || fourthButton == secondButton || fourthButton ==thirdButton);

                b_ans2.setText(list.get(secondButton).getName());
                b_ans1.setText(list.get(thirdButton).getName());
                b_ans4.setText(list.get(fourthButton).getName());
                break;
            case 4:
                b_ans4.setText(list.get(firstButton).getName());

                do{
                    secondButton = r.nextInt(list.size());
                } while(secondButton == firstButton);
                do{
                    thirdButton = r.nextInt(list.size());
                } while(thirdButton == firstButton || thirdButton == secondButton);
                do{
                    fourthButton = r.nextInt(list.size());
                }while(fourthButton == firstButton || fourthButton == secondButton || fourthButton ==thirdButton);

                b_ans2.setText(list.get(secondButton).getName());
                b_ans3.setText(list.get(thirdButton).getName());
                b_ans1.setText(list.get(fourthButton).getName());
                break;
        }
    }

    public void getResult()
    {
        Intent results_page = new Intent(getApplicationContext(),QuizResult.class);
        results_page.putExtra("rightAnswers",right_answers);
        startActivity(results_page);
    }
}
