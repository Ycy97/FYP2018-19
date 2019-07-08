package com.example.app4autism;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class MusicPlayer extends AppCompatActivity {

    MusicBoundService.MyLocalMusicBinder music_binder;
    boolean isBound = false;
    private ListView lv;
    private Intent intent;
    private File list[];
    private File selectedFromList;
    ServiceConnection myMusicConnection;
    private SeekBar music_progress;
    private TextView name_of_song;
    private TextView completion_of_song;
    private Handler progress_handler;
    private int progress;
    private final String TAG = "MP3Player";

    //decibel recorder
    TextView mStatusView;
    MediaRecorder mRecorder;
    private static double mEMA= 0.0;
    static final private double EMA_FILTER = 0.6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#87CEFA"));

        Log.i(TAG,"Music Player  activity started");

        //decibel status
        mStatusView = (TextView) findViewById(R.id.decibel_value);

        //Intent as as argument when binding the service
        intent = new Intent(this,MusicBoundService.class);
        music_progress = (SeekBar) findViewById(R.id.music_seekbar);
        name_of_song = (TextView) findViewById(R.id.name_of_song);
        completion_of_song = (TextView) findViewById(R.id.completion_view);

        //initialize handler object to use runnable
        progress_handler = new Handler();

        //When the app starts connect to the MusicBoundService class
        myMusicConnection= new ServiceConnection() {
            //once the service is connected then:
            @Override
            public void onServiceConnected(ComponentName classname, IBinder service) {
                //instantiate binder as am object of class MyLocalMusicBinder
                // under the class MusicBoundService of type service
                startRecorder();
                Log.i(TAG,"Mic starts recording ...");
                music_binder = (MusicBoundService.MyLocalMusicBinder) service;

                //set boolean bounded state as true
                isBound=true;

                Log.i(TAG,"MP3 connected to service");
            }

            //if the service is disconnected set the bound boolean as false
            @Override
            public void onServiceDisconnected(ComponentName classname) {
                isBound=false;
                Log.i(TAG,"MP3 disconnected from service");
            }
        };

        //bind to the service using intent to the MusicBoundService class using the variable
        //myMusicConnection as a service connection
        bindService(intent,myMusicConnection, Context.BIND_AUTO_CREATE);

        //update the progress of the song using runnable thread
        progress_handler.postDelayed(mRunnable, 1000);

        //when the app starts it create a list view of available music from the device's storage
        displayMusicList();

        //once the music list is displayed,display the music progress which allows users to
        //pull it back and forth in order to control the time of the music being played
        music_progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //seekInTo method in order to allow the music to be played
                //based on the location of the seekBar indicator when user drag the seekBar
                //Moves the media to specified time position
                music_binder.seekInTo(music_binder.get_music_duration()* music_progress.getProgress()/ seekBar.getMax());
            }
        });
    }//end of onCreate

    //method to display list of music using adapter ; separated from onCreate in order to
    // start the Service WHEN IT IS CLICKED by the user
    private void displayMusicList()
    {
        lv = (ListView)findViewById(R.id.music_list);

        //obtain the path directory to the music files
        File musicDir = new File(Environment.getExternalStorageDirectory().getPath()+"/media/audio/notifications/");

        //list all the files available
        list = musicDir.listFiles();

        lv.setAdapter(new ArrayAdapter<File>(this,android.R.layout.simple_list_item_1,list));

        //if user click on the item(music)
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                selectedFromList =(File) (lv.getItemAtPosition(myItemInt));

                //call stop_Music method here in order to stop any other previous
                // music that is playing before playing the music selected by the user
                music_binder.stop_Music();

                // load the music and start playing the music
                music_binder.load_Music(selectedFromList.getAbsolutePath());
                music_binder.pause_Music();


                String songName =music_binder.set_name();
                name_of_song.setText("Currently Playing :" + songName);

                //postDelayed allows the seekbar to progress along with the music
                // when it is playing or paused when the music is paused
                //progress_handler.postDelayed(mRunnable, 1000);
            }
        });

        Log.i(TAG,"List of music files displayed");
    }


    //use runnable as it separates the code that need to run asynchronous;
    // updates the progress bar as the music is being played
    private Runnable mRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            updateDecibelText();
            Log.i(TAG,"updating decibel text...");

            if(music_binder.get_music_duration() != 0)
            {
                //get the progress of music by using current progress and multiply by the
                // max of progress over total duration of song
                progress = music_binder.get_music_progress() * music_progress.getMax()/ music_binder .get_music_duration();
                //set the music progress based on the percentage of current progress by maximum duration over the duration it played
                music_progress.setProgress(progress);
                //obtain the progress of song to completion and display as percentage
                completion_of_song.setText(progress + "%");
            }
            progress_handler.postDelayed(mRunnable,1000);

            //the thread will only be updating the progress if music player is running after 0.3 second
