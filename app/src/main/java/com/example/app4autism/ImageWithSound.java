package com.example.app4autism;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;

public class ImageWithSound extends AppCompatActivity {

    private TextToSpeech mTTS;
    private TextView mTextName;
    private SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeed;
    private Button mButtonSpeak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_with_sound);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#87CEFA"));

        mTextName = findViewById(R.id.name_pic);
        mSeekBarPitch = findViewById(R.id.seek_bar_pitch);
        mSeekBarSpeed = findViewById(R.id.seek_bar_speed);

        Intent i = getIntent();

        int position = i.getExtras().getInt("id");

        int code = i.getExtras().getInt("Num");

        if(code == 1)
        {
            ImageAdapterBodyParts adapter = new ImageAdapterBodyParts(this);
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageResource(adapter.images[position]);
            mTextName.setText(adapter.name[position]);
        }
        else if (code ==2)
        {
            ImageAdapterColour adapter = new ImageAdapterColour(this);
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageResource(adapter.images[position]);
            mTextName.setText(adapter.name[position]);
        }
        else if (code ==3)
        {
            ImageAdapterEmotions adapter = new ImageAdapterEmotions(this);
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageResource(adapter.images[position]);
            mTextName.setText(adapter.name[position]);
        }
        else if (code ==4)
        {
            ImageAdapterFruits adapter = new ImageAdapterFruits(this);
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageResource(adapter.images[position]);
            mTextName.setText(adapter.name[position]);
        }
        else if (code == 5)
        {
            ImageAdapterLifeStyle adapter = new ImageAdapterLifeStyle(this);
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageResource(adapter.images[position]);
            mTextName.setText(adapter.name[position]);
        }

        mButtonSpeak = findViewById(R.id.button_speak);

        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    int result = mTTS.setLanguage(Locale.ENGLISH);

                    if(result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS", "Language not supported");
                    }
                    else{
                        mButtonSpeak.setEnabled(true);
                    }
                }
                else{
                    Log.e("TTS","Initialization failed");
                }

            }
        });
        mButtonSpeak.setOnClickListener(    new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }
    private void speak()
    {
        String text = mTextName.getText().toString();
        float pitch = (float)mSeekBarPitch.getProgress()/50;
        if(pitch<0.1) pitch = 0.1f;

        float speed = (float)mSeekBarSpeed.getProgress()/50;
        if(speed<0.1) speed = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);

        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {
        if(mTTS != null)
        {
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }
}
