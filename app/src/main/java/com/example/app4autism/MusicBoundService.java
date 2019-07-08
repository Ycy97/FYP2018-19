package com.example.app4autism;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MusicBoundService extends Service {
    private final String TAG = "MP3Player";
    //instantiate MP3Player object to use MP3Player class's methods
    MP3Player player = new MP3Player();

    private IBinder myMusicBinder = new MyLocalMusicBinder();

    //Variables to handle notifications
    NotificationCompat.Builder notificationBuilder;
    NotificationManager notificationManager;
    Intent notification_back_to_activity;

    String file_name = null;

    //Constructor for MusicBoundService class
    public MusicBoundService() {
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        call_notification();

        Log.i(TAG,"Service started");
    }

    //onBind() method was invoked when the system call bindService() when another component wants
    //to bind with the service and onBind() method returns an IBinder;
    //therefore does not require onStartCommand()
    @Override
    public IBinder onBind(Intent intent)
    {
        Log.i(TAG,"onBind");
        return myMusicBinder;
    }

    public void call_notification()
    {
        //create an intent that goes back to MainActivity class

        notification_back_to_activity = new Intent(this,MainActivity.class);

        //Uses PendingIntent to allow user to click on notification to return back to activity
        notificationBuilder = new NotificationCompat.Builder(this,"CHANNEL_ID")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Music Player")
                .setContentText("Click to Return back to MP3")
                .setContentIntent(PendingIntent.getActivity(this, 0, notification_back_to_activity, PendingIntent.FLAG_UPDATE_CURRENT));

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1,notificationBuilder.build());

        Log.i(TAG,"Notification created");
    }

    //MyLocalMusicBinder class of bounded service that will call the methods from MP3Player class
    public class MyLocalMusicBinder extends Binder
    {
        //Method to load music using the file path
        public void load_Music(String file_path)
        {
            player.load(file_path);
            Log.i(TAG,"Music loaded");
        }

        //Method to play the music if it is in a PAUSED state
        public void play_Music()
        {
            player.play();
            Log.i(TAG,"Music played");
        }

        //Method to pause the music if it is in a PLAYING state
        public void pause_Music()
        {
            player.pause();
            Log.i(TAG,"Music paused");
        }

        //Method to stop the music
        public void stop_Music()
        {
            player.stop();
            Log.i(TAG,"Music stopped");
        }

        //Method to replay the music
        public void replay_music()
        {
            player.load(player.getFilePath());
            Log.i(TAG,"Music replayed");
        }

        //Method to get duration of music
        public int get_music_duration()
        {
            return player.getDuration();
        }

        //Method to get progress of music
        public int get_music_progress()
        {
            return player.getProgress();
        }

        //Method to get state of music
        public MP3Player.MP3PlayerState get_music_state() {
            return player.getState();
        }

        //Method to set the name of the music based on the file path
        public String set_name()
        {
            String full_string= player.getFilePath();
            file_name = full_string.substring(26);
            return file_name;
        }

        //Method to Moves the media to specified time position
        public void seekInTo(int time)
        {
            player.seekInTo(time);
        }

    }

    //-----Service Lifecycle-------
    //when the service is not bound stop the music
    @Override
    public boolean onUnbind(Intent intent)
    {
        player.stop();
        Log.i(TAG,"Service unbind");
        return true;
    }
}
