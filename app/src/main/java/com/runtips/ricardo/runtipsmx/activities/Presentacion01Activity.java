package com.runtips.ricardo.runtipsmx.activities;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.runtips.ricardo.runtipsmx.classes.PlayerConfig;
import com.runtips.ricardo.runtipsmx.R;


public class Presentacion01Activity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener,
        YouTubePlayer.PlayerStateChangeListener,
        YouTubePlayer.PlaybackEventListener {

    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_presentacion01);

        youTubePlayerView = findViewById(R.id.testPlayer);
        youTubePlayerView.initialize(PlayerConfig.API_KEY, this);


    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        youTubePlayer.setPlayerStateChangeListener(this);
        youTubePlayer.setPlaybackEventListener(this);
        youTubePlayer.loadVideo(getResources().getString(R.string.urlTestFisicoId));
        youTubePlayer.play();
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if(youTubeInitializationResult.isUserRecoverableError())
            youTubeInitializationResult.getErrorDialog(this, 1).show();
        else{
            String error =  "Error al iniciar YouTube" + youTubeInitializationResult.toString();
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == 1){
            getYouTubePlayerProvider().initialize(PlayerConfig.API_KEY, this);

        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider(){
        return youTubePlayerView;
    }


    @Override
    public void onLoading() {

    }

    @Override
    public void onLoaded(String s) {

    }

    @Override
    public void onAdStarted() {

    }

    @Override
    public void onVideoStarted() {

    }

    @Override
    public void onVideoEnded() {
        Intent intent = new Intent(Presentacion01Activity.this, CameraHeartRateActivity.class);
        startActivity(intent);
    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {

    }

    @Override
    public void onPlaying() {

    }

    @Override
    public void onPaused() {

    }

    @Override
    public void onStopped() {

    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }
}
