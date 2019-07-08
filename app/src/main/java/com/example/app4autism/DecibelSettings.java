package com.example.app4autism;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DecibelSettings extends AppCompatActivity {

    private EditText decibel_input;
    private Button apply;
    Thread runner;
    private TextView decibel_text;
    private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10;

    public static int value = 100;

    final Runnable updater = new Runnable() {
        @Override
        public void run() {
            showTolerableDecibel();
        }
    };

    final Handler sHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decibel_settings);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#87CEFA"));

        decibel_input = (EditText)findViewById(R.id.maxdecibel);
        apply = (Button)findViewById(R.id.buttonapply);
        decibel_text = (TextView)findViewById(R.id.tolerableValue);
        tv1 = (TextView)findViewById(R.id.textView);
        tv1.setBackgroundColor(Color.WHITE);
        tv2 = (TextView)findViewById(R.id.textView2);
        tv2.setBackgroundColor(Color.WHITE);
        tv3 = (TextView)findViewById(R.id.textView3);
        tv3.setBackgroundColor(Color.WHITE);
        tv4 = (TextView)findViewById(R.id.textView4);
        tv4.setBackgroundColor(Color.WHITE);
        tv5 = (TextView)findViewById(R.id.textView5);
        tv5.setBackgroundColor(Color.WHITE);
        tv6 = (TextView)findViewById(R.id.textView6);
        tv6.setBackgroundColor(Color.WHITE);
        tv7 = (TextView)findViewById(R.id.textView7);
        tv7.setBackgroundColor(Color.WHITE);
        tv8 = (TextView)findViewById(R.id.textView8);
        tv8.setBackgroundColor(Color.WHITE);
        tv9 = (TextView)findViewById(R.id.textView9);
        tv9.setBackgroundColor(Color.WHITE);
        tv10 = (TextView)findViewById(R.id.textView10);
        tv10.setBackgroundColor(Color.WHITE);


        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDecibel();
                finish();
            }
        });

        if(runner == null)
        {
            runner = new Thread()
            {
                public void run()
                {
                    while(runner!=null)
                    {
                        try{
                            Thread.sleep(1000);
                        }catch(InterruptedException e)
                        {

                        };
                        sHandler.post(updater);
                    }
                }
            };
            runner.start();
        }
    }//end of onCreate

    public void setDecibel(){
        try {
            value = Integer.parseInt(decibel_input.getText().toString());
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void showTolerableDecibel(){
        decibel_text.setText(Integer.toString(value));
    }

}