//            if(music_binder.get_music_state().equals(MP3Player.MP3PlayerState.PLAYING))
//            {
//                //using music progress as a handler to call postDelayed
//                progress_handler.postDelayed(mRunnable,300);
//            }
        }
    };

    //-----------Methods for Buttons in UI------------------

    //method to play the music
    public void onClickPlay(View view)
    {
        //to prevent app from crashing when user click button straight without loading a music;
        // call the method if only binder is not null (applies to all button methods)
        if(music_binder!=null)
        {

//            //Toast only when the playing of music is resumed
//            if(music_binder.get_music_state().equals(MP3Player.MP3PlayerState.PAUSED))
//            {
//                Toast.makeText(this,"Resumed playing!",Toast.LENGTH_SHORT).show();
//            }
            if(DecibelSettings.value < soundDb(1.00)){
                music_binder.play_Music();
            }

            //added postDelayed here as when the music is paused and replayed again
            // using play button the seekbar will continue to update
//            if(!music_binder.get_music_state().equals((MP3Player.MP3PlayerState.PLAYING)))
//            {
//                progress_handler.postDelayed(mRunnable, 1000);
//            }
        }
    }

    //method to pause the music
    public void onClickPause(View view)
    {
        if(music_binder!=null)
        {
            music_binder.pause_Music();
            //Toast.makeText(this,"Music is paused!",Toast.LENGTH_SHORT).show();
        }
    }

    //method to stop the music
    public void onClickStop(View view)
    {
        if(music_binder!=null)
        {
            music_binder.stop_Music();
            Toast.makeText(this,"Music has stopped playing!",Toast.LENGTH_SHORT).show();

            //once the music has been stopped, set the seekbar back to 0
            music_progress.setProgress(0);

            //and set the name of the song to be empty
            name_of_song.setText("");

            //and set completion to 0%
            completion_of_song.setText(0 + "%");
        }
    }

    //method to go back to Main Page
    public void onClickBack(View view)
    {
        Intent toMainPage = new Intent(this,MainActivity.class);
        startActivity(toMainPage);
    }

    //method to go to decibel setting
    public void onClickDecibel(View view)
    {
        Intent toSetting = new Intent(this,DecibelSettings.class);
        startActivity(toSetting);
    }

    //onDestroy method to ensure the application unbind the service when the app is destroyed
    @Override
    protected void onDestroy()
    {
        stopRecorder();
        //unbind the service connection first before destroyed
        unbindService(myMusicConnection);
        super.onDestroy();
        Log.i(TAG,"MP3 MainActivity is destroyed");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        startRecorder();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.MEDIA_CONTENT_CONTROL) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MEDIA_CONTENT_CONTROL, Manifest.permission.READ_PHONE_STATE,},2);
            }
        }//end of if
    }//end of onResume

    public void startRecorder()
    {
        if(mRecorder == null)
        {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.setOutputFile("/dev/null");

            try {
                mRecorder.prepare();
            } catch (java.io.IOException ioe) {
                android.util.Log.e("Start12345", "IOException: " + android.util.Log.getStackTraceString(ioe));
            } catch (java.lang.SecurityException e) {
                android.util.Log.e("Start12345", "SecurityException: " + android.util.Log.getStackTraceString(e));
            }
            try {
                mRecorder.start();
            } catch (java.lang.SecurityException e) {
                android.util.Log.e("Start12345", "SecurityException: " + android.util.Log.getStackTraceString(e));
            }
        }//end of if statement (mRecorder == null)
    }

    public void stopRecorder() {
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    }

    public double soundDb(double ampl) {
        return 20 * Math.log10(getAmplitudeEMA() / ampl);
    }

    public double getAmplitude() {
        if (mRecorder != null) {
            return (mRecorder.getMaxAmplitude());
        } else {
            return 0;
        }
    }

    public double getAmplitudeEMA() {
        double amp = getAmplitude();
        mEMA = EMA_FILTER * amp + (1.0 - EMA_FILTER) * mEMA;
        return mEMA;
    }

    public void updateDecibelText()
    {
        mStatusView.setText(Double.toString((soundDb(1.00)))+ "db");
    }
}
