package com.madao.simplebeat;

import android.app.Service;
import android.content.Intent;
import android.media.PlaybackParams;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.security.Provider;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class backStageService extends Service {
    private MediaPlayer mediaPlayer;
    private static int bpm;
    private static String sound;
    private Handler handler;
    private Runnable playRunnable;
    private int num = 0;

    private static final Map<String, String> audioMap = new HashMap<String, String>() {{
        put("Default", "audios/downbeat.wav");
        put("BassDrum", "audios/BassDrum1.wav");
        put("Clap", "audios/Clap1.wav");
        put("Claves", "audios/Claves1.wav");
        put("Rimshot", "audios/Rimshot1.wav");
    }};

    private static final Map<String, String> audioMapSp = new HashMap<String, String>() {{
        put("Default", "audios/upbeat.wav");
        put("BassDrum", "audios/BassDrum2.wav");
        put("Clap", "audios/Clap2.wav");
        put("Claves", "audios/Claves2.wav");
        put("Rimshot", "audios/Rimshot2.wav");
    }};
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler = new Handler();
        playRunnable = new Runnable() {
            @Override
            public void run() {
                playAudioFromAssets();
                // 计算间隔时间，以毫秒为单位
                int interval = 60000 / bpm; // 60,000 毫秒除以 BPM
                handler.postDelayed(this, interval); // 按间隔时间重新运行
            }
        };
        handler.post(playRunnable);
        return START_STICKY;
    }
    public static void setNumber(int _bpm, String _sound) {
        bpm = _bpm;
        sound = _sound;
    }

    private void playAudioFromAssets() {
        mediaPlayer = new MediaPlayer();
        try {
            num++;
            AssetFileDescriptor afd;
            if(num%4==0){
                afd = getAssets().openFd(audioMapSp.get(sound));
            }else{
                afd = getAssets().openFd(audioMap.get(sound));
            }
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) { }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null && playRunnable != null) {
            handler.removeCallbacks(playRunnable);
        }
        // Stop and release the MediaPlayer
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
