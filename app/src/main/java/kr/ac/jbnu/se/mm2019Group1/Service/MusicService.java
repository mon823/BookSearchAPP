package kr.ac.jbnu.se.mm2019Group1.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import kr.ac.jbnu.se.mm2019Group1.R;

public class MusicService extends Service {

    MediaPlayer musicPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean message = intent.getExtras().getBoolean("MESSEAGE_KEY");
        if(message){
            musicPlayer = MediaPlayer.create(this, R.raw.music);
            musicPlayer.start();
        }
        else{
            musicPlayer.stop();
            musicPlayer.release();
        }


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {


        super.onCreate();
    }

    @Override
    public void onDestroy() {
        musicPlayer.stop();
        super.onDestroy();
    }


}
