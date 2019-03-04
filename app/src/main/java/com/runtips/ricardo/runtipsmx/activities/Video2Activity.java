package com.runtips.ricardo.runtipsmx.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import com.runtips.ricardo.runtipsmx.R;

public class Video2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        VideoView videoview = (VideoView)findViewById(R.id.videoView);
        MediaController mc = new MediaController(this);
        mc.setAnchorView(videoview);
        videoview.setMediaController(mc);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.pulseinstructions;
        videoview.setVideoURI(Uri.parse(path));
        videoview.start();

        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Intent intent = new Intent(Video2Activity.this, HeartRateActivity.class);
                startActivity(intent);
            }
        });

    }
}
