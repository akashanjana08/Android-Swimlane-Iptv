package shop.tv.rsys.com.tvapplication.player;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import java.io.IOException;

import shop.tv.rsys.com.tvapplication.R;

/** Created by akash.sharma on 2/12/2018.
 * */

public class MediaPlayerActivity extends Activity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {
    private SurfaceView mSurfaceView;
    private MediaPlayer mMediaPlayer;
    private SurfaceHolder mSurfaceHolder;
    int flag = 0;
    //private static final String VIDEO_PATH = "rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov";
    //private static final String VIDEO_PATH = "rtsp://10.67.190.130:554/E-Ent-1A-1Subt-1txt_0.ts";
    private static String VIDEO_PATH = "rtsp://10.67.181.197:1935/vod/trailer.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(MediaPlayerActivity.this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setDisplay(mSurfaceHolder);
        try {
            mMediaPlayer.setDataSource(VIDEO_PATH);
            mMediaPlayer.prepare();
            mMediaPlayer.setOnPreparedListener(MediaPlayerActivity.this);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mMediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public void changeChannel(View view) {
        flag++;
        if (flag == 0) {
            VIDEO_PATH = "rtsp://10.67.181.197:1935/vod/trailer.mp4";
        } else if(flag == 1){
            VIDEO_PATH = "rtsp://10.67.181.197:1935/vod/sample.mp4";
        }
        else  if(flag == 2){
            VIDEO_PATH = "rtsp://10.67.181.197:1935/vod/future_display.mp4";
            flag = -1;
        }
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(VIDEO_PATH);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}