package com.example.itp4501_assignment;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class knifeSoundEffectService extends Service {
    private MediaPlayer player;

    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.knife);
        player.setLooping(false);
        player.setVolume(10,10);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!player.isPlaying()) {
            player.start();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player.isPlaying()) {
            player.stop();
            player.release();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}