package com.runtips.ricardo.runtipsmx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

public class Presentacion01Activity extends AppCompatActivity {

    private VideoView videoView;
    private String youtubelink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentacion01);
        videoView = (VideoView) findViewById(R.id.videoViewPresentacion01);
        youtubelink = getString(R.string.urlTestFisico);
        videoView.setVideoPath(youtubelink);
        videoView.start();

    }


}
